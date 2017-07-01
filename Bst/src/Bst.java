import edu.princeton.cs.algs4.ResizingArrayQueue;

/**
 * Created by Thanh on 6/17/2017.
 */
public class Bst<Key extends Comparable<Key>, Value>
{
    private Node root;

    public Value get(Key key)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }

        Node x = root;
        while (x != null)
        {
            int comp = key.compareTo(x.key);
            if (comp < 0)
            {
                x = x.left;
            }
            else if (comp > 0)
            {
                x = x.right;
            }
            else
            {
                return x.value;
            }
        }
        return null;
    }

    public void put(Key key, Value value)
    {
        if (key == null)
        {
            throw new NullPointerException();
        }
        root = put(root, key, value);
    }

    private Node put(Node x, Key k, Value v)
    {
        if (x == null) return new Node(k, v);
        int cmp = k.compareTo(x.key);
        if (cmp < 0)
        {
            x.left = put(x.left, k, v);
        }
        else if (cmp > 0)
        {
            x.right = put(x.right, k, v);
        } else
        {
            x.value = v;
        }
        return x;
    }

    private void inOrderKeyTravel(ResizingArrayQueue<Key> queue, Node x)
    {
        if (x == null)
        {
            return;
        }
        inOrderKeyTravel(queue, x.left);
        queue.enqueue(x.key);
        inOrderKeyTravel(queue, x.right);
    }

    public Iterable<Key> keys()
    {
        ResizingArrayQueue<Key> queue = new ResizingArrayQueue<Key>();
        inOrderKeyTravel(queue, root);
        return queue;
    }


    public static void main(String[] args)
    {
        Bst<Character, Integer> test = new Bst<>();
        test.put('S', 5);
        test.put('E', 2);
        test.put('C', 1);
        test.put('H', 3);
        test.put('L', 4);
        test.put('X', 6);

        for (Character x : test.keys())
        {
            System.out.printf("%s -> %d %n", x, test.get(x));
        }
    }

    private class Node
    {
        private Key key;
        private Value value;
        private Node left;
        private Node right;

        Node(Key key, Value value)
        {
            this.key = key;
            this.value = value;
        }
    }
}
