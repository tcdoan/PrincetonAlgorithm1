import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
    TODO:
    1. Remove tests for exact floating-point equality.
 */
public class FastCollinearPoints
{
    private ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more pointsv
    public FastCollinearPoints(Point[] points)
    {
        if (points == null)
        {
            throw new NullPointerException("argument is null");
        }

        for (int i = 0; i < points.length; i++)
        {
            if (points[i] == null) throw new java.lang.NullPointerException("point" + i + " is null");
        }

        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++)
        {
            aux[i] = points[i];
        }
        Arrays.sort(aux);
        for (int i  = 1; i < aux.length; i++)
        {
            if (aux[i].compareTo(aux[i-1]) == 0)
            {
                throw  new java.lang.IllegalArgumentException("constructor contains a repeated point");
            }
        }

        segments = new ArrayList<>();
        if (points.length < 4)
        {
            return;
        }

        for (int i = 0; i < points.length; i++)
        {
            Point p = points[i];
            Comparator<Point> c = p.slopeOrder();

            Arrays.sort(aux);
            Arrays.sort(aux, c);

            for (int j = 1; j < aux.length-2; j++)
            {
                if (p.slopeTo(aux[j]) == p.slopeTo(aux[j+1]) && p.slopeTo(aux[j]) == p.slopeTo(aux[j+2]))
                {
                    ArrayList<Point> points4LineSegment = new ArrayList<>();
                    points4LineSegment.add(p);
                    points4LineSegment.add(aux[j]);
                    points4LineSegment.add(aux[j+1]);
                    points4LineSegment.add(aux[j+2]);
                    double slope = p.slopeTo(aux[j]);
                    int k = j+3;
                    while (k < aux.length && p.slopeTo(aux[k]) == slope)
                    {
                        points4LineSegment.add(aux[k]);
                        k++;
                    }
                    if (isSorted(points4LineSegment))
                    {
                        LineSegment segment = new LineSegment(p, aux[k-1]);
                        segments.add(segment);
                    }
                    j = k-1;
                }
            }
        }

    }

    private boolean isSorted(ArrayList<Point> points4LineSegment)
    {
        for (int i  = 0; i < points4LineSegment.size()-1; i++)
        {
            if (points4LineSegment.get(i).compareTo(points4LineSegment.get(i+1)) > 0)
            {
                return false;
            }
        }
        return true;
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return segments.size();
    }

    // the line segments
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments())
        {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
