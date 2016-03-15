import javalib.worldimages.*;
import javalib.colors.*;
import java.awt.Color;


//represents a UNIQUE cell *NOTE: WE USE == TO COMPARE CELLS BECAUSE
//WE WERE VERY CAREFUL TO ONLY INITIALIZE EACH CELL ONCE
//(Lerner Approved)
class Cell {
    //represents the cell's x coordinate
    int x;
    //represents the cell's y coordinate
    int y;
    //represents the cell above this cell
    Cell top;
    //represents the cell below this cell
    Cell bottom;
    //represents the cell to the left of this cell
    Cell left;
    //represents the cell to the right of this cell
    Cell right;
    //represents the size that this cell will be drawn as
    int size;
    //represents whether this cell has been visited by bfs/dfs
    boolean wasVisited;
    //represents whether this cell is part of the correct path to the end
    boolean correctPath;
    //represents whether this cell is the head of the path being found currently
    boolean current;
    //represents whether the user wants to view visited paths or not
    boolean toggle;
    //represents if this cell is part of the correct
    //path to the end (for wrong steps)
    boolean actualCorrectPath;
    //represents if this cell was visited (for wrong steps extra credit)
    boolean actualWasVisited;

    //constructor
    Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.initialize();
    }
    //constructor
    Cell(int x, int y, boolean toggle) {
        this.x = x;
        this.y = y;
        this.initialize();
    }
    //draws the image of this cell
    WorldImage drawCell() {
        Color c = new Color(255, 255, 255);

        // if the cell is not visited or on the correct path
        if (!(this.wasVisited) || (this.correctPath)) {
            c = new Color(192, 192, 192);
        }

        // if the cell has been visited and is not on the correct path
        if ((this.wasVisited) && !(this.correctPath)) {
            c = new Color(0, 148, 189);
        }
        // if the cell is the current head of the search
        if (this.current) {
            c = new Color(255, 0, 0);
        }
        // if the cell is part of the shortest path
        if (this.correctPath) {
            c = new Color(255, 102, 0);
        }
        // if the user does not want to view visited paths
        if (this.toggle && !(this.correctPath)) {
            c = new Color(192, 192, 192);
        }
        // if this cell is the start of the maze
        if (this.x == 0 && this.y == 0) {

            c = new Color(0, 200, 0);
        }
        // if this cell is the end of the maze 
        if (this.x == MazeGame.WIDTH && this.y == MazeGame.HEIGHT) {
            c = new Color(200, 0, 200);
        }

        WorldImage toBeReturned = new RectangleImage(
                new Posn(this.x * this.size + this.size / 2, this.y * this.size
                        + this.size / 2), this.size, this.size, c);

        // draws walls

        if (this.left == this) {
            WorldImage leftWall = new LineImage(new Posn(this.x * this.size,
                    this.y * this.size), new Posn(this.x * this.size, this.y
                            * this.size + this.size), new Black());
            toBeReturned = new OverlayImagesXY(toBeReturned, leftWall, 0, 0);
        }
        if (this.right == this) {
            WorldImage rightWall = new LineImage(new Posn(this.x * this.size
                    + this.size, this.y * this.size), new Posn(this.x
                            * this.size + this.size, this.y * 
                            this.size + this.size),
                            new Black());
            toBeReturned = new OverlayImagesXY(toBeReturned, rightWall, 0, 0);
        }
        if (this.top == this) {
            WorldImage topWall = new LineImage(new Posn(this.x * this.size,
                    this.y * this.size), new Posn(this.x * this.size
                            + this.size, this.y * this.size), new Black());
            toBeReturned = new OverlayImagesXY(toBeReturned, topWall, 0, 0);
        }
        if (this.bottom == this) {
            WorldImage bottomWall = new LineImage(new Posn(this.x * this.size,
                    this.y * this.size + this.size), new Posn(this.x
                            * this.size + this.size, 
                            this.y * this.size + this.size),
                            new Black());
            toBeReturned = new OverlayImagesXY(toBeReturned, bottomWall, 0, 0);
        }

        return toBeReturned;
    }
    // EFFECT: initialize everything (for use on "r" keyevent)
    void initialize() {
        this.size = MazeGame.CELL_SIZE;
        this.wasVisited = false;
        this.correctPath = false;
        this.current = false;
        this.toggle = false;
        this.actualCorrectPath = false;
        this.actualWasVisited = false;
        this.top = this;
        this.bottom = this;
        this.right = this;
        this.left = this;
    }
}