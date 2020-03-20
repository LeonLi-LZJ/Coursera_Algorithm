import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

	private int size;
	// private int trial_times;
	// private double mean;
	// private double sd;
	private double[] data;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		size = n;
		// trial_times = trials;
		data = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation per = new Percolation(n);
			while (!per.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
				int col = StdRandom.uniform(1, n + 1);
				if (!per.isOpen(row, col)) {
					per.open(row, col);
				}
			}
			data[i] = (double) per.numberOfOpenSites() / (size ^ 2);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(data);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(data);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(data.length);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(data.length);
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats percStats = new PercolationStats(n, trials);
		StdOut.println("mean                    = " + percStats.mean());
		StdOut.println("stddev                  = " + percStats.stddev());
		StdOut.println(
				"95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
	}

}