import java.util.Arrays;


public class PointPlotter {
    public static void main(String[] args) {

        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenColor(StdDraw.BLUE);
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
		Point[] points = new Point[N];
		for(int i = 0;i < N;i++){
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
			points[i].draw();
		}
		Quick.sort(points);
		boolean[] flags = new boolean[N];
		for(int i = 0;i < N;i++){
			Point[] pfs = new Point[N];
			for(int m = 0;m < N;m++)
				pfs[m] = points[m];
			Arrays.sort(pfs,points[i].SLOPE_ORDER);
			for(int j = 0;j < N;){
				double t1 = points[i].slopeTo(pfs[j]);
				int idx = j;
				int count = 0;
				while(idx < N && points[i].slopeTo(pfs[idx]) == t1 && points[i].compareTo(pfs[idx]) < 0)
				{
					idx++;
					count++;
				}
				if(count >= 3)
				{
					int result = 0;
					for(int k = j;k < idx;k++)
						if(flags[i])
							result++;
					if(result == count){
						j = idx;
						continue;
					}
					StdOut.print(points[i] + " -> ");
					for(int k = j;k < idx;k++){
						if(k == idx - 1)
							StdOut.print(pfs[k] + "\n");
						else
							StdOut.print(pfs[k] + " -> ");
						flags[k] = true;
						points[i].drawTo(pfs[k]);
					}
					j = idx;
				}
				else
					j++;
			}
		}
        StdDraw.show(0);
    }
}
