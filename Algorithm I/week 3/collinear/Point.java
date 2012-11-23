import java.util.Comparator;


import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    /**
     * create the point (x, y)
     * @param x
     * @param y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * plot this point to standard drawing
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * draw line between this point and that point to standard drawing
     * @param that
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * slope between this point and that point
     * @param that
     * @return
     */
    public double slopeTo(Point that) {
    	if(that.y == this.y && that.x == this.x)
    		return Double.NEGATIVE_INFINITY;
    	else if(that.x == this.x)
    		return Double.POSITIVE_INFINITY;
    	else if(that.y == this.y)
    		return (1.0 - 1.0) / 1.0;
    	else
    		return (that.y - this.y) * 1.0 / (that.x - this.x);
    }

    /**
     * is this point lexicographically smaller than that one?
     * comparing y-coordinates and breaking ties by x-coordinates
     */
    public int compareTo(Point that) {
        if(this.y < that.y)
        	return -1;
        else if(this.y == that.y && this.x < that.x)
        	return -1;
        else if(this.y == that.y && this.x == that.x)
        	return 0;
        else
        	return 1;
    }

    /**
     * return string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
	 *	comparator implement SLOPE_ORDER
     */
    private class SlopeOrder implements Comparator<Point>{
    	public int compare(Point a,Point b){
    		double slopeToA = Point.this.slopeTo(a);
    		double slopeToB = Point.this.slopeTo(b);
    		if(slopeToA < slopeToB)
    			return -1;
    		else if(slopeToA == slopeToB)
    			return 0;
    		else
    			return 1;
    	}
    }
    
    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
