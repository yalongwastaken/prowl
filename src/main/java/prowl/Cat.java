package prowl;
import java.util.Random;
import java.lang.Math;

public class Cat extends Creature {
    private char type = 'c';
    private int eaten = 50;
    private int closest = -1;
    
    // constructor
    public Cat(int x, int y, City city, Random rnd) {
        super(x, y, city, rnd);
        lab = LAB_YELLOW;
        stepLen = 2;
    }

    // step for the cat
    public void step() {
        died();

        if (!dead) {
            // randomly turn 5% of the time
            int bound = rand.nextInt(100);
            if (bound == 0 || bound == 1 || bound == 2 || bound == 3 || bound == 4) {
                    randomTurn();
            }
        }  

        super.step();
    }

    public void takeAction() {
        // finds closest mice
        closest = findClosest('m');

        // basic checks
        eat();

        // checks if closest mouse is within a dist of 20 to chase
        if (closest != -1 && this.city.creatures.get(closest).getGridPoint().dist(this.getGridPoint()) < 20) {
            lab = LAB_CYAN;
            
            if (!this.city.creatures.get(closest).isDead()) {
                this.setDir(chase());
            }
        }
        else {
            lab = LAB_YELLOW;
        }
    }

    // updating direction based on moving in minimizing distance
    // returns the direction that cat should be moving in
    private int chase() {
        int currentx = this.getX();
        int currenty = this.getY();
        int chasingx = this.city.creatures.get(closest).getX();
        int chasingy = this.city.creatures.get(closest).getY();

        // checks if vertical distance is strictly greater than horizontal
        if (Math.abs(currenty - chasingy) > Math.abs(currentx - chasingx)) {
            int ifnorth = getY() + stepLen * dirY[0];
            int ifsouth = getY() + stepLen * dirY[2];

            // checks if moving upward or downwards leads to the least distance
            if (Math.abs(ifnorth - chasingy) > Math.abs(ifsouth - chasingy)) {
                return 2;
            }
            else {
                return 0;
            }
        }
        else {
            int ifeast = getX() + stepLen * dirX[1];
            int ifwest = getX() + stepLen * dirX[3];

            // checks if moving west or east leads to the least distance
            if (Math.abs(ifeast - chasingx) > Math.abs(ifwest - chasingx)) {
                return 3;
            }
            else {
                return 1;
            }
        }

    }

    // checks if current location matches the closest 
    // true -> creature gets eaten (dies) & resests eaten timer
    private void eat() {
        if (closest != -1 && this.city.creatures.get(closest).getGridPoint().equals(this.getGridPoint())) {
            this.city.creatures.get(closest).eaten();
            eaten = 50;
        }
        else {
            eaten--;
        }
    }

    public void died() {
        if (eaten == 0) {
            dead = true;
        }
    }

    public char getType() {
        return type;
    }
}
