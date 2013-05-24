import java.util.Arrays;
import java.util.Comparator;


public class CircularSuffixArray {
	
	private final String original;
	
	private final int len;
	
	private Integer[] index;
	
	public CircularSuffixArray(String s){
		original = s;
		len = s.length();
		index = new Integer[original.length()];
		for(int i = 0;i < original.length();i++)
			index[i] = i;
		prepare();
	}
	
	public int length(){
		return len;
	}
	
	public int index(int i){
		return index[i];
	}
	
	private void prepare(){
		Arrays.sort(index, new Comparator<Integer>() {
			public int compare(Integer a,Integer b){
				if(a.equals(b))
					return 0;
				else{
					for(int i = 0;i < len;i++)
						if(original.charAt((a + i) % len)
								!= original.charAt((b + i) % len))
								return original.charAt((a + i) % len) - original.charAt((b + i) % len);
					return 0;
				}
			}
		});
	}
	
	public static void main(String[] args){
		CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
		for(int i = 0;i < csa.length();i++)
			StdOut.println(csa.index(i));
	}
}
