import java.util.HashMap;




public class SAP {
	private final Digraph graph;
	private HashMap<Pair<Integer,Integer>, Pair<Integer,Integer> > cache =
		new HashMap<Pair<Integer,Integer>, Pair<Integer,Integer> >();
	
	
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G){
		this.graph = new Digraph(G);
	}

	private boolean legalNode(int n){
		if(n >= 0 && n <= graph.V() - 1)
			return true;
		else 
			return false;
	}
	
	private boolean legalNode(Iterable<Integer> n){
		for(int i : n)
			if(i < 0 || i > graph.V() - 1)
				return false;
		return true;
	}
	
	private int search(int v,int w,int type){
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
		int min = Integer.MAX_VALUE;
		int id = -1;
		for(int i = 0;i < graph.V();i++){
			if(bfsv.hasPathTo(i) && bfsw.hasPathTo(i) && min > bfsv.distTo(i) + bfsw.distTo(i)){
				min = bfsv.distTo(i) + bfsw.distTo(i);
				id = i;
			}
		}
		if(id == -1)
			return -1;
		if(cache.get(new Pair(v,w)) == null){
			cache.put(new Pair(v,w), new Pair(id,min));
			cache.put(new Pair(w,v), new Pair(id,min));
		}
		if(type == 0)
			return min;
		else
			return id;
	}
	
	private int searchMulti(Iterable<Integer> v,Iterable<Integer> w,int type){
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(graph, w);
		int min = Integer.MAX_VALUE;
		int id = -1;
		for(int i = 0;i < graph.V();i++){
			if(bfsv.hasPathTo(i) && bfsw.hasPathTo(i) && min > bfsv.distTo(i) + bfsw.distTo(i)){
				min = bfsv.distTo(i) + bfsw.distTo(i);
				id = i;
			}
		}
		if(id == -1)
			return -1;
		else if(type == 0)
			return min;
		else
			return id;
	}
	
	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w){
		if(!legalNode(v) || !legalNode(w))
			throw new IndexOutOfBoundsException();
		if(cache.get(new Pair(v,w)) != null)
			return cache.get(new Pair(v,w)).getSecond();
		return search(v,w,0);
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w){
		if(!legalNode(v) || !legalNode(w))
			throw new IndexOutOfBoundsException();
		if(cache.get(new Pair(v,w)) != null)
			return cache.get(new Pair(v,w)).getFirst();
		return search(v,w,1);
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w){
		if(!legalNode(v) || !legalNode(w))
			throw new IndexOutOfBoundsException();
		return searchMulti(v,w,0);
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
		if(!legalNode(v) || !legalNode(w))
			throw new IndexOutOfBoundsException();
		return searchMulti(v,w,1);
	}
	private class Pair<A, B> {
	    private A first;
	    private B second;

	    public Pair(A first, B second) {
	    	super();
	    	this.first = first;
	    	this.second = second;
	    }

	    public int hashCode() {
	    	int hashFirst = first != null ? first.hashCode() : 0;
	    	int hashSecond = second != null ? second.hashCode() : 0;

	    	return (hashFirst + hashSecond) * hashSecond + hashFirst;
	    }

	    public boolean equals(Object other) {
	    	if (other instanceof Pair) {
	    		Pair otherPair = (Pair) other;
	    		return 
	    		((this.first.equals(otherPair.first)&&this.second.equals(otherPair.second))
	    		||(this.first.equals(otherPair.second)&&this.second.equals(otherPair.first)));
	    	}

	    	return false;
	    }

	    public String toString()
	    { 
	           return "(" + first + ", " + second + ")"; 
	    }
	    public A getFirst() {
	    	return first;
	    }

	    public void setFirst(A first) {
	    	this.first = first;
	    }

	    public B getSecond() {
	    	return second;
	    }

	    public void setSecond(B second) {
	    	this.second = second;
	    }
	}
	// for unit testing of this class (such as the one below)
	public static void main(String[] args){
		In in = new In(args[0]);
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
	    while (!StdIn.isEmpty()) {
	    	 int v = StdIn.readInt();
	         int w = StdIn.readInt();
	         int length = sap.length(v, w);
	         int ancestor = sap.ancestor(v, w);
	         StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
	    }
	}
}
