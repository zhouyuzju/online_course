/*************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer input.txt
 *  Dependencies: PointSET.java KdTree.java Point2D.java In.java StdDraw.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 *************************************************************************/

public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

//        while (true) {

            // the location (x, y) of the mouse
            double x = .81;
            double y = .30;
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(4);
            kdtree.draw();
//            brute.draw();

            // draw in red the nearest neighbor according to the brute-force algorithm
//            StdDraw.setPenRadius(2);
//            StdDraw.setPenColor(StdDraw.RED);
//            brute.nearest(query).draw();
//            StdDraw.setPenRadius(2);

            // draw in blue the nearest neighbor according to the kd-tree algorithm
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(4);
            kdtree.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
//        }
    }
}
