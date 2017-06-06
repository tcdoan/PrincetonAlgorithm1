import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints
{
    private LineSegment[] segments;
    private int numberOfSegments;

    // finds all line segments containing 4 or more pointsv
    public FastCollinearPoints(Point[] points)
    {
        if (points == null)
        {
            throw new NullPointerException("argument is null");
        }

        for (Point p: points)
        {
            if (p == null) throw new java.lang.NullPointerException("a point is null");
        }

        for (int i  = 1; i < points.length; i++)
        {
            if (points[i].compareTo(points[i-1]) == 0)
            {
                throw  new java.lang.IllegalArgumentException("constructor contains a repeated point");
            }
        }

        if (points.length < 4)
        {
            numberOfSegments = 0;
            segments = new LineSegment[0];
            return;
        }


        Arrays.sort(points);

        Point[] aux = new Point[points.length];
        for (int i = 0; i < points.length; i++)
        {
            aux[i] = points[i];
        }

        LinkedQueue<LineSegment> queue = new LinkedQueue<>();
        for (int i = 0; i < points.length; i++)
        {
            Point p = points[i];
            Comparator<Point> c = p.slopeOrder();
            Arrays.sort(aux, c);
            for (int j = 1; j < aux.length-2; j++)
            {
                if (p.slopeTo(aux[j]) == p.slopeTo(aux[j+1]) && p.slopeTo(aux[j]) == p.slopeTo(aux[j+2]))
                {
                    double slope = p.slopeTo(aux[j]);
                    int k = j+2;
                    while (k < aux.length && p.slopeTo(aux[k]) == slope)
                    {
                        k++;
                    }
                    LineSegment segment = new LineSegment(p, aux[k-1]);
                    queue.enqueue(segment);
                    j = k;
                }
            }
        }

        numberOfSegments = queue.size();
        segments = new LineSegment[numberOfSegments];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments())
        {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
