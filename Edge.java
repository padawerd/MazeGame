import java.util.*;

//represents an edge between 2 cells
public class Edge {
    //represents the cell that the edge starts at
    Cell start;
    //represents the cell that the edge ends at
    Cell end;
    //represents the weight of the edge
    int weight;

    //constructor
    Edge(Cell start, Cell end, Random r) {
        this.start = start;
        this.end = end;
        this.weight = r.nextInt(MazeGame.WIDTH * MazeGame.HEIGHT);

    }
    //constructor
    Edge(Cell start, Cell end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}








