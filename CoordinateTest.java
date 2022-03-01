/*
  This file contains a suite of test cases for the Coordinate class we provided you. Please read
  through this *thoroughly* as it contains useful information for when you write your own test
  cases.

  DO NOT MODIFY OR SUBMIT THIS FILE.

  For more information on testing please visit:
  https://www.cis.upenn.edu/~cis121/current/testing_guide.html
*/

/*
  These imports are required for testing. Note that you will have to add the JUnit library to your
  classpath to use these packages. See the linked document in the writeup for more instructions.
*/

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CoordinateTest {

    /**
     * To ensure encapsulation between test cases, one should ensure that these class variables are
     * also private.
     */
    private Coordinate testCoordinate;

    /**
     * Use @Before annotated methods to set up testing variables in advance instead of initializing
     * it for each individual test case. Note that changes made to the variable in one test case
     * do *not* remain when other test cases uses the variable.
     */
    @Before
    public void setupCoordinateObjects() {
        testCoordinate = new Coordinate(3, 5);
    }

    /**
     * We split up getting the X coordinate and Y coordinate as these are two separate cases.
     * To ensure functionality, we made each component have a different value.
     */
    @Test
    public void testGetXCoordinate() {
        assertEquals(3, testCoordinate.getX());
    }

    @Test
    public void testGetYCoordinate() {
        assertEquals(5, testCoordinate.getY());
    }

    /**
     * A common edge case is to test that your code works when the inputs are zeros or null. Here
     * we can't input null values to the constructor, but we test the zero case.
     */
    @Test
    public void testZeroCoordinate() {
        Coordinate zeroCoordinate = new Coordinate(0, 0);
        assertEquals(0, zeroCoordinate.getX());
        assertEquals(0, zeroCoordinate.getY());
    }

    /**
     * Now we test the toString method of the Coordinate class. We test this last because it has
     * a dependency on both the getX and getY methods. To know an error exists within the
     * toString method, you must first know that the getX and getY methods are both
     * functioning properly.
     * <p>
     * Also make sure to note the order of the assertEquals. The first argument is the expected
     * output and the second argument is the output you're testing. *Ensure* you abide by this
     * when testing otherwise your error message will be swapped, and may confuse you.
     */
    @Test
    public void testCoordinateToString() {
        assertEquals("x: 3, y: 5", testCoordinate.toString());
    }

    /**
     * We note that the toString method that takes a string as an argument depends on the default
     * toString method, so we test this next. By doing so we ensure that any bugs we get exist
     * in this method and not in the overrided toString method.
     */
    @Test
    public void testCoordinateToCustomString() {
        assertEquals("My Message: x: 3, y: 5", testCoordinate.toString("My Message:"));
    }

    /**
     * Here's an example of how to test for exceptions with JUnit. Note that when we pass in null
     * as an argument to the toString method it throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullCustomStringThrowsException() {
        testCoordinate.toString(null);
    }

    @Test
    public void testCoordinateIsEqual() {
        // Here we test the first case, that referentially equal objects hold true with our
        // implemented equals method.
        assertEquals(testCoordinate, testCoordinate);
        // Here we test the second case, that a non-referentially equal object still returns
        // true if the coordinate values are identical.
        Coordinate testCoordinateDuplicate = new Coordinate(3, 5);
        assertEquals(testCoordinate, testCoordinateDuplicate);
    }

    /**
     * Here we test that you return false if the object compared with is not a Coordinate object.
     * Also note that you can cast an int to an Integer, making it an object! Extremely useful
     * for when writing test cases.
     */
    @Test
    public void testCoordinateNotEqualsDifferentObject() {
        assertNotEquals(testCoordinate, (Integer) 5);
    }

    /**
     * Note how there are two cases where the equals method could return false, when the x
     * coordinate differs OR the y coordinate differs. You must test *both* to ensure correctness.
     */
    @Test
    public void testCoordinateNotEqualsDifferentCoordinate() {
        Coordinate differentYCoordinate = new Coordinate(3, 6);
        assertNotEquals(testCoordinate, differentYCoordinate);
        Coordinate differentXCoordinate = new Coordinate(4, 5);
        assertNotEquals(testCoordinate, differentXCoordinate);
    }

    /*
      If you run the test cases with code coverage, you should see that your code coverage is at
      100% for the coordinate class! Your code coverage won't always be at 100% but in this class
      you should aim to typically have it be above 90-92%. Also, *do not* think that you're
      finished testing because your code coverage is over 92%. You should think about all the
      possible edge cases that may arise.

      Our cutoff for full credit on unit tests will differ between assignments, but will always
      be in the 90s range. We will not release specific cutoffs.

      Also don't worry about your test coverage results for your test classes
      (e.g., CoordinateTest), they may be low based on the types of test cases
      (i.e., testing for exceptions)
     */
}