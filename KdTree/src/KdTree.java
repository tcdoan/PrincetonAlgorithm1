import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * Created by Thanh on 6/25/2017.
 */
public class KdTree
{
    private class Node
    {
        private double key;
        private Point2D value;
        private Node left;
        private Node right;
        private Node(double key, Point2D value, int size)
        {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    int siz;

    // construct an empty set of points
    public KdTree()
    {
        root = null;
        siz = 0;
    }

    // is the set empty?
    public  boolean isEmpty()
    {
        return size() == 0;
    }

    // number of points in the set
    public int size()
    {
        return siz;
    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        siz++;
        root = put(root, p, 0);
    }

    private Node put(Node x, Point2D p, int level)
    {
        double key;
        if(level%2 == 0)
        {
            key = p.x();
        }
        else
        {
            key = p.y();
        }

        if (x == null)
        {
            return new Node(key, p, 1);
        }

        if(!x.value.equals(p)) {
            if (key < x.key)
            {
                x.left = put(x.left, p, level + 1);
            }
            else
           {
                x.right = put(x.right, p, level + 1);
           }
        }
        else
        {
            siz--;
        }
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        Node x = root;
        int level = 0;
        while (x != null)
        {
            if (x.value.equals(p))
            {
                return true;
            }

            double key;
            if (level % 2 == 0)
            {
                key = p.x();
            }
            else
            {
                key = p.y();
            }
            double cmp = key - x.key;
            if (cmp < 0)
            {
                x = x.left;
            }
            else
            {
                x = x.right;
            }
        }
        return false;
    }

    private Iterable<Point2D> values()
    {
            
    }

    // draw all points to standard draw
    public  void draw()
    {



    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect)
    {
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public  Point2D nearest(Point2D p)
    {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args)
    {

    }

}
