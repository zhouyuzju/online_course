import java.util.HashMap;


public class BurrowsWheeler {
	
	public static void encode(){
		StringBuilder sb = new StringBuilder();
		while(!BinaryStdIn.isEmpty()){
			char c = BinaryStdIn.readChar();
			sb.append(c);
		}
		BinaryStdIn.close();
		
		String ori = sb.toString();
		CircularSuffixArray csa = new CircularSuffixArray(ori);
		int first = -1;
		sb = new StringBuilder();
		for(int i = 0;i < ori.length();i++){
			if(csa.index(i) == 0){
				first = i;
				sb.append(ori.charAt(ori.length() - 1));
			}
			else
				sb.append(ori.charAt(csa.index(i) - 1));
		}
		
		BinaryStdOut.write(first);
		String t = sb.toString();
		for(int i = 0;i < t.length();i++)
			BinaryStdOut.write(t.charAt(i));
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}
	
	private static void sort(char[] a) {
        int N = a.length;
        int R = 256;   // extend ASCII alphabet size
        char[] aux = new char[N];
        // compute frequency counts
        int[] count = new int[R+1];
        for (int i = 0; i < N; i++)
        	count[a[i] + 1]++;
        
        // compute cumulates
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
        
        // move data
        for (int i = 0; i < N; i++)
            aux[count[a[i]]++] = a[i];

        // copy back
        for (int i = 0; i < N; i++)
        	a[i] = aux[i];
	}
	
	public static void decode(){
		StringBuilder sb = new StringBuilder();
		int first = BinaryStdIn.readInt();
		while(!BinaryStdIn.isEmpty()){
			char c = BinaryStdIn.readChar();
			sb.append(c);
		}
		BinaryStdIn.close();
		
		char[] t = sb.toString().toCharArray();
		char[] s = sb.toString().toCharArray();
		int[] next = new int[t.length];
		next[0] = first;
		sort(s);
		
		HashMap<Character, Queue<Integer> > col = new HashMap<Character, Queue<Integer> >();
		for(int i = 0;i < t.length;i++){
			if(col.containsKey(t[i]))
				col.get(t[i]).enqueue(i);
			else{
				col.put(t[i], new Queue<Integer>());
				col.get(t[i]).enqueue(i);
			}
		}
		
		for(int i = 0;i < t.length;i++)
			next[i] = col.get(s[i]).dequeue();
		
		for(int i = 0;i < t.length;i++){
			BinaryStdOut.write(s[first]);
			first = next[first];
		}
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}
	
	public static void main(String[] args){
		if(args[0].equals("-")) encode();
		else if(args[0].equals("+")) decode();
		else throw new RuntimeException("Illegal command line argument");
	}
}
