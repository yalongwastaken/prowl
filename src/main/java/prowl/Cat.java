/**
 * @file Cat.java
 * @brief Cat creature implementation for Prowl simulation.
 *        Hunts the closest mouse by minimizing Manhattan distance,
 *        changes color when chasing, and dies if it goes too long without eating.
 */
package prowl;

import java.util.Random;
import java.lang.Math;

public class Cat extends Creature {

    // constants
    private static final int INITIAL_HUNGER   = 50;
    private static final int CHASE_RANGE      = 20;
    private static final int RANDOM_TURN_ODDS = 5;

    // state
    private char type   = 'c';
    private int  eaten  = INITIAL_HUNGER;
    private int  closest = -1;

    // constructor
    public Cat(int x, int y, City city, Random rnd) {
        super(x, y, city, rnd);
        lab     = LAB_YELLOW;
        stepLen = 2;
    }

    @Override
    public void step() {
        died();

        if (!dead) {
            // randomly turn ~5% of the time
            if (rand.nextInt(100) < RANDOM_TURN_ODDS) {
                randomTurn();
            }
        }

        super.step();
    }

    @Override
    public void takeAction() {
        // find closest mouse and attempt to eat it
        closest = findClosest('m');
        eat();

        // chase if mouse is within range
        if (closest != -1) {
            Creature target = city.creatures.get(closest);
            boolean inRange = target.getGridPoint().dist(this.getGridPoint()) < CHASE_RANGE;

            if (inRange) {
                lab = LAB_CYAN;
                if (!target.isDead()) {
                    this.setDir(chase());
                }
            } else {
                lab = LAB_YELLOW;
            }
        } else {
            lab = LAB_YELLOW;
        }
    }

    // returns the direction that minimizes distance to the closest mouse
    private int chase() {
        int currentx  = this.getX();
        int currenty  = this.getY();
        int chasingx  = city.creatures.get(closest).getX();
        int chasingy  = city.creatures.get(closest).getY();

        // prioritize the axis with greater distance
        if (Math.abs(currenty - chasingy) > Math.abs(currentx - chasingx)) {
            int ifnorth = getY() + stepLen * dirY[NORTH];
            int ifsouth = getY() + stepLen * dirY[SOUTH];

            return (Math.abs(ifnorth - chasingy) > Math.abs(ifsouth - chasingy)) ? SOUTH : NORTH;
        } else {
            int ifeast = getX() + stepLen * dirX[EAST];
            int ifwest = getX() + stepLen * dirX[WEST];

            return (Math.abs(ifeast - chasingx) > Math.abs(ifwest - chasingx)) ? WEST : EAST;
        }
    }

    // eats the mouse at current position if one exists; resets hunger timer
    private void eat() {
        if (closest != -1 && city.creatures.get(closest).getGridPoint().equals(this.getGridPoint())) {
            city.creatures.get(closest).eaten();
            eaten = INITIAL_HUNGER;
        } else {
            eaten--;
        }
    }

    // marks cat as dead if hunger reaches zero
    public void died() {
        if (eaten == 0) {
            dead = true;
        }
    }

    public char getType() {
        return type;
    }
}