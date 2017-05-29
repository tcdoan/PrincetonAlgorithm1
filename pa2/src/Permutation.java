import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Thanh on 5/28/2017.
 */
public class Permutation
{
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            throw new IllegalArgumentException();
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty())
        {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        for (int i = 0; i < k; i++)
        {
            StdOut.println(queue.dequeue());
        }
    }
}
