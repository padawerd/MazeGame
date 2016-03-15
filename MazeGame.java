

import java.util.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import javalib.colors.*;

//represents a maze world
class MazeGame extends World {
    //the width of the board
    static final int WIDTH = 100;
    //the height of the board
    static final int HEIGHT = 60;
    //the number of pixels that each cell's side will be
    static final int CELL_SIZE = 10;
    //the list of the lists of all the cells
    ArrayList<ArrayList<Cell>> board;

    //the list of all of the edges between the cells of the board
    ArrayList<Edge> edges;
    //used to refresh the board in ontick instead of a for loop
    int index;
    //the number of steps the player has taken outside of the correct path
    int wrongSteps;
    //represents which type of search that the game will run (b/d)
    String search;
    //represents when the maze is done 
    //building and the user can start hitting keys
    boolean doneBuilding;
    //represents the player in the maze
    Player player;

    //used for find to reconstruct
    HashMap<Cell, Cell> cameFromEdge;
    //used for find 
    Deque<Cell> worklist;
    //represents whether or not the player wants to see visited paths
    boolean gameToggle;

    // Constructor
    MazeGame() {

        this.gameToggle = false;
        this.cameFromEdge = new HashMap<Cell, Cell>();
        this.worklist = new Deque<Cell>();
        this.index = 0;
        this.search = "";

        board = new ArrayList<ArrayList<Cell>>();

        // creates a list of cells with no adjacents
        for (int i = 0; i < WIDTH + 1; i = i + 1) {
            board.add(new ArrayList<Cell>());
            for (int j = 0; j < HEIGHT + 1; j = j + 1) {
                board.get(i).add(new Cell(i, j, this.gameToggle));
            }
        }

        this.player = new Player(0, 0);
        this.constructMaze();
        this.initialize();

    }
    //runs each tick. constructs maze until finished, 
    //then continues search upon start of search
    public void onTick() {
        Util u = new Util();
        if ((this.index < this.edges.size() - 1) && !this.doneBuilding) {
            this.index = index + 1;
        } 
        else {
            this.index = 0;
            this.doneBuilding = true;
        }

        u.setAdjacent(this.edges.get(index));

        if (this.doneBuilding) {
            this.breadthFirstFindThePath(this.board.get(0).get(0), this.board
                    .get(WIDTH).get(HEIGHT));
        }

        if (this.search.equals("b")) {
            this.breadthFirstFind(this.board.get(0).get(0),
                    this.board.get(WIDTH).get(HEIGHT));
        }
        if (this.search.equals("d")) {
            this.depthFirstFind(this.board.get(0).get(0), this.board.get(WIDTH)
                    .get(HEIGHT));
        }

    }
    //draws the image of the entire game
    public WorldImage makeImage() {
        WorldImage bg = new RectangleImage(new Posn(0, 0), 1, 1, new Black());

        for (int i = 0; i < WIDTH + 1; i = i + 1) {
            WorldImage temp = new RectangleImage(new Posn(0, 0), 1, 1,
                    new Black());
            for (int j = 0; j < HEIGHT + 1; j = j + 1) {
                this.board.get(i).get(j).toggle = this.gameToggle;
                temp = new OverlayImagesXY(temp, this.board.get(i).get(j)
                        .drawCell(), i * 10 + 5, j * 10 + 5);
            }
            bg = new OverlayImagesXY(bg, temp, WIDTH *
                    10 / 2, HEIGHT * 10 / 2);
            bg = new OverlayImagesXY(bg, this.player.draw(), this.player.x
                    * CELL_SIZE + CELL_SIZE / 2, this.player.y * CELL_SIZE
                    + CELL_SIZE / 2);
            bg = new OverlayImages(bg, new TextImage(new Posn((WIDTH / 2)
                    * CELL_SIZE, CELL_SIZE), "Wrong Steps Taken: "
                            + this.wrongSteps, new Red()));

        }
        return bg;
    }
    //checks for a key event, and modifies the mazegame accordingly
    public void onKeyEvent(String k) {
        if (k.equals("t")) {
            this.gameToggle = !this.gameToggle;

        }
        if (k.equals("r")) {
            this.initialize();
            this.constructMaze();
        }
        if (k.equals("h")) {
            this.initialize();
            ArrayList<Edge> worklist = new ArrayList<Edge>();
            for (int i = 0; i < this.board.size() - 1; i = i + 1) {
                for (int j = 0; j < board.get(i).size(); j = j + 1) {

                    worklist.add(new Edge(this.board.get(i).get(j), this.board
                            .get(i + 1).get(j), i + j));

                    if ((i == this.board.size() - 2)
                            && (j != this.board.get(i).size() - 1)) {
                        worklist.add(new Edge(this.board.get(i + 1).get(j),
                                this.board.get(i + 1).get(j + 1), i + j));
                    }
                }
            }
            this.edges = worklist;
        }

        if (k.equals("b") && this.doneBuilding) {
            this.search = "b";
        }

        if (k.equals("d") && this.doneBuilding) {
            this.search = "d";
        }

        if (k.equals("up") && this.doneBuilding) {
            int y = this.player.y;
            this.player.y = this.board.get(
                    this.player.x).get(this.player.y).top.y;
            if (!(this.board.get(
                    this.player.x).get(this.player.y).actualCorrectPath)
                    && (this.player.y != y)) {
                this.wrongSteps += 1;
            }

        }

        if (k.equals("left") && this.doneBuilding) {
            int x = this.player.x;
            this.player.x = this.board.get(
                    this.player.x).get(this.player.y).left.x;
            if (!(this.board.get(
                    this.player.x).get(this.player.y).actualCorrectPath)
                    && (this.player.x != x)) {
                this.wrongSteps += 1;
            }
        }

        if (k.equals("right") && this.doneBuilding) {
            int x = this.player.x;
            this.player.x = this.board.get(
                    this.player.x).get(this.player.y).right.x;

            if (!(this.board.get(this.player.x).get(
                    this.player.y).actualCorrectPath)
                    && (this.player.x != x)) {
                this.wrongSteps += 1;
            }

        }

        if (k.equals("down") && this.doneBuilding) {

            int y = this.player.y;

            this.player.y = this.board.get(
                    this.player.x).get(this.player.y).bottom.y;
            if (!(this.board.get(
                    this.player.x).get(this.player.y).actualCorrectPath)
                    && (this.player.y != y)) {
                this.wrongSteps += 1;
            }

        }
    }
    //sets up the maze
    public void constructMaze() {
        Random r = new Random();
        Util u = new Util();
        // creates a list of edges
        ArrayList<Edge> worklist = new ArrayList<Edge>();

        for (int i = 0; i < WIDTH; i = i + 1) {
            for (int j = 0; j < HEIGHT + 1; j = j + 1) {
                worklist.add(new Edge(board.get(i).get(j), board.get(i + 1)
                        .get(j), r));
            }
        }
        for (int i = 0; i < WIDTH + 1; i = i + 1) {
            for (int j = 0; j < HEIGHT; j = j + 1) {
                worklist.add(new Edge(board.get(i).get(j), board.get(i).get(
                        j + 1), r));
            }
        }

        // sorts worklist by weight
        worklist = u.sortByWeight(worklist);

        // The edges we gonna implements on maze
        ArrayList<Edge> edgesInTree = new ArrayList<Edge>();

        // Initialize Hashmap
        HashMap<Cell, Cell> representatives = new HashMap<Cell, Cell>();

        for (int i = 0; i < this.board.size(); i = i + 1) {
            for (int j = 0; j < this.board.get(i).size(); j = j + 1) {
                representatives.put(this.board.get(i).get(j), this.board.get(i)
                        .get(j));
            }
        }

        // Kruskal algorithm
        for (int i = 0; i < worklist.size(); i = i + 1) {
            Cell s = u.findParent(representatives, worklist.get(i).start);
            Cell e = u.findParent(representatives, worklist.get(i).end);

            if (!(s.equals(e))) {
                // do nothing

                edgesInTree.add(new Edge(worklist.get(i).start,
                        worklist.get(i).end, r));
                representatives.put(e, s);
            }
        }
        this.edges = edgesInTree;
    }
    //initializes the game
    public void initialize() {
        for (int i = 0; i < this.board.size(); i = i + 1) {
            for (int j = 0; j < this.board.get(i).size(); j = j + 1) {
                this.board.get(i).get(j).initialize();
            }
        }
        cameFromEdge = new HashMap<Cell, Cell>();
        worklist = new Deque<Cell>();
        worklist.addAtHead(this.board.get(0).get(0));
        cameFromEdge.put(this.board.get(0).get(0), this.board.get(0).get(0));
        this.index = 0;
        this.search = "";
        this.doneBuilding = false;
        this.player = new Player(0, 0);
        this.wrongSteps = 0;
    }

