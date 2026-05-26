/**
 * @file Creature.java
 * @brief Abstract base class for all creatures in the Prowl simulation.
 *        Defines shared state (position, direction, color, health),
 *        movement logic, and the interface all subclasses must implement.
 *
 * Grid direction reference:
 *   NORTH (0) → y-1
 *   EAST  (1) → x+1
 *   SOUTH (2) → y+1
 *   WEST  (3) → x-1
 */
package prowl;

import java.util.Random;

public abstract class Creature {

    // directions
    public static final int NORTH    = 0;
    public static final int EAST     = 1;
    public static final int SOUTH    = 2;
    public static final int WEST     = 3;
    public static final int NUM_DIRS = 4;
    public static final int[] DIRS   = { NORTH, EAST, SOUTH, WEST };

    // direction delta tables indexed by NORTH/EAST/SOUTH/WEST
    protected final int[] dirY = { -1,  0,  1,  0 };
    protected final int[] dirX = {  0,  1,  0, -1 };

    // point color labels
    public static final char LAB_BLACK   = 'k';
    public static final char LAB_BLUE    = 'b';
    public static final char LAB_RED     = 'r';
    public static final char LAB_YELLOW  = 'y';
    public static final char LAB_ORANGE  = 'o';
    public static final char LAB_PINK    = 'p';
    public static final char LAB_MAGENTA = 'm';
    public static final char LAB_CYAN    = 'c';
    public static final char LAB_GREEN   = 'g';
    public static final char LAB_GRAY    = 'e';

    // grid bounds (must match City and Plotter)
    private static final int GRID_SIZE = 80;

    // state
    private int       dir;
    private GridPoint point;
    protected char    lab;
    protected boolean dead    = false;
    protected int     stepLen;

    // dependencies
    protected Random rand;
    protected City   city;

    // constructor
    public Creature(int x, int y, City city, Random rand) {
        this.point = new GridPoint(x, y);
        this.city  = city;
        this.rand  = rand;
        this.dir   = rand.nextInt(NUM_DIRS);
        this.dead  = false;
    }

    // --- state ---

    public boolean isDead() {
        return dead;
    }

    // marks this creature as dead (used when eaten by a cat)
    public void eaten() {
        dead = true;
    }

    // --- position ---

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }

    // returns a defensive copy to preserve encapsulation
    public GridPoint getGridPoint() {
        return new GridPoint(point);
    }

    // moves the creature by (dx, dy), wrapping around grid boundaries
    public void setGridPoint(int dx, int dy) {
        int newx = (getX() + dx) % GRID_SIZE;
        int newy = (getY() + dy) % GRID_SIZE;

        if (newx < 0) newx += GRID_SIZE;
        if (newy < 0) newy += GRID_SIZE;

        point.x = newx;
        point.y = newy;
    }

    // --- direction ---

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    // picks a random direction
    public void randomTurn() {
        this.dir = rand.nextInt(NUM_DIRS);
    }

    // --- distance ---

    // returns manhattan distance to another creature
    public int dist(Creature c) {
        return point.dist(c.getGridPoint());
    }

    // returns the index of the closest creature of the given type, or -1 if none found
    public int findClosest(char type) {
        int index = -1;

        for (int i = 0; i < city.creatures.size(); i++) {
            if (city.creatures.get(i).getType() != type) continue;

            if (index == -1 || this.dist(city.creatures.get(i)) < this.dist(city.creatures.get(index))) {
                index = i;
            }
        }

        return index;
    }

    // --- simulation ---

    // advances position one step in the current direction
    public void step() {
        int dx = stepLen * dirX[this.getDir()];
        int dy = stepLen * dirY[this.getDir()];
        this.setGridPoint(dx, dy);
    }

    public abstract void takeAction();

    public abstract char getType();

    // --- display ---

    public char getLab() {
        return lab;
    }

    // outputs position and color for the Plotter: "x y lab"
    @Override
    public String toString() {
        return point.x + " " + point.y + " " + lab;
    }
}