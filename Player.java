import java.util.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import javalib.colors.*;
import java.awt.Color;

//represents the movable player
class Player {
    //represents the player's x coordinate
    int x;
    //represents the player's y coordinate
    int y;

    //constructor
    Player(int x,  int y) {
        this.x = x;
        this.y = y;
    }
    //draws the image of the player
    WorldImage draw() {
        return new RectangleImage(
                new Posn(
                        this.x * MazeGame.CELL_SIZE + MazeGame.CELL_SIZE / 2,  
                        this.y * MazeGame.CELL_SIZE + MazeGame.CELL_SIZE / 2),  
                        MazeGame.CELL_SIZE ,  
                        MazeGame.CELL_SIZE,  
                        new Color(255, 255, 255));
    }



}
