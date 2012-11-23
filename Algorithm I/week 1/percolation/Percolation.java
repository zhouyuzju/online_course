import java.util.HashSet;


public class Percolation{
	
	private WeightedQuickUnionUF uf;
	private boolean grid[][];
	private int N;
	/**
	 * create N-by-N grid, with all sites blocked
	 * @param N
	 */
	public Percolation(int N) throws IllegalArgumentException{
		if(N <=0)
			throw new IllegalArgumentException();
		this.N = N;
		uf = new WeightedQuickUnionUF(N * N + 2);
		grid = new boolean[N][N];
		for(int i = 0;i < N;i++)
			for(int j = 0;j < N;j++)
				grid[i][j] = false;
	}
	
	/**
	 * open site (row i, column j) if it is not already
	 * @param i
	 * @param j
	 */
	public void open(int i, int j) throws IndexOutOfBoundsException {
		if (i > N || i < 1)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j > N || j < 1)
			throw new IndexOutOfBoundsException("column index j out of bounds");
		grid[i - 1][j - 1] = true;
		int p = (i - 1) * N + (j - 1);
		// left
		if (j > 1 && grid[i - 1][j - 2]) {
			int q = p - 1;
			uf.union(p, q);
		}
		// right
		if (j < N && grid[i - 1][j]) {
			int q = p + 1;
			uf.union(p, q);
		}
		// up
		if (i > 1 && grid[i - 2][j - 1]) {
			int q = p - N;
			uf.union(p, q);
		}
		// down
		if (i < N && grid[i][j - 1]) {
			int q = p + N;
			uf.union(p, q);
		}
		//N * N --> connected with top
		if(i == 1)
			uf.union(p, N * N);
		//avoid backwash
		if (isFull(i, j)) {
	        for (int x = 1; x <= N; x++) {
	          if (grid[N - 1][x - 1] && isFull(N, x)) {
	            uf.union((N * N - N + x - 1),N * N + 1);
	          }
	        }
	      }
	}
	
	/**
	 * is site (row i, column j) open?
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
		if (i > N || i < 1)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j > N || j < 1)
			throw new IndexOutOfBoundsException("column index j out of bounds");
		return grid[i - 1][j - 1];
	}
	
	/**
	 * is site (row i, column j) full?
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j) {
		if (i > N || i < 1)
			throw new IndexOutOfBoundsException("row index i out of bounds");
		if (j > N || j < 1)
			throw new IndexOutOfBoundsException("column index j out of bounds");
		int q = (i - 1) * N + (j - 1);
		return uf.connected(N * N, q);
	}
	
	/**
	 * does the system percolate?
	 * @return
	 */
	public boolean percolates(){
		return uf.connected(N * N, N * N + 1);
	}
}
