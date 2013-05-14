import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BaseballElimination {
	
	private int n;
	private List<String> teams;
	private short wins[];
	private short losses[];
	private short remaining[];
	private short against[][];
	private int maxwin,maxidx;
	private double maxflow;
	private static final double EPS = 1e-12;
	
	public BaseballElimination(String filename){
		In in = new In(filename);
		n = in.readInt();
		maxwin = Integer.MIN_VALUE;
		wins = new short[n];
		losses = new short[n];
		remaining = new short[n];
		against = new short[n][n];
		teams = new ArrayList<String>();
		for(int i = 0;i < n;i++){
			String teamname = in.readString();
			teams.add(teamname);
			wins[i] = (short) in.readInt();
			if(maxwin < wins[i]){
				maxwin = wins[i];
				maxidx = i;
			}
			losses[i] = (short) in.readInt();
			remaining[i] = (short) in.readInt();
			for(int j = 0;j < n;j++)
				against[i][j] = (short) in.readInt();
		}
	}
	
	private void validation(String name){
		if(teams.contains(name) == false)
			throw new IllegalArgumentException();
	}
	
	public int numberOfTeams(){
		return n;
	}
	
	public Iterable<String> teams(){
		return teams;
	}
	
	public int wins(String team){
		validation(team);
		return wins[teams.indexOf(team)];
	}
	
	public int losses(String team){
		validation(team);
		return losses[teams.indexOf(team)];
	}
	
	public int remaining(String team){
		validation(team);
		return remaining[teams.indexOf(team)];
	}
	
	public int against(String team1, String team2){
		validation(team1);
		validation(team2);
		return against[teams.indexOf(team1)][teams.indexOf(team2)];
	}
	
	private boolean equal(double x,double y){
		return Math.abs(x - y) < EPS;
	}
	
	public boolean isEliminated(String team){
		validation(team);
		int idx = teams.indexOf(team);
		if(wins[idx] + remaining[idx] < maxwin)
			return true;
		final int base = (n - 1) * (n - 2) / 2;
		FlowNetwork fnw = new FlowNetwork(base + n + 2);
		maxflow = 0.0;
		int count = 1;
		for(int i = 0;i < n;i++){
			for(int j = i + 1;j < n;j++)
				if(i != idx && j != idx && against[i][j] != 0){
					maxflow += against[i][j];
					fnw.addEdge(new FlowEdge(0, count, against[i][j] * 1.0));
					fnw.addEdge(new FlowEdge(count, base + 1 + i, Double.MAX_VALUE));
					fnw.addEdge(new FlowEdge(count, base + 1 + j, Double.MAX_VALUE));
					count++;
				}
			if(i != idx)
				fnw.addEdge(new FlowEdge(base + 1 + i,base + n + 1,(wins[idx] + remaining[idx] - wins[i]) * 1.0));
		}
		FordFulkerson ff = new FordFulkerson(fnw, 0, base + n + 1);
		return !equal(ff.value(),maxflow);
	}
	
	public Iterable<String> certificateOfElimination(String team){
		validation(team);
		int idx = teams.indexOf(team);
		Set<String> result = new HashSet<String>();
		if(wins[idx] + remaining[idx] < maxwin){
			result.add(teams.get(maxidx));
			return result;
		}
		Map<Integer, Integer> pairToIdx = new HashMap<Integer, Integer>();
		final int base = (n - 1) * (n - 2) / 2;
		FlowNetwork fnw = new FlowNetwork(base + n + 2);
		maxflow = 0.0;
		int count = 1;
		for(int i = 0;i < n;i++){
			for(int j = i + 1;j < n;j++)
				if(i != idx && j != idx && against[i][j] != 0){
					maxflow += against[i][j];
					fnw.addEdge(new FlowEdge(0, count, against[i][j] * 1.0));
					pairToIdx.put(n * i + j, count);
					fnw.addEdge(new FlowEdge(count, base + 1 + i, Double.MAX_VALUE));
					fnw.addEdge(new FlowEdge(count, base + 1 + j, Double.MAX_VALUE));
					count++;
				}
			if(i != idx)
				fnw.addEdge(new FlowEdge(base + 1 + i,base + n + 1,(wins[idx] + remaining[idx] - wins[i]) * 1.0));
		}
		FordFulkerson ff = new FordFulkerson(fnw, 0, base + n + 1);
		for(int i = 0;i < n;i++)
			for(int j = i + 1;j < n;j++)
				if(pairToIdx.get(n * i + j) != null && ff.inCut(pairToIdx.get(n * i + j))){
					result.add(teams.get(i));
					result.add(teams.get(j));
				}		
		if(result.size() == 0)
			return null;
		else
			return result;
	}
	
	public static void main(String[] args) {
	    BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team))
	                StdOut.print(t + " ");
	            StdOut.println("}");
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
//	    	StdOut.println(team + ":" + division.losses(team)); 
	    }
	}
}
