import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item>{

	private Node first,last;
	private int N;
	
	/**
	 * inner class for store items
	 * @author zhouyu
	 *
	 */
	private class Node{
		Item item;
		Node next,before;
	}
	/**
	 * 
	 */
	public Deque(){
		this.N = 0;
		first = null;
		last = null;
	}
	
	/**
	 * is the deque empty?
	 * @return
	 */
	public boolean isEmpty(){
		return N == 0;
	}
	
	/**
	 * return the number of items on the deque
	 * @return
	 */
	public int size(){
		return N;
	}
	
	/**
	 * insert the item at the front
	 * @param item
	 */
	public void addFirst(Item item) throws NullPointerException {
		if(item == null)
			throw new NullPointerException();
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = null;
		first.before = null;
		if(isEmpty())
			last = first;
		else{
			first.next = oldfirst;
			oldfirst.before = first;
		}
		N++;
	}
	
	/**
	 * insert the item at the end
	 * @param item
	 */
	public void addLast(Item item){
		if(item == null)
			throw new NullPointerException();
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.before = null;
		if(isEmpty())
			first = last;
		else{
			last.before = oldlast;
			oldlast.next = last;
		}
		N++;
	}
	
	/**
	 * delete and return the item at the front
	 * @return
	 */
	public Item removeFirst() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		if(size() == 1)
			last = null;
		else if(first != null)
			first.before = null;
		N--;
		return item;
	}
	
	/**
	 * delete and return the item at the end
	 * @return
	 */
	public Item removeLast() throws NoSuchElementException {
		if(isEmpty())
			throw new NoSuchElementException();
		Item item = last.item;
		last = last.before;
		if(size() == 1)
			first = null;
		else if(last != null)
			last.next = null;
		N--;
		return item;
	}
	
	/**
	 * return an iterator over items in order from front to end
	 */
	public Iterator<Item> iterator(){
		return new DequeIterator();
	}
	
	/**
	 * inner class implement hasNext() next()
	 */
	private class DequeIterator implements Iterator<Item>{
		private Node current = first;
		
		/**
		 * if iterator has next item
		 */
		public boolean hasNext(){
			return current != null;
		}
		
		/**
		 * reture an item from iterator
		 */
		public Item next() throws NoSuchElementException{
			if(current == null)
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next; 
			return item;
		}
		
		/**
		 * not supported
		 */
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	}
}
