import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    // elements of the queue
    private Item[] items;

    // number of elements of the queue
    private int n;

    // point to the index of the last item
    private int last;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue()
    {
        n = 0;
        items = (Item[]) new Object[2];
        last = 0;
    }

    /**
     * is the queue empty?
     * @return
     */
    public boolean isEmpty()
    {
        return (n == 0);
    }

    /**
     * Return the number of items on the queue
     * @return
     */
    public int size()
    {
        return n;
    }

    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new NullPointerException();
        }

        // double size of array if necessary
        if (last == items.length)
        {
            resize(2*items.length);
        }
        items[last++] = item;
        n++;
    }

    /**
     * Resize the underlying array
     * @param newSize the new array size
     */
    private void resize(int newSize)
    {
        assert newSize >= n;
        Item[] copy = (Item[]) new Object[newSize];

        int j = 0;
        for (int i = 0; i < last; i++)
        {
            if (items[i] != null)
            {
                copy[j++] = items[i];
            }
        }
        last = j;
        items = copy;
    }

    /**
     * Remove and return a random item
     * @return Item a random item from queue
     */
    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        if (size() == items.length/4)
        {
            resize(items.length/2);
        }
        int randIdx = StdRandom.uniform(0, last);
        Item item = null;
        while ((item = items[randIdx]) == null)
        {
            randIdx = StdRandom.uniform(0, last);
        }
        items[randIdx] = null;
        n--;
        return item;
    }

    /**
     * Return (but do not remove) a random item
     * @return Item a random item from queue
     */
    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int randIdx = StdRandom.uniform(0, last);
        Item item = null;
        while ((item = items[randIdx]) == null)
        {
            randIdx = StdRandom.uniform(0, last);
        }
        return item;
    }

    /**
     * Return an independent iterator over items in random order
     * @return
     */
    public Iterator<Item> iterator()
    {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item>
    {
        private int current;

        private Item[] copies = (Item[]) new Object[n];

        public QueueIterator()
        {
            int j = 0;
            for (int i = 0; i < last; i++)
            {
                if (items[i] != null)
                {
                    copies[j++] = items[i];
                }
            }
            StdRandom.shuffle(copies);
            current = 0;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current < copies.length;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Item next() {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            return copies[current++];
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
            throw  new UnsupportedOperationException();
        }
    }

    /**
     * unit testing (optional)
     * @param args
     */
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        for (int  i = 0; i < 10; i++)
        {
            q.enqueue(i);
        }

        for (int  i = 0; i < 5; i++)
        {
            q.dequeue();
        }

        for (int x : q)
        {
            for (int x2 : q)
            {
                System.out.println(x + ":" + x2);
            }
        }
    }
}
