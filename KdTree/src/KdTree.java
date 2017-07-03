import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private class Node {
        private double key;
        private Point2D value;
        private Node lb;
        private Node rt;
        private RectHV rect;
        private Node(double key, Point2D value, RectHV rect) {
            this.key = key;
            this.value = value;
            this.rect = rect;
        }
    }
    private Node root;
    private int siz;

    public KdTree() {
        root = null;
        siz = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return siz;
    }

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
            if (pNode == null) {
                rect = new RectHV(0, 0, 1, 1);
            } else {
                if (level % 2 == 0) {
                    if (p.y() < pNode.value.y()) {
                        rect = new RectHV(pNode.rect.xmin(), pNode.rect.ymin(), pNode.rect.xmax(), pNode.value.y());
                    } else {
                        rect = new RectHV(pNode.rect.xmin(), pNode.value.y(), pNode.rect.xmax(), pNode.rect.ymax());
                    }
                } else // horizontal split
                {
                    if (p.x() < pNode.value.x()) {
                        rect = new RectHV(pNode.rect.xmin(), pNode.rect.ymin(), pNode.value.x(), pNode.rect.ymax());
                    } else {
                        rect = new RectHV(pNode.value.x(), pNode.rect.ymin(), pNode.rect.xmax(), pNode.rect.ymax());
                    }
                }
            }
            return new Node(key, p, rect);
        }
        if (!n.value.equals(p)) {
            if (key < n.key) {
                n.lb = put(n.lb, n, p, level + 1);
            } else {
                n.rt = put(n.rt, n, p, level + 1);
            }
        } else {
            siz--;
        }
        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node x = root;
        int level = 0;
        while (x != null) {
            if (x.value.equals(p)) {
                return true;
            }
            double cmp;
            if (level % 2 == 0) {
                cmp = p.x() - x.key;
            } else {
                cmp = p.y() - x.key;
            }
            if (cmp < 0) {
                x = x.lb;
                level++;
            } else {
                x = x.rt;
                level++;
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
        if (x == null) { return; }
        inOrderKeyTravel(queue, x.lb);
        queue.enqueue(x.value);
        inOrderKeyTravel(queue, x.rt);
    }

    public void draw() {
        draw(root, null, 0);
    }

    private void draw(Node x, Node parent, int level) {
        if (x != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.value.draw();
            StdDraw.setPenRadius();
            if (parent == null) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.value.x(), 0.00, x.value.x(), 1.0);
            } else {
                if (level % 2 == 0) {
                    StdDraw.setPenColor(StdDraw.RED);
                    double px = x.value.x();
                    if (x.value.y() < parent.value.y()) {
                        StdDraw.line(px, 0.00, px, parent.value.y());
                    } else {
                        StdDraw.line(px, parent.value.y(), px, 1.0);
                    }
                } else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    double py = x.value.y();
                    if (x.value.x() < parent.value.x()) {
                        StdDraw.line(0, py, parent.value.x(), py);
                    } else {
                        StdDraw.line(parent.value.x(), py, 1.0, py);
                    }
                }
            }
            draw(x.lb, x, level + 1);
            draw(x.rt, x, level + 1);
        }
    }

    public Iterable<Point2D> range(RectHV query) {
        if (query == null) throw new IllegalArgumentException();
        int level = 0;
        ResizingArrayQueue<Point2D> queue = new ResizingArrayQueue<>();
        search(queue, root, level, query);
        return queue;
    }

    private void search(ResizingArrayQueue<Point2D> queue, Node n, int level, RectHV query) {
        if (n == null) return;
        if (query.contains(n.value)) {
            queue.enqueue(n.value);
        }
        if (n.lb != null && query.intersects(n.lb.rect)) {
            search(queue, n.lb, level + 1, query);
        }
        if (n.rt != null && query.intersects(n.rt.rect)) {
            search(queue, n.rt, level + 1, query);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return nearest(root, null, p, 0);
    }

    private Point2D nearest(Node n, Point2D champ, Point2D qp, int level) {
        if (n == null) return champ;
        if (champ == null || qp.distanceSquaredTo(n.value) < qp.distanceSquaredTo(champ)) {
            champ = n.value;
        }
        if (level % 2 == 0) {
            if (qp.x() < n.value.x()) {
                champ = nearest(n.lb, champ, qp, level + 1);
                if (n.rt != null && n.rt.rect.distanceSquaredTo(qp) < qp.distanceSquaredTo(champ)) {
                    champ = nearest(n.rt, champ, qp, level + 1);
                }
            } else {
                champ = nearest(n.rt, champ, qp, level + 1);
                if (n.lb != null && n.lb.rect.distanceSquaredTo(qp) < qp.distanceSquaredTo(champ)) {
                    champ = nearest(n.lb, champ, qp, level + 1);
                }
            }
        } else {
            if (qp.y() < n.value.y()) {
                champ = nearest(n.lb, champ, qp, level + 1);
                if (n.rt != null && n.rt.rect.distanceSquaredTo(qp) < qp.distanceSquaredTo(champ)) {
                    champ = nearest(n.rt, champ, qp, level + 1);
                }
            } else {
                champ = nearest(n.rt, champ, qp, level + 1);
                if (n.lb != null && n.lb.rect.distanceSquaredTo(qp) < qp.distanceSquaredTo(champ)) {
                    champ = nearest(n.lb, champ, qp, level + 1);
                }
            }
        }
        return champ;
    }
}