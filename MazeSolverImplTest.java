import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MazeSolverImplTest {

    private int[][] smallWriteupMaze;
    private int[][] bigWriteupMaze;

    @Before
    public void setupTestMazes() {
        smallWriteupMaze = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 0}
        };

        bigWriteupMaze = new int[][]{
                {0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 1, 0, 1, 1, 0},
                {1, 1, 0, 1, 1, 0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 1, 0, 0, 0, 1, 0},
                {0, 1, 1, 0, 0, 1, 1, 0, 1, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

    }

    @Test
    public void testReturnsSmallMazeSolutionPathInWriteup() {
        int[][] solutionPath = {
                {1, 1, 1, 0},
                {0, 0, 1, 0},
                {1, 1, 1, 0},
                {1, 1, 0, 0}
        };
        assertArrayEquals(solutionPath, MazeSolverImpl.solveMaze(smallWriteupMaze,
                new Coordinate(0, 0), new Coordinate(0, 2)));
    }

    @Test
    public void testReturnsBigMazeSolutionPathInWriteup() {
        int[][] bigWriteupSolution = new int[][]{
                {1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int[][] returnedPath = MazeSolverImpl.solveMaze(bigWriteupMaze, new Coordinate(2, 0),
                new Coordinate(4, 0));
        assertArrayEquals(bigWriteupSolution, returnedPath);
    }

    /*
      Note: the above tests are the ones included in the writeup and NOT exhaustive. The autograder
      uses other test cases not listed above. Please thoroughly read all stub files, including
      CoordinateTest.java!

      For help with creating test cases, please see this link:
      https://www.seas.upenn.edu/~cis121/current/testing_guide.html
     */

    @Test
    public void testCoordinateOutOfBounds() {
        Coordinate c = new Coordinate(4, 1);
        assertTrue(MazeSolverImpl.outOfBounds(c, 4, 4));
    }

    @Test
    public void testCoordinateNotOutOfBounds() {
        Coordinate c = new Coordinate(3, 3);
        assertFalse(MazeSolverImpl.outOfBounds(c, 4, 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullArray() {
        MazeSolverImpl.solveMaze(null, new Coordinate(2, 0), new Coordinate(0,0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSourceOutOfBounds() {
        MazeSolverImpl.solveMaze(smallWriteupMaze,
                new Coordinate(-1, 0), new Coordinate(0,0));
    }

    @Test
    public void noPathReturnsNull() {
        assertNull(MazeSolverImpl.solveMaze(smallWriteupMaze,
                new Coordinate(0,0), new Coordinate(3, 3)));
    }

    @Test
    public void noLegalMoves() {
        assertNull(MazeSolverImpl.solveMaze(smallWriteupMaze,
                new Coordinate(0,0), new Coordinate(3,3)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyArray() {
        int[][] arr = new int[0][0];
        MazeSolverImpl.solveMaze(arr, new Coordinate(0,0), new Coordinate(0,0));
    }

    @Test
    public void startingIsGoal() {
        int[][] solutionPath = {
                {0, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        assertArrayEquals(solutionPath, MazeSolverImpl.solveMaze(smallWriteupMaze,
                new Coordinate(1,0), new Coordinate(1, 0)));
    }
    @Test(expected = IllegalArgumentException.class)
    public void sourceIsBlocked() {
        MazeSolverImpl.solveMaze(smallWriteupMaze,
                new Coordinate(0, 1), new Coordinate(0,0));
    }

}
