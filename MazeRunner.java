/**
 * Created by david on 3/15/16.
 */
public class MazeRunner {

//TO RUN GAME:
//you will not be able to enter keystrokes until maze is done constructing
//hit b for breadth first search
//hit d for depth first search
//hit r to restart the game (new maze)
//hit t to toggle visited paths
//hit h for a horizontal maze
//move the player with the
//arrow keys (game ends when you reach the end)
//keeps track of every step off of the correct
//path you take at top of screen
//arrow keys to move



    public static void main(String[] args) {

        MazeGame initial = new MazeGame();
        initial.bigBang((MazeGame.WIDTH + 1) * MazeGame.CELL_SIZE + 1,
                (MazeGame.HEIGHT + 1) * MazeGame.CELL_SIZE + 1,  .00001);

        //takes a while, but once maze is done generating, you can actually do stuff

    }
}
