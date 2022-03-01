
public class MazeSolverImpl {

    /**
     * You should write your code within this method. A good rule of thumb, especially with
     * recursive problems like this, is to write your input exception handling within this
     * method and then use helper methods to carry out the actual recursion.
     * <p>
     * How you set up the recursive methods is up to you, but note that since this is a *static*
     * method, all helper methods that it calls must *also* be static. Make them package-private
     * (i.e. without private or public modifiers) so you can test them individually.
     *
     * @param maze See the writeup for more details about the format of the input maze.
     * @param sourceCoord The source (starting) coordinate
     * @param goalCoord The goal (ending) coordinate
     * @return a matrix of the same dimension as the input maze containing the solution
     * path marked with 1's, or null if no path exists. See the writeup for more details.
     * @throws IllegalArgumentException in the following instances:
     * 1. If the maze is null
     * 2. If m * n <= 1 where m and n are the dimensions of the maze
     * 3. If either the source coordinate OR the goal coordinate are out of the matrix bounds.
     * 4. If your source or goal coordinate are on a blocked tile.
     */

    private static int numRows;
    private static int numCols;
    private static int[][] originalMaze;

    public static int[][] solveMaze(int[][] maze, Coordinate sourceCoord, Coordinate goalCoord) {

        // handle exceptions
        if (maze == null) {
            throw new IllegalArgumentException();
        }

        numRows = maze.length;
        if (numRows == 0) {
            throw new IllegalArgumentException();
        }
        numCols = maze[0].length;

        if (numRows * numCols <= 1) {
            throw new IllegalArgumentException();
        }
        if (outOfBounds(sourceCoord, numRows, numCols) ||
                outOfBounds(goalCoord, numRows, numCols)) {
            throw new IllegalArgumentException();
        }
        if (maze[sourceCoord.getY()][sourceCoord.getX()] == 1) {
            throw new IllegalArgumentException();
        }
        if (maze[goalCoord.getY()][goalCoord.getX()] == 1) {
            throw new IllegalArgumentException();
        }

        numRows = maze.length;
        numCols = maze[0].length;

        originalMaze = maze;
        int[][] startingPath = new int[numRows][numCols];
        startingPath[sourceCoord.getY()][sourceCoord.getX()] = 1;

        if (sourceCoord.equals(goalCoord)) {
            return startingPath;
        }

        return getNext(sourceCoord, goalCoord, startingPath); // calling recursive function
    }

    static int[][] getNext(Coordinate currentCoord, Coordinate goalCoord, int[][] currentPath) {

        Coordinate up = new Coordinate(currentCoord.getX(), currentCoord.getY() - 1);
        Coordinate down = new Coordinate(currentCoord.getX(), currentCoord.getY() + 1);
        Coordinate left = new Coordinate(currentCoord.getX() - 1, currentCoord.getY());
        Coordinate right = new Coordinate(currentCoord.getX() + 1, currentCoord.getY());

        if (currentCoord.getX() == goalCoord.getX() && currentCoord.getY() == goalCoord.getY()) {
            return currentPath;
        }
        if (validNext(down, currentPath)) {
            currentPath[down.getY()][down.getX()] = 1;
            if  (getNext(down, goalCoord, currentPath) != null) {
                return currentPath;
            } else {
                currentPath[down.getY()][down.getX()] = 0;
            }
        }
        if (validNext(up, currentPath)) {
            currentPath[up.getY()][up.getX()] = 1;
            if (getNext(up, goalCoord, currentPath) != null) {
                return currentPath;
            } else {
                currentPath[up.getY()][up.getX()] = 0;
            }
        }
        if (validNext(left, currentPath)) {
            currentPath[left.getY()][left.getX()] = 1;
            if (getNext(left, goalCoord, currentPath) != null) {
                return currentPath;
            } else {
                currentPath[left.getY()][left.getX()] = 0;
            }
        }
        if (validNext(right, currentPath)) {
            currentPath[right.getY()][right.getX()] = 1;
            if (getNext(right, goalCoord, currentPath) != null) {
                return currentPath;
            } else {
                currentPath[right.getY()][right.getX()] = 0;
            }
        }
        return null;
    }

    static boolean validNext(Coordinate coord, int[][] currPath) {
        if (outOfBounds(coord, numRows, numCols)) {
            return false;
        } else if (originalMaze[coord.getY()][coord.getX()] == 1
                || currPath[coord.getY()][coord.getX()] == 1) {
            return false;
        }
        return true;
    }

    static boolean outOfBounds(Coordinate anyCoord, int numRows, int numCols) {
        if (anyCoord.getX() < 0 || anyCoord.getX() >= numCols) {
            return true;
        } else if (anyCoord.getY() < 0 || anyCoord.getY() >= numRows) {
            return true;
        }

        return false;
    }
}
