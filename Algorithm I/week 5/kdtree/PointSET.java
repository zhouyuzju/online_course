import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author zhouyu
 *
 */
public class PointSET {

	private SET<Point2D> pointset;
	/**
	 * construct an empty set of points
	 */
	public PointSET(){
		pointset = new SET<Point2D>();
	}
	
	/**
	 * is the set empty?
	 * @return
	 */
	public boolean isEmpty(){
		return pointset.size() == 0;
	}
	
	/**
	 * number of points in the set
	 * @return
	 */
	public int size(){
		return pointset.size();
	}
	
	/**
	 * add the point p to the set (if it is not already in the set)
	 * @param p
	 */
	public void insert(Point2D p){
		if(!pointset.contains(p))
			pointset.add(p);
	}
	
	/**
	 * does the set contain the point p?
	 * @param p
	 * @return
	 */
	public boolean contains(Point2D p){
		return pointset.contains(p);
	}
	
	/**
	 * draw all of the points to standard draw
	 */
	public void draw(){
		for(Point2D p:pointset)
			p.draw();
	}
	
	/**
	 * all points in the set that are inside the rectangle
	 * @param rect
	 * @return
	 */
	public Iterable<Point2D> range(RectHV rect){
		List<Point2D> result = new ArrayList<Point2D>();
		for(Point2D p:pointset)
			if(rect.contains(p))
				result.add(p);
		return result;
	}
	
	/**
	 * a nearest neighbor in the set to p; null if set is empty
	 * @param p
	 * @return
	 */
	public Point2D nearest(Point2D p){
		Point2D result = null;
		double distance = Double.MAX_VALUE;
		for(Point2D key:pointset)
			if(distance > key.distanceTo(p)){
				result = key;
				distance = key.distanceTo(p);
			}
		return result;
	}
}