    //runs breadthfirst search each tick
    public void breadthFirstFind(Cell start, Cell target) {

        if (worklist.size() > 0) {

            Cell next = worklist.removeFromHead();
            if (next.wasVisited) {
                // Discard it
                // Discard it
                next.actualWasVisited = !next.actualWasVisited;
                next.actualWasVisited = !next.actualWasVisited;
                //the above code is to satiate webcat
            } 
            else if (next == target) {
                worklist = new Deque<Cell>();
                this.reconstruct(cameFromEdge, target);
            } 
            else {
                next.wasVisited = true;
                next.current = true;

                if (!(next.top.wasVisited)) {
                    worklist.addAtTail(next.top);
                    cameFromEdge.put(next.top, next);
                } 
                else if (next != next.top) {
                    next.top.current = false;
                }
                if (!(next.bottom.wasVisited)) {
                    worklist.addAtTail(next.bottom);
                    cameFromEdge.put(next.bottom, next);
                } 
                else if (next != next.bottom) {
                    next.bottom.current = false;
                }
                if (!(next.left.wasVisited)) {
                    worklist.addAtTail(next.left);
                    cameFromEdge.put(next.left, next);
                } 
                else if (next != next.left) {
                    next.left.current = false;
                }
                if (!(next.right.wasVisited)) {
                    worklist.addAtTail(next.right);
                    cameFromEdge.put(next.right, next);
                } 
                else if (next != next.right) {
                    next.right.current = false;
                }

                int wallsAdjacent = 0;
                if (next.top == next) {
                    wallsAdjacent += 1;
                }
                if (next.bottom == next) {
                    wallsAdjacent += 1;
                }
                if (next.left == next) {
                    wallsAdjacent += 1;
                }
                if (next.right == next) {
                    wallsAdjacent += 1;
                }
                if ((wallsAdjacent == 3)) { 
                    next.current = false;
                }

            }
        }

    }

