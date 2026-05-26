/**
 * @file City.java
 * @brief City grid manager for Prowl simulation.
 *        Maintains the creature list, handles spawning, and steps
 *        the simulation forward one round at a time.
 *
 * Grid reference:
 *
 *        (x)
 *         0 1 2 3 4 5 ... WIDTH
 *        .----------------...
 *   (y) 0|           ,--y
 *       1|      * (3,1)
 *       2|         ^
 *       3|         '-x
 *       .|
 *    HEIGHT:
 */
package prowl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class City {

    // grid dimensions (must match Plotter window size)
    public static final int WIDTH  = 80;
    public static final int HEIGHT = 80;

    // simulation data structures
    public List<Creature>  creatures;
    public Queue<Creature> creaturesToAdd;

    // random instance
    private Random rand;

    // constructor
    public City(Random rand, int numMice, int numCats, int numZombieCats) {
        this.rand           = rand;
        this.creatures      = new LinkedList<>();
        this.creaturesToAdd = new LinkedList<>();

        // populate initial creatures
        for (int i = 0; i < numMice; i++) addMouse();
        for (int i = 0; i < numCats;  i++) addCat();
        // for (int i = 0; i < numZombieCats; i++) addZombieCat();

        addNewCreatures();
    }

    // returns the current number of creatures in the simulation
    public int numCreatures() {
        return creatures.size();
    }

    // queues a new mouse at a random position
    public void addMouse() {
        creaturesToAdd.add(new Mouse(rand.nextInt(HEIGHT), rand.nextInt(WIDTH), this, rand));
    }

    // queues a new cat at a random position
    public void addCat() {
        creaturesToAdd.add(new Cat(rand.nextInt(HEIGHT), rand.nextInt(WIDTH), this, rand));
    }

    /*
    public void addZombieCat() {
        creaturesToAdd.add(new ZombieCat(rand.nextInt(HEIGHT), rand.nextInt(WIDTH), this, rand));
    }
    */

    // flushes the spawn queue into the active creature list
    public void addNewCreatures() {
        while (!creaturesToAdd.isEmpty()) {
            creatures.add(creaturesToAdd.remove());
        }
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    // advances the simulation by one round:
    // step all creatures → take actions → remove dead → spawn queued → print state
    public void simulate() {
        // move all creatures forward one step
        for (Creature c : creatures) {
            c.step();
        }

        // each creature takes an action based on new positions
        for (Creature c : creatures) {
            c.takeAction();
        }

        // collect dead creatures
        LinkedList<Creature> deadCreatures = new LinkedList<>();
        for (Creature c : creatures) {
            if (c.isDead()) deadCreatures.add(c);
        }

        // remove dead creatures
        for (Creature c : deadCreatures) {
            creatures.remove(c);
        }

        // flush spawn queue
        addNewCreatures();

        // print current state for Plotter
        for (Creature c : creatures) {
            System.out.println(c);
        }
    }
}