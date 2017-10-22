
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
		
	   private final int n;
	   private boolean[][] grid;
	   private int numberOfOpenSites;
	   private final WeightedQuickUnionUF  weightedQuickUnionUF;
	   
	   
		// create n by n grid, with all sites blocked
	   public Percolation(int n) {
		   if(n<=0) {
			   throw new IllegalArgumentException();
		   }
		   this.n=n;
		   this.grid= new boolean[n][n];
		   weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);
		   
	   }
	   // convert x,y coordinates of sites to indices
	   private int xyToIndex(int row, int col) {
		   if(row <= 0|| row > n || col <= 0 || col > n) {
			   throw new  IllegalArgumentException();
		   }
		   row--;
		   col--;
		   return row*n+col;
	   }
	   // open site(row,col) if it is not sopened yet
	   public void open(int row, int col) {
		   if(row <= 0|| row > n || col <= 0 || col > n) {
			   throw new IllegalArgumentException();
		   }
		   if(!grid[row-1][col-1]) {
			   grid[row-1][col-1] = true;
			   numberOfOpenSites++; 
		   }
		   if(row==1)   			   // connect all top sites to an independent site
			   weightedQuickUnionUF.union(0,xyToIndex(row,col)); 
		   if(row==n)                  // connect all bottom sites to an independent site
			   weightedQuickUnionUF.union(xyToIndex(row,col), n*n+1);
		   if(col>1) {
			   if(isOpen(row,col-1))   // if the left site is open, then connect it to current site
				   weightedQuickUnionUF.union(xyToIndex(row,col-1), xyToIndex(row,col));   
		   }
		   if(col<n) {
			   if(isOpen(row,col+1))   // if the right site is open, then connect it to current site
				   weightedQuickUnionUF.union(xyToIndex(row,col), xyToIndex(row,col+1));
		   }
		   if(row>1) {
			   if(isOpen(row-1,col))   // if the top site is open, then connect it to current site
				   weightedQuickUnionUF.union(xyToIndex(row-1,col), xyToIndex(row,col));
		   }
		   if(row<n) {
			   if(isOpen(row+1,col))   // if the bottom site is open, then connect it to current site
				   weightedQuickUnionUF.union(xyToIndex(row+1,col), xyToIndex(row,col));
		   }
			   
	   }
	   // is the site opened?
	   public boolean isOpen(int row, int col) {
		   if(row <= 0|| row > n || col <= 0 || col > n) {
			   throw new IllegalArgumentException();
		   }
		   
		   return grid[row-1][col-1];
		   
	   }
	   // is the site full?
	   public boolean isFull(int row, int col) {
		   if(row <= 0|| row > n || col <= 0 || col > n) {
			   throw new IllegalArgumentException();
		   }
		   // only if the current site is open and it can be connected to the top, return isFUll true
		   return isOpen(row,col) && weightedQuickUnionUF.connected(0, xyToIndex(row,col)); 
		   
		   
	   }
	   //number of open sites
	   public int numberOfOpenSites() {
		   return numberOfOpenSites;
		   
	   }
	   //does the system percolate?       if the independent top and bottom sites are connected, return percolate true
	   public boolean percolates() {
		   return weightedQuickUnionUF.connected(0,n*n+1);
	   }

}
