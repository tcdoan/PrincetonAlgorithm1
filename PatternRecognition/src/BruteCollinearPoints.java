import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints
{
    private LineSegment[] segments;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        if (points == null)
        {
            throw new NullPointerException("argument is null");
        }
        if (points.length < 4)
        {
            numberOfSegments = 0;
            segments = new LineSegment[0];
            return;
        }

        Arrays.sort(points);
        LinkedQueue<LineSegment> queue = new LinkedQueue<>();
        for (int x1 = 0; x1 < points.length-3; x1++)
        {
            for (int x2 = x1+1; x2 < points.length-2; x2++)
            {
                for (int x3= x2+1; x3 < points.length-1; x3++)
                {
                    double pq = points[x1].slopeTo(points[x2]);
                    double pr = points[x1].slopeTo(points[x3]);
                    if (pq == pr)
                    {
                        for (int x4 = x3 + 1; x4 < points.length; x4++)
                        {
                            double ps = points[x1].slopeTo(points[x4]);
                            if (pq == ps)
                            {
                                numberOfSegments += 1;
                                LineSegment segment = new LineSegment(points[x1], points[x4]);
                                queue.enqueue(segment);
                            }
                        }
                    }
                }
            }
        }
        segments = new LineSegment[queue.size()];
        int i = 0;
        for (LineSegment segment: queue)
        {
            segments[i++] = segment;
        }
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments()
    {
        return segments;
    }

    public static void main(String[] args)
    {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points)
        {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments())
        {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
