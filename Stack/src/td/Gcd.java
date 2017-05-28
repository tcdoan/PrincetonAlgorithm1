package td;

public class Gcd
{
    // recursive implementation of greatest common divisor.
    public static int gdc2(int p, int q)
    {
        if (q == 0)
        {
            return p;
        } else
        {
            return (gdc2(q, p%q));
        }
    }

    // stack based implementation of greatest common divisor.
    public static int gdc(int p, int q)
    {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(p);
        stack.push(q);
        while (true)
        {
            q = stack.pop();
            p = stack.pop();
            if (q == 0)
            {
                return p;
            }
            else
            {
                stack.push(q);
                stack.push(p%q);
            }
        }
    }

    public static void main(String[] args)
    {
         System.out.println(gdc(27, 6));
    }
    
}
