import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue <Item> implements Iterable<Item>{

	 private static final int DEF_CAPACITY = 4;

	    private int size;

	    private int capacity;

	    private Item[] data;

	    public RandomizedQueue() {
	        capacity = DEF_CAPACITY;
	        data = (Item[]) new Object[capacity];
	        size = 0;
	    }

	    public boolean isEmpty() {
	        return size == 0;
	    }

	    public int size() {
	        return size;
	    }

	    public void enqueue(Item item) {
	        if (item == null) {
	            throw new NullPointerException();
	        }
	        if (size == capacity) {
	            Item[] oldData = data;
	            capacity *= 2;
	            data = (Item[]) new Object[capacity];
	            for (int i = 0; i < size; i++) {
	                data[i] = oldData[i];
	            }
	            oldData = null;
	        }
	        data[size++] = item;
	    }

	    public Item dequeue() {
	        if (isEmpty()) {
	            throw new NoSuchElementException();
	        }
	        int pos = StdRandom.uniform(size);
	        Item item = data[pos];
	        data[pos] = data[size - 1];

	        data[size - 1] = null;
	        size--;
	        if (size > 0 && size == capacity / 4) {
	            Item[] oldData = data;
	            capacity /= 2;
	            data = (Item[]) new Object[capacity];
	            for (int i = 0; i < size; i++) {
	                data[i] = oldData[i];
	            }
	            oldData = null;
	        }
	        return item;
	    }

	    public Item sample() {
	        if (isEmpty()) {
	            throw new NoSuchElementException();
	        }
	        int pos = StdRandom.uniform(size);
	        Item item = data[pos];
	        return item;
	    }

	    @Override
	    public Iterator<Item> iterator() {
	        return new RandomizedQueueIterator();
	    }

	    private class RandomizedQueueIterator implements Iterator<Item> {

	        private int innerSize;

	        private int[] indices;

	        public RandomizedQueueIterator() {
	            innerSize = size;
	            indices = new int[innerSize];
	            for (int i = 0; i < innerSize; i++)
	                indices[i] = i;
	        }

	        @Override
	        public boolean hasNext() {
	            return innerSize > 0;
	        }

	        @Override
	        public Item next() {
	            if (!hasNext())
	                throw new NoSuchElementException();
	            int pos = StdRandom.uniform(innerSize);
	            int now = indices[pos];
	            indices[pos] = indices[innerSize - 1];
	            innerSize--;
	            return data[now];
	        }

	        @Override
	        public void remove() {
	            throw new UnsupportedOperationException();
	        }

	    }
}
