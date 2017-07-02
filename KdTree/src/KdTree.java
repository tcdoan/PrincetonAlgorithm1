import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class KdTree {

    private class Node {
        private double key;

        // the point
        private Point2D value;

        // the left/bottom subtree
        private Node left;

        // the right/top subtree
        private Node right;

        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;

        private Node(double key, Point2D value, RectHV rect) {
            this.key = key;
            this.value = value;
            this.rect = rect;
        }
    }

    private Node root;
    int siz;

    // construct an empty set of points
    public KdTree() {
        root = null;
        siz = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return siz;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        siz++;
        root = put(root, null, p, 0);
    }

    private Node put(Node n, Node pNode, Point2D p, int level) {
        double key;
        if (level % 2 == 0) {
            key = p.x();
        } else {
            key = p.y();
        }

        if (n == null) {
            RectHV rect;

            if (pNode == null )
            {
                rect = new RectHV(0,0,1,1);
            }
            else
            {
                if (level % 2 == 0)
                {
                    // node is on the bottom of the parent horizontal line split
                    if (n.value.y() < pNode.value.y())
                    {
                        rect = new RectHV(pNode.rect.xmin(), pNode.rect.ymin(),pNode.rect.xmax(), pNode.value.y());
                    }
                    else
                    {
                        rect = new RectHV(pNode.rect.xmin(), pNode.value.y(), pNode.rect.xmax(), pNode.rect.ymax());
                    }
                }
                else // horizontal split
                {
                    // node is on the left of the parent vertical line split
                    if (n.value.x() < pNode.value.x())
                    {
                        rect = new RectHV(pNode.rect.xmin(), pNode.rect.ymin(),pNode.value.x(),pNode.rect.ymax());
                    }
                    else
                    {
                        rect = new RectHV(pNode.value.x(), pNode.rect.ymin(),pNode.rect.xmax(), pNode.rect.ymax());
                    }
                }
            }
            return new Node(key, p, rect);
        }

        if (!n.value.equals(p)) {
            if (key < n.key) {
                n.left = put(n.left, n, p, level + 1);
            } else {
                n.right = put(n.right, n, p, level + 1);
            }
        } else {
            siz--;
        }
        return n;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node x = root;
        int level = 0;
        while (x != null) {
            if (x.value.equals(p)) {
                return true;
            }

            double key;
            if (level % 2 == 0) {
                key = p.x();
            } else {
                key = p.y();
            }
            double cmp = key - x.key;
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return false;
    }

    private Iterable<Point2D> values() {
        ResizingArrayQueue<Point2D> queue = new ResizingArrayQueue<>();
        inOrderKeyTravel(queue, root);
        return queue;
    }

    private void inOrderKeyTravel(ResizingArrayQueue<Point2D> queue, Node x) {
        if (x == null) {
            return;
        }
        inOrderKeyTravel(queue, x.left);
        queue.enqueue(x.value);
        inOrderKeyTravel(queue, x.right);
    }

    public void draw() {
        draw(root, null, 0);
    }

    // draw all points to standard draw
    private void draw(Node x, Node parent, int level) {
        if (x != null) {
            StdDraw.setPenColor(Color.black);
            StdDraw.setPenRadius(0.01);
            x.value.draw();
            StdDraw.setPenRadius();
            if (parent == null) {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(x.value.x(), 0.00, x.value.x(), 1.0);
            } else {
                if (level % 2 == 0) {
                    StdDraw.setPenColor(Color.red);
                    double px = x.value.x();
                    if (x.value.y() < parent.value.y()) {
                        StdDraw.line(px, 0.00, px, parent.value.y());
                    } else {
                        StdDraw.line(px, parent.value.y(), px, 1.0);
                    }
                } else {
                    StdDraw.setPenColor(Color.blue);
                    double py = x.value.y();
                    if (x.value.x() < parent.value.x()) {
                        StdDraw.line(0, py, parent.value.x(), py);
                    } else {
                        StdDraw.line(parent.value.x(), py, 1.0, py);
                    }
                }
            }
            draw(x.left, x, level + 1);
            draw(x.right, x, level + 1);
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        int level = 0;
        ResizingArrayQueue<Point2D> queue = new ResizingArrayQueue<>();
        search(queue, root, level, rect);
        return queue;
    }

    private void search(ResizingArrayQueue<Point2D> queue, Node n, int level, RectHV rect) {
        if (n == null) return;
        if (rect.contains(n.value)) {
            queue.enqueue(n.value);
        }

        if (level % 2 == 0) {
            // rect is on the right.
            if (n.value.x() < rect.xmin()) {
                search(queue, n.right, level + 1, rect);
            } else if (n.value.x() > rect.xmax()) {
                search(queue, n.left, level + 1, rect);
            } else if (n.value.x() > rect.xmin() && n.value.x() < rect.xmax()) {
                search(queue, n.left, level + 1, rect);
                search(queue, n.right, level + 1, rect);
            }
        } else {
            // rect is on the right.
            if (rect.ymax() < n.value.y()) {
                search(queue, n.left, level + 1, rect);
            } else if (rect.xmin() > n.value.y()) {
                search(queue, n.right, level + 1, rect);
            } else if (n.value.y() > rect.ymin() && n.value.y() < rect.ymax()) {
                search(queue, n.left, level + 1, rect);
                search(queue, n.right, level + 1, rect);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        tree.draw();
    }
}
