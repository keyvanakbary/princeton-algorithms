import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Fast {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        Point[] points = readPoints(args[0]);

        for (Point pivot : points) {
            Point[] copy = copy(points);

            Arrays.sort(copy, pivot.SLOPE_ORDER);

            int j = 0;
            double previous = 0.0;
            LinkedList<Point> collinear = new LinkedList<Point>();
            for(Point p : copy) {
                if (j == 0 || p.slopeTo(pivot) != previous) {
                    if (collinear.size() >= 3) {
                        collinear.add(pivot);
                        Collections.sort(collinear);
                        if (pivot == collinear.getFirst()) printPoints(collinear);
                    }
                    collinear.clear();
                }
                collinear.add(p);

                previous = p.slopeTo(pivot);
                j++;
            }
        }

        StdDraw.show(0);
    }

    private static Point[] copy(Point[] points) {
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) copy[i] = points[i];

        return copy;
    }

    private static Point[] readPoints(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        return points;
    }

    private static void printPoints(LinkedList<Point> points) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < points.size(); i++) {
            buffer.append(points.get(i));
            if (i < points.size() - 1) buffer.append(" -> ");
        }
        StdOut.println(buffer);
        points.getFirst().drawTo(points.getLast());
    }
}
