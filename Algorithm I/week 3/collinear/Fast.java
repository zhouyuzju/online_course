import java.util.Arrays;


public class Fast {
	
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
		for(int i = 0;i < N;i++){
			Point[] pfs = new Point[N];
			for(int m = 0;m < N;m++)
				pfs[m] = points[m];
			Arrays.sort(pfs,points[i].SLOPE_ORDER);
			
//			if(points[i].compareTo(new Point(18000,10000)) == 0)
//				for(int xx = 0;xx < N;xx++)
//					StdOut.print(pfs[xx] + ",");
//			StdOut.print("\n");
			
			for(int j = 0;j < N;){
				double t1 = points[i].slopeTo(pfs[j]);
				int idx = j;
				int count = 0;
				boolean flag = false;
				while(idx < N && points[i].slopeTo(pfs[idx]) == t1)
				{
					if(points[i].compareTo(pfs[idx]) > 0)
					{
						flag = true;
					}
					idx++;
					count++;
				}

				if(count >= 3 && !flag)
				{
					StdOut.print(points[i] + " -> ");
					for(int k = j;k < idx;k++){
						if(k == idx - 1)
							StdOut.print(pfs[k] + "\n");
						else
							StdOut.print(pfs[k] + " -> ");
					}
					points[i].drawTo(pfs[idx - 1]);
					j = idx;
				}
				else if(flag)
					j = idx;
				else
					j++;
			}
		}
		StdDraw.show(0);
	}

}
