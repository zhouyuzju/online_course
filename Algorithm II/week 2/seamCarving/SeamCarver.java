import java.awt.Color;

public class SeamCarver {

	private Picture p;
	private double[][] dis;
	private static final int BORDERENERGY = 195075;
	private static final double EPS = 1e-12;
	public SeamCarver(Picture picture) {
		this.p = picture;
	}

	public Picture picture() {
		return this.p;
	}

	public int width() {
		return p.width();
	}

	public int height() {
		return p.height();
	}

	private double pow2(int base) {
		return 1.0 * base * base;
	}

	private double min(double x,double y,double z){
		double tmp = x > y ? y : x;
		return tmp > z ? z : tmp;
	}
	
	private double min(double x,double y){
		return x > y ? y : x;
	}
	
	private boolean equal(double x,double y){
		return Math.abs(x - y) < EPS;
	}
	
	public double energy(int x, int y) {
		if(x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
			throw new IndexOutOfBoundsException();
		if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1)
			return BORDERENERGY;
		Color x1 = p.get(x - 1, y), x2 = p.get(x + 1, y), y1 = p.get(x, y - 1), y2 = p
				.get(x, y + 1);
		return pow2(x1.getRed() - x2.getRed())
				+ pow2(x1.getGreen() - x2.getGreen())
				+ pow2(x1.getBlue() - x2.getBlue())
				+ pow2(y1.getRed() - y2.getRed())
				+ pow2(y1.getGreen() - y2.getGreen())
				+ pow2(y1.getBlue() - y2.getBlue());
	}

	public int[] findHorizontalSeam() {
		int [] result = new int[width()];
		
		dis = new double[height()][width()];
		
		for(int i = 0;i < height();i++)
			dis[i][0] = 0.0;
		for(int i = 1;i < width() - 1;i++)
			for(int j = 1;j < height() - 1;j++){
				if(j == 1 && j == height() - 2)
					dis[j][i] = dis[j][i - 1] + energy(i,j);
				else if(j == 1)
					dis[j][i] = min(dis[j][i - 1],dis[j + 1][i - 1]) + energy(i,j);
				else if(j == height() - 2)
					dis[j][i] = min(dis[j - 1][i - 1],dis[j][i - 1]) + energy(i,j);
				else
					dis[j][i] = min(dis[j - 1][i - 1],dis[j][i - 1],dis[j + 1][i - 1]) + energy(i,j);
			}
		
		double minDis = Double.MAX_VALUE,idx = 0;
		for(int i = 1;i < height() - 1;i++){
			dis[i][width() - 1] = dis[i][width() - 2] + BORDERENERGY;
			if(dis[i][width() - 1] < minDis){
				minDis = dis[i][width() - 1];
				idx = i;
			}
		}

		for(int i = width() - 1;i >= 1 ;i--){
			for(int j = 1;j <= height() - 2;j++)
				if(equal(minDis,dis[j - 1][i]) && Math.abs(idx - j + 1) <= 1){
					result[i] = j - 1;
					minDis -= energy(i,j - 1);
					idx = j - 1;
					break;
				}
				else if(equal(minDis,dis[j][i]) && Math.abs(idx - j) <= 1){
					result[i] = j;
					minDis -= energy(i,j);
					idx = j;
					break;
				}
				else if(equal(minDis,dis[j + 1][i])  && Math.abs(idx - j - 1) <= 1){
					result[i] = j + 1;
					minDis -= energy(i,j + 1);
					idx = j + 1;
					break;
				}
		}
		result[width() - 1] = result[width() - 2] - 1;
		result[0] = result[1] - 1;
		return result;
	}

	public int[] findVerticalSeam() {
		int [] result = new int[height()];	
		dis = new double[height()][width()];
		
		for(int i = 0;i < width();i++)
			dis[0][i] = 0.0;
		for(int i = 1;i < height() - 1;i++)
			for(int j = 1;j < width() - 1;j++)
				if(j == 1 && j == width() - 2)
					dis[i][j] = dis[i - 1][j] + energy(j,i);
				else if(j == 1)
					dis[i][j] = min(dis[i - 1][j],dis[i - 1][j + 1]) + energy(j,i);
				else if(j == width() - 2)
					dis[i][j] = min(dis[i - 1][j - 1],dis[i - 1][j]) + energy(j,i);
				else
					dis[i][j] = min(dis[i - 1][j - 1],dis[i - 1][j],dis[i - 1][j + 1]) + energy(j,i);
		double minDis = Double.MAX_VALUE,idx = 0;
		for(int i = 1;i < width() - 1;i++){
			dis[height() - 1][i] = dis[height() - 2][i] + BORDERENERGY;
			if(dis[height() - 1][i] < minDis){
				idx = i;
				minDis = dis[height() - 1][i];
			}
		}
		
		for(int i = height() - 1;i >= 1 ;i--){
			for(int j = 1;j <= width() - 2;j++)
				if(equal(minDis,dis[i][j - 1]) && Math.abs(idx - j + 1) <= 1){
					result[i] = j - 1;
					minDis -= energy(j - 1,i);
					idx = j - 1;
					break;
				}
				else if(equal(minDis,dis[i][j]) && Math.abs(idx - j) <= 1){
					result[i] = j;
					minDis -= energy(j,i);
					idx = j;
					break;
				}
				else if(equal(minDis,dis[i][j + 1]) && Math.abs(idx - j - 1) <= 1){
					result[i] = j + 1;
					minDis -= energy(j + 1,i);
					idx = j + 1;
					break;
				}
		}
		result[height() - 1] = result[height() - 2] - 1;
		result[0] = result[1] - 1;
		return result;
	}

	public void removeHorizontalSeam(int[] a) {
		if(height() <= 1 || a.length != width())
			throw new IllegalArgumentException();
		Picture newp = new Picture(width(), height() - 1);
		int old = a[0];
		for(int i = 0;i < a.length;i++){
			if(Math.abs(a[i] - old) > 1)
				throw new IllegalArgumentException();
			for(int j = 0;j < a[i];j++)
				newp.set(i, j, p.get(i, j));
			for(int j = a[i];j < height() - 1;j++)
				newp.set(i, j, p.get(i,j + 1));
			old = a[i];
		}
		p = newp; 
	}

	public void removeVerticalSeam(int[] a) {
		if(width() <= 1 || a.length != height())
			throw new IllegalArgumentException();	
		Picture newp = new Picture(width() - 1, height());
		int old = a[0];
		for(int i = 0;i < a.length;i++){
			if(Math.abs(a[i] - old) > 1)
				throw new IllegalArgumentException();
			for(int j = 0;j < a[i];j++)
				newp.set(j, i, p.get(j, i));
			for(int j = a[i];j < width() - 1;j++)
				newp.set(j, i, p.get(j + 1, i));
			old = a[i];
		}
		p = newp;
	}
}
