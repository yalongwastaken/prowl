import java.util.*;

public class Mouse extends Creature {
    private char type = 'm';
    private int life = 22;
    private int reproduce = 20;

    // constructor
    public Mouse(int x, int y, City city, Random rnd) {
        super(x, y, city, rnd);
        lab = LAB_BLUE;
        stepLen = 1;
    }

    // step for the mouse
    public void step() {
        died();

        if (!dead) {
            // 20% chance of occuring
            int bound = rand.nextInt(10);
            if (bound == 0 || bound == 1) {
                randomTurn();
            }

            super.step();
        }
    }

    // takes necessary actions after each step has been taken
    public void takeAction() {
        reproduce();
    }

    // add a new mouse every 20 turns
    private void reproduce() {
        if (reproduce  == 1) {
            this.city.creaturesToAdd.add(new Mouse(this.getX(), this.getY(), this.city, this.rand));
        }
        reproduce--;
    }

    // current mouse dies after 22 rounds
    public void died() {
        if (life == 0) {
            this.dead = true;
        }
        else {
            life--;
        }
    }

    // returns mouse specifier 'm'
    public char getType() {
        return type;
    }
}