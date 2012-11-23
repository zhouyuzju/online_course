import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Board {

	private final int N;
	private short[][] grid;
	private int manhattan = 0;
	private int hamming = 0;
	
	/**
	 * construct a board from an N-by-N array of blocks
	 * @param blocks
	 */
	public Board(int[][] blocks){
		this.N = blocks.length;
		grid = new short[N][N];
		for(int i = 0;i < N;i++)
			for(int j = 0;j < N;j++)
				grid[i][j] = (short)blocks[i][j];
		initCache();
	}
	
	/**
	 * private constructor
	 * @param blocks
	 */
	private Board(short[][] blocks){
		this.N = blocks.length;
		grid = new short[N][N];
		for(int i = 0;i < N;i++)
			for(int j = 0;j < N;j++)
				grid[i][j] = (short)blocks[i][j];
		initCache();
	}
	
	/**
	 * init manhattan hamming and hashcode
	 */
	private void initCache(){
		for(int i = 0;i < N;i++)
			for(int j = 0;j < N;j++){
				if(grid[i][j] != 0){
					int row = (grid[i][j] - 1) / N;
					int column = (grid[i][j] - 1) % N;
					this.manhattan += Math.abs(row - i) + Math.abs(column - j);
				}
				if(grid[i][j] != N * i + j + 1 && !(i == N - 1 && j == N - 1))
					this.hamming++;
			}
	}
	
	/**
	 * board dimension N
	 * @return
	 */
	public int dimension(){
		return N;
	}
	
	/**
	 * number of blocks out of place
	 * @return
	 */
	public int hamming(){
		return hamming;
	}
	
	/**
	 * sum of Manhattan distances between blocks and goal
	 * @return
	 */
	public int manhattan(){
		return manhattan;
	}
	
	/**
	 * is this board the goal board?
	 * @return
	 */
	public boolean isGoal(){
		return hamming == 0;
	}
	
	private short[][] copyArray(){
		short[][] newGrid = new short[N][N];
		for(int i = 0;i < N;i++)
			for(int j = 0;j < N;j++)
				newGrid[i][j] = grid[i][j];
		return newGrid;
	}
	
	/**
	 * a board obtained by exchanging two adjacent blocks in the same row
	 * @return
	 */
	public Board twin(){
		//init
		short[][] twinGrid = copyArray();

		//twist
		for(int i = 0;i < N;i++)
			if(twinGrid[i][0] != 0 && twinGrid[i][1] != 0){
				swap(twinGrid,i,0,i,1);
				break;
			}
		return new Board(twinGrid);
	}
	
	/**
	 * does this board equal y?
	 */
	public boolean equals(Object y){
		if(y == this)
			return true;
		if(y == null || y.getClass() != this.getClass())
			return false;
		Board that = (Board) y;
		if(this.N != that.N || this.hamming != that.hamming || this.manhattan 
				!= that.manhattan)
			return false;
		for(int i = 0;i < N;i++)
			for(int j = 0;j < N;j++)
				if(this.grid[i][j] != that.grid[i][j])
					return false;
		return true;
	}
	
	/**
	 * all neighboring boards
	 * @return
	 */
	public Iterable<Board> neighbors(){
		List<Board> neighbors = new ArrayList<Board>();
		int column = 0,row = 0;
		short[][] orig = new short[N][N];
		orig = Arrays.copyOf(grid, N);
		for(int i = 0;i < N;i++)
			for(int j = 0;j < N;j++)
				if(grid[i][j] == 0){
					row = i;
					column = j;
					break;
				}
		if(row > 0){
			swap(orig,row,column,row - 1,column);
			neighbors.add(new Board(orig));
			swap(orig,row,column,row - 1,column);
		}
		if(row < N - 1){
			swap(orig,row,column,row + 1,column);
			neighbors.add(new Board(orig));
			swap(orig,row,column,row + 1,column);
		}
		if(column > 0){
			swap(orig,row,column,row,column - 1);
			neighbors.add(new Board(orig));
			swap(orig,row,column,row,column - 1);
		}
		if(column < N - 1){
			swap(orig,row,column,row,column + 1);
			neighbors.add(new Board(orig));
			swap(orig,row,column,row,column + 1);
		}
		return neighbors;
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", grid[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	/**
	 * swap a and b
	 * @param a
	 * @param b
	 */
	private void swap(short[][]orig,int a1,int b1,int a2,int b2){
		short tmp = orig[a1][b1];
		orig[a1][b1] = orig[a2][b2];
		orig[a2][b2] = tmp;
	}
}
