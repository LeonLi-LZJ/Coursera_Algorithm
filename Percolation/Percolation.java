import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int size;
	private boolean[][] site; // to store features: open/blocked
	private WeightedQuickUnionUF QF; // to store all the site, top and bottom added
	private int top_node = 0;
	private int bottom_node;
	private int num_open;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be a positive number.");
		size = n;
		bottom_node = size * size + 1;
		site = new boolean[size][size];
		num_open = 0;
		QF = new WeightedQuickUnionUF(size * size + 2); // add the top and bottom node
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) 
			throw new IllegalArgumentException();
		
		if (isOpen(row, col))
			return;

		num_open++;

		site[row - 1][col - 1] = true; // do not need to check open or not
		// Be careful, there exists a map from array site to the real grid.
		// And the element in array, indexing from 0.

		// then we should union this point in other four directions

		// top
		if (row == 1)
			QF.union((row - 1) * size + col, top_node);

		// bottom
		if (row == size)
			QF.union((row - 1) * size + col, bottom_node);

		// OTHER situation
		// left
		if (col > 1 && isOpen(row, col - 1))
			QF.union((row - 1) * size + col, (row - 1) * size + col - 1);

		// right
		if (col < size && isOpen(row, col + 1))
			QF.union((row - 1) * size + col, (row - 1) * size + col + 1);

		// top
		if (row > 1 && isOpen(row - 1, col))
			QF.union((row - 1) * size + col, ((row - 1) - 1) * size + col);

		// bottom
		if (row < size && isOpen(row + 1, col))
			QF.union((row - 1) * size + col, ((row + 1) - 1) * size + col);

	}

	// is the site (row, col) open? (also means that it open or block)
	public boolean isOpen(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) 
			throw new IllegalArgumentException();
		return site[row - 1][col - 1] == true;
	}

	// is the site (row, col) full? (also means that it is connected or not)
	public boolean isFull(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) 
			throw new IllegalArgumentException();
		return QF.connected(top_node, (row - 1) * size + col);
		// There is a warrning.
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return num_open;

	}

	// does the system percolate?
	public boolean percolates() {
		return QF.connected(top_node, bottom_node);
	}

	// test client (optional)
	public static void main(String[] args) {

	}

}