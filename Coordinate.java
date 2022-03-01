/**
 * The Coordinate class is a helper class to assist in your solution in MazeSolverImpl. You may
 * use it as you see fit in your MazeSolverImpl solution but *DO NOT* modify the class otherwise
 * it may interfere with our test cases.
 * <p>
 * DO NOT MODIFY OR SUBMIT THIS FILE.
 */
public class Coordinate {

    // Notice how these are *private*. To ensure encapsulation, make all class variables private.
    // If you'd like to access them in your test files, you should instead make them 
    // *package private*, which means dropping the 'private' modifier, or use getter methods.
    private int x, y;

    /**
     * Constructor for the Coordinate class.
     *
     * @param x The x component of the coordinate
     * @param y The y component of the coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method returns the x component of the coordinate. By having a getter method instead of
     * making x public we enforce encapsulation. If you want to have a different x component,
     * you would have to create an entirely new Coordinate object.
     *
     * @return The x component of the coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return The y component of the coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Every Object has a built in "toString" method that you can override. This is incredibly
     * useful for debugging purposes.
     *
     * @return A String representation of the Coordinate object. Note that we use the String.format
     * method to more easily construct strings.
     */
    @Override
    public String toString() {
        return String.format("x: %d, y: %d", this.getX(), this.getY());
    }

    /**
     * Java's default implementation of equals() will only return true if two references point to
     * the same object in memory as it compares memory locations with == operator rather than
     * comparing contents. If two objects having same data are stored at different locations in
     * memory, the default implementation will return false.
     * <p>
     * Therefore, when we define our own data types (classes), we need to override equals().
     * <p>
     * More info can be found at:
     * https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-
     *
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Coordinate)) {
            return false;
        }

        Coordinate coord = (Coordinate) obj;

        return this.x == coord.getX() && this.y == coord.getY();
    }

    /**
     * We've provided this custom toString method to show you how to test with exceptions! You
     * can still choose to use this for debugging purposes if you would like.
     * <p>
     * Also note that this method has the same name as the default toString method but doesn't
     * conflict because it has a different method signature. This is called "method overloading",
     * a useful concept to know in Java programming!
     *
     * @param message String to be pre-pended onto the default Coordinate toString.
     * @return A String representation of the Coordinate object appended to the given message.
     * @throws IllegalArgumentException if the inputted message is null.
     */
    public String toString(String message) {
        if (message == null) {
            // You can add a custom exception message to make debugging easier!
            throw new IllegalArgumentException("Message String cannot be null!");
        }

        return String.format("%s %s", message, this.toString());
    }
}
