/**
 * @file Simulator.java
 * @brief Entry point for the Prowl simulation.
 *        Parses CLI arguments, initializes the city, and runs the
 *        simulation loop — spawning new creatures at fixed intervals
 *        and optionally pausing each round in DEBUG mode.
 *
 * Usage:
 *   java -cp work prowl.Simulator <numMice> <numCats> <numZombieCats> <rounds> [randSeed] [--DEBUG]
 */
package prowl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Simulator {

    // spawn intervals
    private static final int MOUSE_SPAWN_INTERVAL = 100;
    private static final int CAT_SPAWN_INTERVAL   = 25;

    // default random seed
    private static final int DEFAULT_SEED = 100;

    // usage string
    private static final String USAGE =
        "java -cp work prowl.Simulator <numMice> <numCats> <numZombieCats> <rounds> [randSeed] [--DEBUG]";

    public static void main(String[] args) {
        // validate arguments
        if (args.length < 4) {
            System.out.println("ERROR: missing arguments");
            System.out.println(USAGE);
            System.exit(1);
        }

        // parse required arguments
        int numMice      = Integer.parseInt(args[0]);
        int numCats      = Integer.parseInt(args[1]);
        int numZombieCats = Integer.parseInt(args[2]);
        int rounds       = Integer.parseInt(args[3]);

        // parse optional arguments
        Random  rand  = new Random(args.length > 4 ? Integer.parseInt(args[4]) : DEFAULT_SEED);
        boolean debug = args.length > 5 && args[5].equals("--DEBUG");

        // initialize city
        City city  = new City(rand, numMice, numCats, numZombieCats);
        int  count = 0;

        // simulation loop
        while (count < rounds) {
            count++;

            // periodic spawning
            if (count % MOUSE_SPAWN_INTERVAL == 0) city.addMouse();
            if (count % CAT_SPAWN_INTERVAL   == 0) city.addCat();

            city.simulate();
            System.out.println("done " + count);
            System.out.flush();

            // pause each round in debug mode
            if (debug) {
                System.err.print("Enter anything to continue: ");
                try {
                    new BufferedReader(new InputStreamReader(System.in)).readLine();
                } catch (Exception e) {
                    System.exit(1);
                }
            }
        }
    }
}