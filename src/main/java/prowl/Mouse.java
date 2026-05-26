/**
 * @file Mouse.java
 * @brief Mouse creature implementation for Prowl simulation.
 *        Moves one step per round with a 20% random turn chance,
 *        reproduces every 20 rounds, and dies after 22 rounds.
 */
package prowl;

import java.util.Random;

public class Mouse extends Creature {

    // constants
    private static final int LIFESPAN        = 22;
    private static final int REPRODUCE_CYCLE = 20;
    private static final int RANDOM_TURN_ODDS = 2; // out of 10 → 20%

    // state
    private char type      = 'm';
    private int  life      = LIFESPAN;
    private int  reproduce = REPRODUCE_CYCLE;

    // constructor
    public Mouse(int x, int y, City city, Random rnd) {
        super(x, y, city, rnd);
        lab     = LAB_BLUE;
        stepLen = 1;
    }

    @Override
    public void step() {
        died();

        if (!dead) {
            // randomly change direction ~20% of the time
            if (rand.nextInt(10) < RANDOM_TURN_ODDS) {
                randomTurn();
            }

            super.step();
        }
    }

    @Override
    public void takeAction() {
        reproduce();
    }

    // spawns a new mouse at current position every REPRODUCE_CYCLE rounds
    private void reproduce() {
        if (reproduce == 1) {
            city.creaturesToAdd.add(new Mouse(this.getX(), this.getY(), city, rand));
        }
        reproduce--;
    }

    // decrements life counter and marks dead when it reaches zero
    public void died() {
        if (life == 0) {
            dead = true;
        } else {
            life--;
        }
    }

    public char getType() {
        return type;
    }
}