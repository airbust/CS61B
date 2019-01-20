public class LinkedListDeque<T> {
	private class StuffNode {
		public T item;
		public StuffNode prev;
		public StuffNode next;

		public StuffNode(T i, StuffNode p, StuffNode n) {
			item = i;
			prev = p;
			next = n;
		}
	}

	private StuffNode sentinel;
	private StuffNode last;
	public int size;

	public LinkedListDeque() {
		sentinel = new StuffNode(null, sentinel, null);
		last = sentinel;
		size = 0;
	}

	public void addFirst(T item) {
		size += 1;
		sentinel.next = new StuffNode(item, sentinel, sentinel.next);
		if (last == sentinel)
		    last = last.next;
        sentinel.prev = last;
	}

	public void addLast(T item) {
		size += 1;
		last.next = new StuffNode(item, last, sentinel);
		last = last.next;
        sentinel.prev = last;
	} 

	public boolean isEmpty() {
		if (size == 0)
			return true;
		return false;
	}

	public int size() {
		return size;
	}

	public void printDeque() {
		StuffNode p = sentinel;

		while (p.next != sentinel) {
		    p = p.next;
			System.out.print(p.item);
			System.out.print(' ');
		}
		System.out.println();
	}

	public T removeFirst() {
		if (size == 0)
			return null;
		size -= 1;
		StuffNode p = sentinel.next;
		if (sentinel.next != sentinel.prev)
		    sentinel.next.next.next = sentinel;
		sentinel.prev = last;
		sentinel.next = sentinel.next.next;
		return p.item;
	}

	public T removeLast() {
		if (size == 0)
			return null;
		size -= 1;
		StuffNode p = last;
		last.prev.next = sentinel;
		last = last.prev;
		sentinel.prev = last;
		return p.item;
	}

	public T get(int index) {
	    StuffNode p = sentinel;
        int cur = 0;

	    while (p.next != sentinel) {
	        p = p.next;
	        if (cur == index)
	            return p.item;
	        cur += 1;
        }
		return null;
	}

	public T getRecursive(int index) {
	    if (index > size - 1)
	        return null;
        return getRecursiveHelper(sentinel.next, index);
	}

	private T getRecursiveHelper(StuffNode p, int index) {
	    if (index == 0)
	        return p.item;
	    return getRecursiveHelper(p.next, index - 1);
    }
}