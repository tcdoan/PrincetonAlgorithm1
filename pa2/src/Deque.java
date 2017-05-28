import java.util.Iterator;

/**
 * A linked list based imlementation of a double-ended queue.
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item>
{

    // number of elements of the double-ended queue.
    private int n;
    private Node first;
    private Node last;


    /**
     * double-linked-list Node.
     */
    private class Node
    {
        Item item;
        Node next;
    }


    // construct an empty deque
    public Deque()
    {
        n = 0;
        first = last = null;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return (n == 0);
    }

    // return the number of items on the deque
    public int size()
    {
        return n;
    }


    // add the item to the front
    public void addFirst(Item item)
    {
        if (isEmpty())
        {

        }
        Node x = new Node();
        x.item = item;


    }

    // add the item to the end
    public void addLast(Item item)
    {

    }

    // remove and return the item from the front
    public Item removeFirst()
    {

    }

    // remove and return the item from the end
    public Item removeLast()
    {

    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {

    }



    // unit testing (optional)
    public static void main(String[] args)
    {

    }
}
