import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;




public class WordNet {
	private final Map<String,ArrayList<Integer> > nouns = new HashMap<String,ArrayList<Integer> >();
	private final List<String> synsetList = new ArrayList<String>();
	private final Digraph graph;
	private final SAP sap;
	
	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms){
		In in = new In(synsets);
		while (!in.isEmpty()) {
			String line = in.readLine();
			int id = Integer.parseInt(line.split(",")[0]);
			String synset = line.split(",")[1];
			for(String tmp : synset.split(" ")){
				if(nouns.get(tmp) == null)
					nouns.put(tmp,new ArrayList<Integer>());
				nouns.get(tmp).add(id);
			}
			synsetList.add(synset);
		}
		boolean [] root = new boolean[synsetList.size()];
		in = new In(hypernyms);
		graph = new Digraph(synsetList.size());
		while (!in.isEmpty()) {
			String line = in.readLine();
			int synset = Integer.parseInt(line.split(",")[0]);
			for(int i = 1;i < line.split(",").length;i++){
				graph.addEdge(synset, Integer.parseInt(line.split(",")[i]));
				root[synset] = true;
			}
		}
		int count = 0;
		for(int i = 0;i < root.length;i++)
			if(!root[i])
				count++;
		DirectedCycle dc = new DirectedCycle(graph);
		if(dc.hasCycle() || count > 1)
			throw new IllegalArgumentException();
		sap = new SAP(graph);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns(){
		return nouns.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word){
		return nouns.get(word) != null;
	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB){
		if(!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();
		return sap.length(nouns.get(nounA), nouns.get(nounB));
	}

	// a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB){
		if(!isNoun(nounA) || !isNoun(nounB))
			throw new IllegalArgumentException();
		int aid = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
		return synsetList.get(aid);
	}

	// for unit testing of this class
	public static void main(String[] args){
		
	}
}
