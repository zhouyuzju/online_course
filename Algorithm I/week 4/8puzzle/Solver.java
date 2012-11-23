import java.util.ArrayList;
import java.util.List;


public class Solver {
	
	private int moves;
	private MinPQ<Node> minpq = new MinPQ<Node>();
	private MinPQ<Node> minpqrev = new MinPQ<Node>();
	private boolean isSolveable;
	private List<Node> solutions = new ArrayList<Node>();

	/**
	 * find a solution to the initial board (using the A* algorithm)
	 * @param initial
	 */
	public Solver(Board initial){
		moves = 0;
		minpq.insert(new Node(initial,moves,null));
//		StdOut.println(initial.toString());
		minpqrev.insert(new Node(initial.twin(),moves,null));
//		StdOut.println(initial.toString());
		Node end = null;
		while(!minpq.isEmpty()){
			Node cur = minpq.delMin();
			Node currev = minpqrev.delMin();
			moves = cur.steps;
			int movesrev = currev.steps;
			if(cur.board.isGoal()){
				end = cur;
				break;
			}
			else if(currev.board.isGoal())
				break;
//			StdOut.println(cur.board.toString() + "\nstep: " + cur.steps + "\tmanhattan:" + cur.board.manhattan());
			for(Board b:cur.board.neighbors())
				if(cur.pre == null)
					minpq.insert(new Node(b,moves + 1,cur));
				else if(!b.equals(cur.pre.board))
					minpq.insert(new Node(b,moves + 1,cur));
			
			for(Board b:currev.board.neighbors())
				if(currev.pre == null)
					minpqrev.insert(new Node(b,movesrev + 1,currev));
				else if(!b.equals(currev.pre.board))
					minpqrev.insert(new Node(b,movesrev + 1,currev));
		}
		if(end == null)
			isSolveable = false;
		else{
			isSolveable = true;
			while(end != null){
				solutions.add(end);
				end = end.pre;
			}
		}
	}
	
	/**
	 * is the initial board solvable?
	 * @return
	 */
	public boolean isSolvable(){
		return isSolveable;
	}
	
	/**
	 * min number of moves to solve initial board; -1 if no solution
	 * @return
	 */
	public int moves(){
		if(!isSolveable)
			return -1;
		else
			return moves;
	}
	
	/**
	 * sequence of boards in a shortest solution; null if no solution
	 * @return
	 */
	public Iterable<Board> solution(){
		if(!isSolveable)
			return null;
		else{
			List<Board> boards = new ArrayList<Board>();
			for(int i = solutions.size() - 1;i >= 0;i--)
				boards.add(solutions.get(i).board);
			return boards;
		}
	}
	
	private class Node implements Comparable<Node>{
		int steps;
		Board board;
		Node pre;
		public Node(Board theBoard,int theSteps,Node thePre){
			this.steps = theSteps;
			this.board = theBoard;
			this.pre = thePre;
		}
		public int compareTo(Node b){
			int thisP = board.manhattan() + steps;
			int thatP = b.board.manhattan() + b.steps;
			if(thisP < thatP)
				return -1;
			else if(thisP == thatP)
				return 0;
			else
				return 1;
		}
	}
	
	/**
	 * solve a slider puzzle
	 * @param args
	 */
	public static void main(String[] args){
		In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = (short)in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}
}
