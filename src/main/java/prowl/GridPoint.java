/**
 * @file GridPoint.java
 * @brief Represents a position on the simulation grid.
 *        Supports copy construction, equality, hashing, and
 *        wrap-aware Manhattan distance calculation.
 *
 * Note: equals() and hashCode() must not be modified —
 *       they are required for use as HashMap keys.
 */
package prowl;

public class GridPoint {

    // publicly accessible grid coordinates
    public int x, y;

    // constructor
    public GridPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // copy constructor
    public GridPoint(GridPoint other) {
        this.x = other.x;
        this.y = other.y;
    }

    // --- equality and hashing (do not modify) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridPoint)) return false;
        GridPoint other = (GridPoint) o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return this.x * 31 + this.y;
    }

    // --- distance ---

    // returns the shorter of the direct or wraparound distance along one axis
    private int minDist(int a, int b, int n) {
        return Math.min(
            Math.abs(a - b),
            Math.min(a, b) - Math.max(a, b) + n
        );
    }

    // returns wrap-aware Manhattan distance to another GridPoint
    public int dist(GridPoint other) {
        return minDist(x, other.x, City.WIDTH) + minDist(y, other.y, City.HEIGHT);
    }

    // --- display ---

    @Override
    public String toString() {
        return "(" + this.y + "," + this.x + ")";
    }
}