import java.util.*;

import javalib.worldimages.*;
import javalib.colors.*;
import tester.*;

//TO RUN GAME:
//uncomment "testPlayGame" method
//you will not be able to enter 
//keystrokes until maze is done constructing (extra credit)
//hit b for breadth first search
//hit d for depth first search
//hit r to restart the game (new maze)
//hit t to toggle visited paths (extra credit)
//hit h for a horizontal maze (extra credit)
//move the player with the 
//arrow keys (game ends when you reach the end)
//keeps track of every step off of the correct 
//path you take at top of screen (extra credit)
//arrow keys to move
public class ExamplesMazeGame {

    void testPlayGame(Tester t) {
        MazeGame initial = new MazeGame();
        initial.bigBang((MazeGame.WIDTH + 1) * MazeGame.CELL_SIZE + 1,  
                (MazeGame.HEIGHT + 1) * MazeGame.CELL_SIZE + 1,  .00001);
    }




    //tests the methods in the Cell class
    void testCellMethods(Tester t) {
        Cell c = new Cell(-1, -1);
        Cell d = new Cell(-2, -2);
        c.actualCorrectPath = true;
        c.actualWasVisited = true;
        c.bottom = d;
        c.correctPath = true;
        c.current = true;
        c.left = d;
        c.right = d;
        c.size = 0;
        c.toggle = true;
        c.top = d;
        c.wasVisited = true;
        c.initialize();

        //tests the initialize method
        t.checkExpect(c.actualCorrectPath,  false);
        t.checkExpect(c.actualWasVisited,  false);
        t.checkExpect(c.bottom,  c);
        t.checkExpect(c.correctPath,  false);
        t.checkExpect(c.current,  false);
        t.checkExpect(c.left,  c);
        t.checkExpect(c.right,  c);
        t.checkExpect(c.toggle,  false);
        t.checkExpect(c.top,  c);
        t.checkExpect(c.wasVisited,  false);
        t.checkExpect(c.x,  -1);
        t.checkExpect(c.y,  -1);
    }
    //tests the methods in the Util Class
    void testUtilMethods(Tester t) {
        Util u = new Util();
        ArrayList<Edge> toBeTested = new ArrayList<Edge>();



        Cell c = new Cell(-5, -5);
        Edge edge1 = new Edge(c,  c,  0);
        Edge edge2 = new Edge(c,  c,  1);
        Edge edge3 = new Edge(c,  c,  2);

        toBeTested.add(edge2);
        toBeTested.add(edge1);
        toBeTested.add(edge3);

        toBeTested = u.sortByWeight(toBeTested);

        //tests the sortByWeight method
        t.checkExpect(toBeTested.remove(0),  edge1);
        t.checkExpect(toBeTested.remove(0),  edge2);
        t.checkExpect(toBeTested.remove(0),  edge3);


        HashMap<Cell,  Cell> hm = new HashMap<Cell,  Cell>();
        Cell c1 = new Cell(0, 0);
        Cell c2 = new Cell(1, 1);

        hm.put(c1,  c1);
        hm.put(c2,  c1);

        //tests the findParent method
        t.checkExpect(u.findParent(hm,  c1),  c1);
        t.checkExpect(u.findParent(hm,  c2),  c1);


        Cell tl = new Cell(0, 0);
        Cell tr = new Cell(1, 0);
        Cell bl = new Cell(0, 1);
        Cell br = new Cell(1, 1);

        Edge top = new Edge(tl,  tr,  0);
        Edge bottom = new Edge(bl,  br,  0);
        Edge left = new Edge(tl,  bl,  0);
        Edge right = new Edge(tr,  br,  0);

        //before setAdjacent
        t.checkExpect(tl.top,  tl);
        t.checkExpect(tl.bottom,  tl);
        t.checkExpect(tl.left,  tl);
        t.checkExpect(tl.right,  tl);
        t.checkExpect(tr.top,  tr);
        t.checkExpect(tr.bottom,  tr);
        t.checkExpect(tr.left,  tr);
        t.checkExpect(tr.right,  tr);
        t.checkExpect(bl.top,  bl);
        t.checkExpect(bl.bottom,  bl);
        t.checkExpect(bl.left,  bl);
        t.checkExpect(bl.right,  bl);
        t.checkExpect(br.top,  br);
        t.checkExpect(br.bottom,  br);
        t.checkExpect(br.left,  br);
        t.checkExpect(br.right,  br);

        u.setAdjacent(top);
        u.setAdjacent(bottom);
        u.setAdjacent(left);
        u.setAdjacent(right);

        //after setAdjacent
        t.checkExpect(tl.top,  tl);
        t.checkExpect(tl.bottom,  bl);
        t.checkExpect(tl.left,  tl);
        t.checkExpect(tl.right,  tr);
        t.checkExpect(tr.top,  tr);
        t.checkExpect(tr.bottom,  br);
        t.checkExpect(tr.left,  tl);
        t.checkExpect(tr.right,  tr);
        t.checkExpect(bl.top,  tl);
        t.checkExpect(bl.bottom,  bl);
        t.checkExpect(bl.left,  bl);
        t.checkExpect(bl.right,  br);
        t.checkExpect(br.top,  tr);
        t.checkExpect(br.bottom,  br);
        t.checkExpect(br.left,  bl);
        t.checkExpect(br.right,  br);
    }
    //tests the methods in MazeGame
    void testMazeGameMethods(Tester t) {
        MazeGame mg = new MazeGame();
        mg.initialize();
        //tests the initialize method
        t.checkExpect(mg.doneBuilding,  false);
        t.checkExpect(mg.player,  new Player(0, 0)); 
        t.checkExpect(mg.search,  "");
        t.checkExpect(mg.wrongSteps,  0);

        //tests the worldEnds method
        WorldEnd toBeTested = mg.worldEnds();
        t.checkExpect(toBeTested,  new WorldEnd(
                false, 
                new OverlayImages(new TextImage(
                        new Posn(MazeGame.WIDTH * MazeGame.CELL_SIZE / 2,  
                                MazeGame.HEIGHT * MazeGame.CELL_SIZE / 2),
                                "You Win!",  new Red()),  
                                new TextImage(
                                        new Posn(
                                                MazeGame.WIDTH * 
                                                MazeGame.CELL_SIZE / 2,  
                                                (MazeGame.HEIGHT + 5) * 
                                                MazeGame.CELL_SIZE / 2),  
                                                "Number of Wrong Steps Taken: " 
                                                        + mg.wrongSteps, 
                                                        new Red()))));
        MazeGame ended = new MazeGame();
        ended.player.x = MazeGame.WIDTH;
        ended.player.y = MazeGame.HEIGHT;
        toBeTested = ended.worldEnds();
        t.checkExpect(toBeTested,  new WorldEnd(
                true, 
                new OverlayImages(new TextImage(
                        new Posn(MazeGame.WIDTH * MazeGame.CELL_SIZE / 2,  
                                MazeGame.HEIGHT * MazeGame.CELL_SIZE / 2),
                                "You Win!",  new Red()),  
                                new TextImage(new Posn(
                                        MazeGame.WIDTH * 
                                        MazeGame.CELL_SIZE / 2,  
                                        (MazeGame.HEIGHT + 5) * 
                                        MazeGame.CELL_SIZE / 2),  
                                        "Number of Wrong Steps Taken: " 
                                                + mg.wrongSteps,  
                                                new Red()))));

        //tests the constructMaze method
        ArrayList<ArrayList<Cell>> loloc = new ArrayList<ArrayList<Cell>>();
        loloc = mg.board;
        mg.initialize();
        t.checkExpect(mg.board,  loloc);

        //tests reconstruct
        HashMap<Cell,  Cell> reconstructTestHM = new HashMap<Cell,  Cell>();
        Cell reconstructTestC1 = new Cell(0,  0);
        Cell reconstructTestC2 = new Cell(1 , 1);
        reconstructTestHM.put(reconstructTestC1,  reconstructTestC1);
        MazeGame reconstructTestMG = new MazeGame();
        reconstructTestMG.reconstruct(reconstructTestHM,  reconstructTestC2);
        t.checkExpect(reconstructTestC2.correctPath,  true);

        //tests reconstructFindThePath
        HashMap<Cell,  Cell> reconstructFindThePathTestHM = 
                new HashMap<Cell,  Cell>();
        Cell reconstructFindThePathTestC1 = new Cell(0,  0);
        Cell reconstructFindThePathTestC2 = new Cell(1 , 1);
        reconstructFindThePathTestHM.put(
                reconstructFindThePathTestC1,  reconstructFindThePathTestC1);
        MazeGame reconstructFindThePathTestMG = new MazeGame();
        reconstructFindThePathTestMG.reconstructFindThePath(
                reconstructFindThePathTestHM,  reconstructFindThePathTestC2);
        t.checkExpect(reconstructFindThePathTestC2.actualCorrectPath,  true);

        //tests breadthFirstFindThePath
        MazeGame breadthFirstFindThePathMG = new MazeGame();
        Cell breadthFirstFindThePathC1 = new Cell(0,  0);
        Cell breadthFirstFindThePathC2 = new Cell(0, 1);
        breadthFirstFindThePathMG.breadthFirstFindThePath(
                breadthFirstFindThePathC1,  breadthFirstFindThePathC2);
        t.checkExpect(breadthFirstFindThePathC2.actualCorrectPath,  false);

        //tests breadthFirstFind
        MazeGame breadthFirstFindMG = new MazeGame();
        Cell breadthFirstFindC1 = new Cell(0,  0);
        Cell breadthFirstFindC2 = new Cell(0, 1);
        breadthFirstFindMG.worklist.addAtHead(breadthFirstFindC2);
        breadthFirstFindMG.worklist.addAtHead(breadthFirstFindC1);
        breadthFirstFindMG.breadthFirstFind(
                breadthFirstFindC1,  breadthFirstFindC2);
        t.checkExpect(breadthFirstFindC1.correctPath,  false);

        //tests onTick
        MazeGame onTickTestMG = new MazeGame();
        t.checkExpect(onTickTestMG.index,  0);
        onTickTestMG.onTick();
        t.checkExpect(onTickTestMG.index,  1);

        //tests depthFirstFind
        MazeGame depthFirstFindMG = new MazeGame();
        Cell depthFirstFindC1 = new Cell(0,  0);
        Cell depthFirstFindC2 = new Cell(0, 1);
        depthFirstFindMG.worklist.addAtHead(depthFirstFindC2);
        depthFirstFindMG.worklist.addAtHead(depthFirstFindC1);
        depthFirstFindMG.depthFirstFind(depthFirstFindC1,  depthFirstFindC2);
        t.checkExpect(depthFirstFindC1.correctPath,  false);

        //tests onKeyEvent
        MazeGame onKeyTestMG = new MazeGame();
        onKeyTestMG.doneBuilding = true;
        onKeyTestMG.board.get(0).get(0).right = 
                onKeyTestMG.board.get(1).get(0);
        onKeyTestMG.board.get(1).get(0).left = 
                onKeyTestMG.board.get(0).get(0);
        onKeyTestMG.board.get(0).get(0).bottom = 
                onKeyTestMG.board.get(0).get(1);
        onKeyTestMG.board.get(0).get(1).top = 
                onKeyTestMG.board.get(0).get(0);
        onKeyTestMG.board.get(1).get(1).left = 
                onKeyTestMG.board.get(0).get(1);
        onKeyTestMG.board.get(0).get(1).right = 
                onKeyTestMG.board.get(1).get(1);
        onKeyTestMG.board.get(1).get(1).top = 
                onKeyTestMG.board.get(1).get(0);
        onKeyTestMG.board.get(1).get(0).bottom = 
                onKeyTestMG.board.get(1).get(1);

        t.checkExpect(onKeyTestMG.player.x,  0);
        t.checkExpect(onKeyTestMG.player.y,  0);
        onKeyTestMG.onKeyEvent("down");

        t.checkExpect(onKeyTestMG.player.x,  0);
        t.checkExpect(onKeyTestMG.player.y,  1);
        onKeyTestMG.onKeyEvent("right");
        t.checkExpect(onKeyTestMG.player.x,  1);
        t.checkExpect(onKeyTestMG.player.y,  1);
        onKeyTestMG.onKeyEvent("up");
        t.checkExpect(onKeyTestMG.player.x,  1);
        t.checkExpect(onKeyTestMG.player.y,  0);
        onKeyTestMG.onKeyEvent("left");
        t.checkExpect(onKeyTestMG.player.x,  0);
        t.checkExpect(onKeyTestMG.player.y,  0);

        onKeyTestMG.onKeyEvent("b");
        t.checkExpect(onKeyTestMG.search,  "b");

        onKeyTestMG.onKeyEvent("d");
        t.checkExpect(onKeyTestMG.search,  "d");

        onKeyTestMG.onKeyEvent("t");
        t.checkExpect(onKeyTestMG.gameToggle,  true); 
    }
} 
