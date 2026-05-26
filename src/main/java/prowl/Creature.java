package prowl;
import java.util.Random;

public abstract class Creature {
    
    public final static int NORTH = 0;
    public final static int EAST = 1;
    public final static int SOUTH = 2;
    public final static int WEST = 3;
    public final static int NUM_DIRS = 4;
    public final static int[] DIRS = {NORTH,EAST,SOUTH,WEST};


    //Use the index of the direction to determine how to add to a row or column
    //For example, if NORTH (index 0), the we subtract 1 from Y, and add 0 to X
    //direction
    protected final int[] dirY = {-1,0,1,0};
    protected final int[] dirX = {0, 1, 0, -1};


    //Point Colors -- handy contests to use to make your code more readiable
    public final static char LAB_BLACK='k';
    public final static char LAB_BLUE='b';
    public final static char LAB_RED='r';
    public final static char LAB_YELLOW='y';
    public final static char LAB_ORANGE='o';
    public final static char LAB_PINK='p';
    public final static char LAB_MAGENTA='m';
    public final static char LAB_CYAN='c';
    public final static char LAB_GREEN='g';
    public final static char LAB_GRAY='e';


    //current direction facing
    private int dir;

    //current point in grid
    private GridPoint point;

    //current color label for the point
    protected char lab;

    //random instance
    protected Random rand;

    //City in which this creature lives so that it can update it's
    //location and get other information it might need (like the
    //location of other creatures) when making decisions.    
    protected City city;

    //boolean to set when this creature is dead
    protected boolean dead = false;

    //how wide the steps are
    protected int stepLen;

    // constructor
    public Creature(int x, int y, City cty, Random rnd) {
        point = new GridPoint(x,y);
        city = cty;
        rand = rnd;
        dir = rand.nextInt(NUM_DIRS);
        dead = false;
    }

    public boolean isDead(){ return dead;}

    
    //getter/setter methods
    public int getY(){
        return point.y;
    }
    public int getX(){
        return point.x;
    }
    public GridPoint getGridPoint(){
        return new GridPoint(point); //return a copy to preseve
                                     //encapsulation
    }

    // changes location
    public void setGridPoint(int x, int y) {

        int tempx = (getX() + x)%80;
        int tempy = (getY() + y)%80;

        if (tempx < 0) {
            tempx = 80 + tempx;
        }
        if (tempy < 0) {
            tempy = 80 + tempy;
        }
        point.x = tempx;
        point.y = tempy;
    }

    //compute the distance to another creature
    public int dist(Creature c){
        return point.dist(c.getGridPoint());
    }

    //make a random turn
    public void randomTurn() {
        this.dir = rand.nextInt(4);
    }

    // when creature gets eaten or dies
    // specifically used for looping through the creatures
    // interaction when a cat eats a mouse
    public void eaten() {
        dead = true;
    }

    // finds closest creature with the specified char 
    public int findClosest(char type) {
        int index = -1;

        for (int i = 0; i < this.city.creatures.size(); i++) {
            if (this.city.creatures.get(i).getType() == type) {
                if (index == -1) {
                    index = i;
                }
                else {
                    if (this.dist(this.city.creatures.get(index)) > this.dist(this.city.creatures.get(i))) {
                        index = i;
                    }
                }
            }
        }
        return index;
    }

    // overwritten methods
    public void step() {
        int newY = stepLen * dirY[this.getDir()];
        int newX = stepLen * dirX[this.getDir()];

        this.setGridPoint(newX, newY);
    }

    public abstract void takeAction();

    public abstract char getType();

    // direction related methods
    public char getLab(){
        return lab;
    }
    public void setDir(int dir){
        this.dir = dir;
    }
    public int getDir(){
        return this.dir;
    }
    
    //To string so you can output a creature to the plotter
    public String toString() {
        //output in (x,y) format
        return ""+this.point.x+" "+this.point.y+" "+lab;
    }
}
