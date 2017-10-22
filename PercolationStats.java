
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
 	
public class PercolationStats {
	// variables declaration
	private static final double CONFIDENCE = 1.96;
	private final double mean;
	private final double stddev;
	private final double confidenceLo;
	private final double confidenceHi;
	

	
	public PercolationStats(int n,int trials){
		
		
		if(n<=0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		
		
		double[] threshold = new double[trials];
		
		for(int i=0;i<trials;i++) {
			Percolation perco = new Percolation(n);
			int row;
			int col;
			
			for(int j=0;j<n*n;j++) {
				do {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                } while (perco.isOpen(row, col));
					perco.open(row, col);

				if(perco.percolates()) {
					threshold[i] = (double)(j+1)/(double)(n*n);
					break;
				}			
			}
		}
		

        this.mean = StdStats.mean(threshold);
        this.stddev = (trials == 1) ? Double.NaN : StdStats.stddev(threshold);
        this.confidenceLo = mean() - CONFIDENCE * stddev() / Math.sqrt(trials);
        this.confidenceHi = mean() + CONFIDENCE * stddev() / Math.sqrt(trials);
	}
	public double mean() {
		return this.mean;
	}
	public double stddev() {

		return this.stddev;
	}
	public double confidenceLo() {

		return this.confidenceLo;
	}
	public double confidenceHi() {

		return this.confidenceHi;
	}
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		
		PercolationStats percoStats = new PercolationStats(n,trials);
		System.out.println("mean    =" + percoStats.mean());
		System.out.println("stddev  =" + percoStats.stddev());
		System.out.println("95% confidence interval  = [" + percoStats.confidenceLo()+", "+percoStats.confidenceHi()+"]");
		
		
	}
	
	

}
