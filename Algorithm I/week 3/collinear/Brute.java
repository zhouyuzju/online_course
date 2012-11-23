
public class Brute {
	public static void main(String [] args){
		StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
		In in = new In(args[0]);
		int N = in.readInt();
		Point[] points = new Point[N];
		for(int i = 0;i < N;i++){
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
			points[i].draw();
		}
		Quick.sort(points);
		for(int i = 0;i < N;i++)
			for(int j = i + 1;j < N;j++)
				for(int k = j + 1;k < N;k++)
					for(int l = k + 1;l < N;l++){
						double sj = points[i].slopeTo(points[j]);
						double sk = points[i].slopeTo(points[k]);
						double sl = points[i].slopeTo(points[l]);
						if(sj == sk && sj == sl){
							StdOut.print(points[i].toString() + " -> " + points[j].toString() + 
									" -> " + points[k].toString() + " -> " + points[l] + "\n");
						points[i].drawTo(points[l]);
					}
			}
		StdDraw.show(0);
	}
}
