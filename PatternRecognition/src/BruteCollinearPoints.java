import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/*
    TODO:
    1. Remove tests for exact floating-point equality.
 */
public class BruteCollinearPoints
{
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        if (points == null)
        {
            throw new NullPointerException("argument is null");
        }

        for (int i = 0; i < points.length; i++)
        {
            if (points[i] == null) throw new java.lang.NullPointerException("point" + i + " is null");
        }

        segments = new ArrayList<>();
        if (points.length < 4)
        {
            return;
        }

        Point[] copies = new  Point[points.length];
        // Check for dups and copy points to copies array
        Set<Point> pointSet = new TreeSet<>();
        for (int i = 0; i < points.length; i++)
        {
            copies[i] = points[i];
            if (pointSet.contains(points[i]))
            {
                throw new IllegalArgumentException();
            }
            pointSet.add(points[i]);
        }
        pointSet.clear();

        Arrays.sort(copies);
        for (int x1 = 0; x1 < copies.length-3; x1++)
        {
            for (int x2 = x1+1; x2 < copies.length-2; x2++)
            {
                for (int x3= x2+1; x3 < copies.length-1; x3++)
                {
                    double pq = copies[x1].slopeTo(copies[x2]);
                    double pr = copies[x1].slopeTo(copies[x3]);
                    if (pq == pr)
                    {
                        for (int x4 = x3 + 1; x4 < copies.length; x4++)
                        {
                            double ps = copies[x1].slopeTo(copies[x4]);
                            if (pq == ps)
                            {
                                LineSegment segment = new LineSegment(copies[x1], copies[x4]);
                                segments.add(segment);
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return segments.size();
    }

    public LineSegment[] segments()
    {
        return segments.toArray(new LineSegment[segments.size()]);
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
