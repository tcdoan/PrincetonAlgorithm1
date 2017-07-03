import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.ResizingArrayQueue;

/**
 * PointSET represents a set of points in the unit square.
 * Implement the following API by using a red–black BST.
 */
public class PointSET
{
    private RedBlackBST<Point2D, Point2D> points;

    // construct an empty set of points
    public PointSET()
    {
        points = new RedBlackBST<>();
    }

    // is the set empty?
    public  boolean isEmpty()
    {
        return points == null || points.size() == 0;
    }

    // number of points in the set
    public int size()
    {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        if (!points.contains(p))
        {
            points.put(p, p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw()
    {
        for (Point2D p : points.keys())
        {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
        {
            throw new IllegalArgumentException();
        }

        ResizingArrayQueue<Point2D> queue = new ResizingArrayQueue<>();
        for (Point2D p : points.keys())
        {
            if (rect.contains(p))
            {
                queue.enqueue(p);
            }
        }
        return queue;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        double minDistance = Double.MAX_VALUE;
        Point2D minDistancePoint = null;

        for (Point2D x : points.keys())
        {
            if (x.distanceTo(p) < minDistance)
            {
                minDistance = x.distanceTo(p);
                minDistancePoint = x;
            }
        }
        return minDistancePoint;
    }

}
