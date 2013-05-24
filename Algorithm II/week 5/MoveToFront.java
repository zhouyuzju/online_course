import java.util.LinkedList;


public class MoveToFront {
	
	public static void encode(){
		int R = 256;
		char[] move = new char[R + 1];
		for(char i = 0;i <= R;i++)
			move[i] = i;
		StringBuilder sb = new StringBuilder();
		while(!BinaryStdIn.isEmpty()){
			char c = BinaryStdIn.readChar();
			sb.append(c);
		}
		BinaryStdIn.close();
		char[] s = sb.toString().toCharArray();
				
		for(int i = 0;i < s.length;i++){
			char idx = 0;
			for(char j = 0;j < R;j++){
				if(move[j] == s[i]){
					BinaryStdOut.write(j);
					idx = j;
					break;
				}
			}
			for(int j = idx - 1;j >= 0;j--)
				move[j + 1] = move[j];
			move[0] = s[i];
		}
		BinaryStdOut.flush();
		BinaryStdOut.close();
	}
	
	public static void decode(){
		int R = 256;
		char[] move = new char[R + 1];
		for(char i = 0;i <= R;i++)
			move[i] = i;
		StringBuilder sb = new StringBuilder();
		while(!BinaryStdIn.isEmpty()){
			char c = BinaryStdIn.readChar();
			sb.append(c);
		}
		BinaryStdIn.close();
		
		char[] s = sb.toString().toCharArray();
		for(int i = 0;i < s.length;i++){
			char idx = 0;
			char tmp = 0;
			for(char j = 0;j < R;j++){
				if(move[j] == move[s[i]]){
					BinaryStdOut.write(move[j]);
					idx = j;
					tmp = move[j];
					break;
				}
			}
			for(int j = idx - 1;j >= 0;j--)
				move[j + 1] = move[j];
			move[0] = tmp;
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
