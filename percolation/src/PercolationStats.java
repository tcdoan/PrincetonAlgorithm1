/**
 * Created by Thanh on 5/21/2017.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats
{
    private int _trials;
    private double[]  _percolationThresholds;

    public PercolationStats(int n, int trials)
    {
        if (n < 1 ||  trials < 1)
        {
            throw new java.lang.IllegalArgumentException();
        }
        _trials = trials;
        _percolationThresholds = new double[_trials];
        for (int t = 0; t < trials; t++)
        {
            Percolation experiment = new Percolation(n);
            while(!experiment.percolates())
            {
                int x = StdRandom.uniform(1, n+1);
                int y = StdRandom.uniform(1, n+1);
                experiment.open(x, y);
            }
            _percolationThresholds[t] = (1.0*experiment.numberOfOpenSites()) / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(_percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(_percolationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - (1.96 * stddev())/Math.sqrt(_trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + (1.96 * stddev())/Math.sqrt(_trials);
    }

    // test client (described below)
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            throw new java.lang.IllegalArgumentException("Invalid arguments");
        }
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stat = new PercolationStats(n, T);
        System.out.printf("%50s = %f \n", "mean", stat.mean());
        System.out.printf("%50s = %f \n", "stddev", stat.stddev());
        System.out.printf("%50s = [%f, %f] \n", "95% confidence interval", stat.confidenceLo(), stat.confidenceHi());
    }
}
