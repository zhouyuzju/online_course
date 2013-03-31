
public class Outcast {
	private final WordNet wordnet;
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet){
		this.wordnet = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns){
		int maxdis = Integer.MIN_VALUE;
		String result = "";
		for(String a : nouns){
			int dis = 0;
			for(String b : nouns){
				if(b.equals(a))
					continue;
				int tmpdis = wordnet.distance(a,b);
				if(tmpdis != -1)
					dis += tmpdis;
			}
			if(dis >= maxdis){
				maxdis = dis;
				result = a;
			}
		}
		return result;
	}

	// for unit testing of this class (such as the one below)
	public static void main(String[] args){
		 WordNet wordnet = new WordNet(args[0], args[1]);
		    Outcast outcast = new Outcast(wordnet);
		    for (int t = 2; t < args.length; t++) {
		        String[] nouns = In.readStrings(args[t]);
		        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		    }
	}
}
