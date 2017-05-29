import java.util.Iterator;
import java.util.NoSuchElementException;

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
        private Item item;
        private Node next;
        private Node prev;
    }


    // construct an empty deque
    public Deque()
    {
        n = 0;
        first = null;
        last = null;
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


    /**
     * Add the item to the front of the Deque.
     * @param item
     */
    public void addFirst(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException();
        }

        Node x = new Node();
        x.item = item;
        if (isEmpty())
        {
            first = x;
            last = x;
        }
        else
        {
            first.prev = x;
            x.next = first;
            first = x;
        }
        ++n;
    }

    // add the item to the end
    public void addLast(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException();
        }

        Node x = new Node();
        x.item = item;
        if (isEmpty())
        {
            first = x;
            last = x;
        }
        else
        {
            last.next = x;
            x.prev = last;
            last = x;
        }
        ++n;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first != null)
        {
            first.prev = null;
        }
        n--;
        if (isEmpty())
        {
           last = null;
           first = null;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        if (last != null)
        {
            last.next = null;
        }
        n--;
        if (isEmpty())
        {
            last = null;
            first = null;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item>
    {
        private Node current = first;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext()
        {
            return (current != null);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args)
    {
        Deque<Integer> deck = new Deque<>();
        for (int i = 25; i >= 1; i--)
        {
            deck.addFirst(i);
        }
        for (int i = 26; i <= 50; i++)
        {
            deck.addLast(i);
        }

        System.out.println("Expecting 1, 2, 3, 4, 5,...,50");
        int k = 1;
        for (int x: deck)
        {
            System.out.printf("%2d ", x);
            assert (x == k);
            k++;
        }
        System.out.println();

        for (int i = 1; i <= 10; i++)
        {
            deck.removeFirst();
        }
        for (int i = 1; i <= 10; i++)
        {
            deck.removeLast();
        }

        k = 11;
        System.out.println("Expecting 11, 12, 13, ...,40");
        for (int x: deck)
        {
            System.out.printf("%2d ", x);
            assert (x == k);
            k++;
        }
        System.out.println();
    }

}
