package td;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Copy from http://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/ResizingArrayStack.java.html
 *
 *  This class represents a last-in-first-out (LIFO) stack of generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  This implementation uses a resizing array, which double the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The <em>push</em> and <em>pop</em> operations take constant amortized time.
 *  The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 *  constant time in the worst case.
 * @param <Item> Generic type of the item.
 */
public class ArrayStack<Item> implements Iterable<Item>
{
    private Item[] items;
    private int n;

    /**
     * Initializes an empty stack with capacity of 2.
     */
    public ArrayStack()
    {
        items = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * Check if the stack is empty.
     * @return true if the stack is empty; false otherwise
     */
    public boolean isEmpty()
    {
        return n == 0;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size()
    {
        return n;
    }

    /**
     * Add the item to stack.
     * @param item the item to add
     */
    public void push(Item item)
    {
        if (n == items.length)
        {
            resize(2 * items.length);
        }
        items[n++] = item;
    }

    /**
     * Return, but not remove, the top item on the stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if stack is empty.
     */
    public Item peek()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Stack underflow");
        }
        return items[--n];
    }

    /**
     * Return, and remove, the top item on the stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if stack is empty.
     */
    public Item pop()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Stack underflow");
        }

        Item item = items[--n];
        items[n] = null;
        if (n == items.length/4)
        {
            resize(items.length/2);
        }
        return item;
    }

    /**
     * Resize the underlying array holding the elements
     * @param new_size is the new capacity of the stack
     */
    private void resize(int new_size)
    {
        assert new_size >= n;
        Item[] copy =  (Item[]) new Object[new_size];
        for (int i = 0; i < n; i++)
        {
            copy[i] = items[i];
        }
        items = copy;
    }

    /**
     * Returns an iterator over elements of type {@code Item}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Item> iterator()
    {
        return new ReverseArrayIterator();
    }

    /**
     * An iterator with no remove() implementation.
     */
    private class ReverseArrayIterator implements Iterator<Item>
    {
        private int i;

        public ReverseArrayIterator()
        {
            this.i = n-1;
        }

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
            return n >= 0;
        }

        /**
         * Dont support remove operation.
         */
        public void remove()
        {
            throw new UnsupportedOperationException();
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
            return items[i--];
        }
    }
}