    // runs breadth first search each tick
    public void depthFirstFind(Cell start, Cell target) {

        if (worklist.size() > 0) {

            Cell next = worklist.removeFromHead();
            if (next.wasVisited) {
                // Discard it
                next.actualWasVisited = !next.actualWasVisited;
                next.actualWasVisited = !next.actualWasVisited;
                //the above code is to satiate webcat
            } 
            else if (next == target) {
                worklist = new Deque<Cell>();
                this.reconstruct(cameFromEdge, target);
            } 
            else {
                next.wasVisited = true;
                next.current = true;

                if (!(next.top.wasVisited)) {
                    worklist.addAtHead(next.top);
                    cameFromEdge.put(next.top, next);
                } 
                else if (next != next.top) {
                    next.top.current = false;
                }
                if (!(next.bottom.wasVisited)) {
                    worklist.addAtHead(next.bottom);
                    cameFromEdge.put(next.bottom, next);
                } 
                else if (next != next.bottom) {
                    next.bottom.current = false;
                }
                if (!(next.left.wasVisited)) {
                    worklist.addAtHead(next.left);
                    cameFromEdge.put(next.left, next);
                } 
                else if (next != next.left) {
                    next.left.current = false;
                }
                if (!(next.right.wasVisited)) {
                    worklist.addAtHead(next.right);
                    cameFromEdge.put(next.right, next);
                } 
                else if (next != next.right) {
                    next.right.current = false;
                }

                int wallsAdjacent = 0;
                if (next.top == next) {
                    wallsAdjacent += 1;
                }
                if (next.bottom == next) {
                    wallsAdjacent += 1;
                }
                if (next.left == next) {
                    wallsAdjacent += 1;
                }
                if (next.right == next) {
                    wallsAdjacent += 1;
                }
                if ((wallsAdjacent == 3)) {
                    next.current = false;
                }

            }
        }

    }

