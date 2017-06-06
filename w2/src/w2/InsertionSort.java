package w2;

import java.util.ArrayList;
import java.util.Arrays;

public class InsertionSort {

    public static void insertionSort(Comparable[] a)
    {
        // i is the right partition index
        for (int i = 1; i < a.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if (less(a[j], a[j-1]))
                {
                    exch(a, j, j-1);
                } else
                {
                    break;
                }
            }
        }
    }

    public static void shellSort(Comparable[] a)
    {
        int n = a.length;
        int h = 1;
        while ( h < n/3 ) h = h*3 +1;

        while (h >=1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h/3;
        }
    }

    public static boolean less(Comparable x1, Comparable x2)
    {
        return x1.compareTo(x2) < 0;
    }

    public static void exch(Comparable[] a, int i, int j)
    {
        Comparable copy = a[i];
        a[i] = a[j];
        a[j] = copy;
    }

    public static void main(String[] args)
    {
        Integer[] a= {4, 3, 3, 3, 4};
        shellSort(a);
        System.out.println(Arrays.asList(a));
    }
}
