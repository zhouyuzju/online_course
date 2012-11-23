import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author zhouyu
 *
 */
public class KdTree {
	private Node root;
	private int size;
	private double xmin,ymin,xmax,ymax;
	private List<Point2D> rangeResult;
	private Point2D near;
	
	public KdTree(){
		root = null;
		size = 0;
		rangeResult = null;
		near = null;
	}
	
	/**
	 * is the set empty?
	 * @return
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/**
	 * number of points in the set
	 * @return
	 */
	public int size(){
		return size;
	}
	
	/**
	 * add the point p to the set (if it is not already in the set)
	 * @param p
	 */
	public void insert(Point2D p){
		xmin = 0.0; ymin = 0.0; xmax = 1.0; ymax = 1.0;
		root = put(root,p,0);
//		StdOut.println(p.toString());
	}
	
	private Node put(Node x,Point2D p,int level){
		if(x == null){
			size++;
//			StdOut.print(new RectHV(xmin, ymin, xmax, ymax));
			return new Node(p,new RectHV(xmin, ymin, xmax, ymax));
		}
		if(p.x() == x.p.x() && p.y() == x.p.y())
			return x;
		else if(level % 2 == 0 && p.x() < x.p.x()){
			xmax = x.p.x();
			x.lb = put(x.lb,p,level + 1);
		}
		else if(level % 2 == 0 && p.x() >= x.p.x()){
			xmin = x.p.x();
			x.rt = put(x.rt,p,level + 1);
		}
		else if(level % 2 == 1 && p.y() < x.p.y()){
			ymax = x.p.y();
			x.lb = put(x.lb,p,level + 1);
		}
		
		else if(level % 2 == 1 && p.y() >= x.p.y()){
			ymin = x.p.y();
			x.rt = put(x.rt,p,level + 1);
		}
		return x;
	}
	
	/**
	 * does the set contain the point p?
	 * @param p
	 * @return
	 */
	public boolean contains(Point2D p){
		Node x = root;
		int level = 0;
		while(x != null){
			if(p.x() == x.p.x() && p.y() == x.p.y())
				return true;
			if((level % 2 == 0 && p.x() < x.p.x()) 
					|| (level % 2 == 1 && p.y() < x.p.y()))
				x = x.lb;
			else if((level % 2 == 0 && p.x() >= x.p.x())
					|| (level % 2 == 1 && p.y() >= x.p.y()))
				x = x.rt;
			level++;
		}
		return false;
	}
	
	/**
	 * draw all of the points to standard draw
	 */
	public void draw(){
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius();
		new Point2D(0,0).drawTo(new Point2D(0,1));
		new Point2D(0,1).drawTo(new Point2D(1,1));
		new Point2D(1,1).drawTo(new Point2D(1,0));
		new Point2D(1,0).drawTo(new Point2D(0,0));
		if(root != null)
			root.draw(0);
	}
	
	/**
	 * all points in the set that are inside the rectangle
	 * @param rect
	 * @return
	 */
	public Iterable<Point2D> range(RectHV rect){
		rangeResult = null;
		rangeResult = new ArrayList<Point2D>();
		get(rect,root,0);
//		StdOut.println(rangeResult.size());
		return rangeResult;
	}
	
	private void get(RectHV rect,Node x,int level){
		if(x == null)
			return;
		if(rect.contains(x.p))
			rangeResult.add(x.p);
		if(level % 2 == 0 && rect.xmax() < x.p.x())
			get(rect,x.lb,level + 1);
		else if(level % 2 == 0 && rect.xmin() > x.p.x())
			get(rect,x.rt,level + 1);
		else if(level % 2 == 1 && rect.ymax() < x.p.y())
			get(rect,x.lb,level + 1);
		else if(level % 2 == 1 && rect.ymin() > x.p.y())
			get(rect,x.rt,level + 1);
		else{
			get(rect,x.lb,level + 1);
			get(rect,x.rt,level + 1);
		}
	}
	
	/**
	 * a nearest neighbor in the set to p; null if set is empty
	 * @param p
	 * @return
	 */
	public Point2D nearest(Point2D p){
		near = root.p;
		searchNeighbor(root, p,0);
		return near;
	}
	
	private void searchNeighbor(Node x,Point2D p,int level){
		if(x == null)
			return;
		double cur = p.distanceTo(x.p);
		double distance = x.rect.distanceTo(p);
		double mindis = p.distanceTo(near);
		if(mindis > cur)
			near = x.p;
		if(mindis < distance)
			return;
//		StdOut.print(1);
		if(level % 2 == 0 && p.x() <= x.p.x() || level % 2 == 1 && p.y() <= x.p.y()){
			searchNeighbor(x.lb,p,level + 1);
			searchNeighbor(x.rt,p,level + 1);
		}
		else{
			searchNeighbor(x.rt,p,level + 1);
			searchNeighbor(x.lb,p,level + 1);
		}
			
	}
	
	private static class Node {
		   private Point2D p;      // the point
		   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		   private Node lb;        // the left/bottom subtree
		   private Node rt;        // the right/top subtree
		   
		   public Node(Point2D point,RectHV r){
			   p = point;
			   rect = r;
			   lb = null;
			   rt = null;
		   }
		   
		   public void draw(int level){
			   StdDraw.setPenColor(StdDraw.BLACK);
			   StdDraw.setPenRadius(4);
			   p.draw();
			   StdDraw.setPenRadius();
			   if(level % 2 == 0){
				   StdDraw.setPenColor(StdDraw.RED);
				   new Point2D(p.x(), rect.ymin()).drawTo(new Point2D(p.x(),rect.ymax()));
			   }
			   else if(level % 2 == 1){
				   StdDraw.setPenColor(StdDraw.BLUE);
				   new Point2D(rect.xmin(),p.y()).drawTo(new Point2D(rect.xmax(),p.y()));
			   }
			   if(lb != null)
				   lb.draw(level + 1);
			   if(rt != null)
				   rt.draw(level + 1);
		   }
	}
}
