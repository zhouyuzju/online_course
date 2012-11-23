import java.util.Random;


public class PercolationStats {
	
	private double x[];
	private int T;
	/**
	 * perform T independent computational experiments on an N-by-N grid
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T) throws IllegalArgumentException{
		if(N <= 0 || T <= 0)
			throw new IllegalArgumentException();
		this.T = T;
		x = new double[T];
		for(int i = 0;i < T;i++){
			Percolation pc = new Percolation(N);
			int steps = 0;
			while(!pc.percolates()){
				int idx = StdRandom.uniform(N) + 1;
				int idy = StdRandom.uniform(N) + 1;
				if(!pc.isOpen(idx, idy)){
					steps++;
					pc.open(idx, idy);
				}
			}
			x[i] = steps * 1.0 / (N * N);
		}
	}
	
	/**
	 * sample mean of percolation threshold
	 * @return
	 */
	public double mean(){
		double sum = 0.0;
		for(int i = 0;i < T;i++)
			sum += x[i];
		return sum / T;
	}
	
	/**
	 * sample standard deviation of percolation threshold
	 * @return
	 */
	public double stddev(){
		double sum = 0.0;
		double mean = mean();
		for(int i = 0;i < T;i++)
			sum += Math.pow(x[i] - mean, 2);
		return Math.sqrt(sum / T);
	}
	
	/**
	 * test client, described below
	 * @param args
	 */
	public static void main(String[] args){
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(N, T);
		double mean = ps.mean();
		double stddev = ps.stddev();
		double left = mean - 1.96 * stddev / Math.sqrt(T);
		double right = mean + 1.96 * stddev / Math.sqrt(T);
		StdOut.print("mean                    = " + mean + "\n");
		StdOut.print("stddev                  = " + stddev + "\n");
		StdOut.print("95% confidence interval = " + left + ", " + right + "\n");
	}
}