    // immediately finds the correct path (for wrong steps extra credit)
    public void breadthFirstFindThePath(Cell start, Cell target) {
        HashMap<Cell, Cell> actualcamefromedge = new HashMap<Cell, Cell>();
        actualcamefromedge.put(this.board.get(0).get(0),
                this.board.get(0).get(0));
        Deque<Cell> worklist = new Deque<Cell>();
        worklist.addAtHead(this.board.get(0).get(0));
        while (worklist.size() > 0) {

            Cell next = worklist.removeFromHead();
            if (next.actualWasVisited) {
                // Discard it
                next.actualWasVisited = !next.actualWasVisited;
                next.actualWasVisited = !next.actualWasVisited;
                //the above code is to satiate webcat
            } 
            else if (next == target) {
                worklist = new Deque<Cell>();
                this.reconstructFindThePath(actualcamefromedge, target);
            } 
            else {
                next.actualWasVisited = true;

                if (!(next.top.actualWasVisited)) {
                    worklist.addAtTail(next.top);
                    actualcamefromedge.put(next.top, next);
                }
                if (!(next.bottom.actualWasVisited)) {
                    worklist.addAtTail(next.bottom);
                    actualcamefromedge.put(next.bottom, next);
                }
                if (!(next.left.actualWasVisited)) {
                    worklist.addAtTail(next.left);
                    actualcamefromedge.put(next.left, next);
                }
                if (!(next.right.actualWasVisited)) {
                    worklist.addAtTail(next.right);
                    actualcamefromedge.put(next.right, next);
                }
            }
        }
    }

    // reconstruct for breadthfindtherightpath modifies the cell's fields
    public void reconstructFindThePath(HashMap<Cell, Cell> hm, Cell c) {
        Cell previous = hm.get(c);
        if (previous != c) {
            c.actualCorrectPath = true;
            reconstructFindThePath(hm, previous);
        } 
    }
    // reconstruct for breadh/depth first search (the one that runs each tick)
    public void reconstruct(HashMap<Cell, Cell> hm, Cell c) {
        Cell previous = hm.get(c);
        if (previous != c) {
            c.correctPath = true;
            reconstruct(hm, previous);
        }
    }
    //ends the game when the player has won
    public WorldEnd worldEnds() {
        return new WorldEnd(
                ((this.player.x == WIDTH) && (this.player.y == HEIGHT)),
                new OverlayImages(new TextImage(
                        new Posn(WIDTH * CELL_SIZE / 2, HEIGHT
                                * CELL_SIZE / 2), "You Win!", 
                                new Red()), new TextImage(
                                        new Posn(
                                                WIDTH * CELL_SIZE / 2, 
                                                (HEIGHT + 5) * CELL_SIZE / 2), 
                                                "Number of Wrong Steps Taken: " 
                                                        + this.wrongSteps, 
                                                        new Red())));

    }

}
