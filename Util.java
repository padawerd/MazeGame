import java.util.*;

class Util {
    // returns a sorted version of the given list of edges by weight
    ArrayList<Edge> sortByWeight(ArrayList<Edge> ale) {
        ArrayList<Edge> toBeReturned = new ArrayList<Edge>();

        while (ale.size() > 0) {
            int minWeight = -1;
            int indexOfMin = -1;

            for (int i = 0; i < ale.size(); i = i + 1) {
                if (ale.get(i).weight < minWeight || minWeight == -1) {
                    minWeight = ale.get(i).weight;
                    indexOfMin = i;
                }
            }
            toBeReturned.add(ale.remove(indexOfMin));
        }
        return toBeReturned;
    }


    // Produce the topmost cell that is c's parent's parent's .... parent
    Cell findParent(HashMap<Cell, Cell> hm, Cell c) {
        if (hm.get(c).equals(c)) {
            return c;
        } 
        else {
            return this.findParent(hm, hm.get(c));
        }

    }

    //sets the start and end of the edge as 
    //adjacents based on their x and y values
    void setAdjacent(Edge e) {
        if ((e.start.x == e.end.x) && (e.start.y == e.end.y - 1)) {
            e.start.bottom = e.end;
            e.end.top = e.start;
        }
        if ((e.start.x == e.end.x) && (e.start.y == e.end.y + 1)) {
            e.start.top = e.end;
            e.end.bottom = e.start;
        }
        if ((e.start.y == e.end.y) && (e.start.x == e.end.x - 1)) {
            e.start.right = e.end;
            e.end.left = e.start;
        }
        if ((e.start.y == e.end.y) && (e.start.x == e.end.x + 1)) {
            e.start.left = e.end;
            e.end.right = e.start;
        }
    }
}
