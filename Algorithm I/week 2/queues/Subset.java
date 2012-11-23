import java.util.Iterator;


public class Subset {
	
	public static void main(String[] args){
		RandomizedQueue<String> rdq = new RandomizedQueue<>();

		int k = Integer.parseInt(args[0]);
		String term = null;
		while(!StdIn.isEmpty())
		{
			term = StdIn.readString();
			rdq.enqueue(term);
		}
		for(int i = 0;i < k;i++)
		{
			StdOut.print(rdq.dequeue() + "\n");
		}
	}
}
