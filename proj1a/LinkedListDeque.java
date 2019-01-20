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
	private int size;

	public LinkedListDeque() {
		sentinel = new StuffNode(null, null, null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;
	}

	public void addFirst(T item) {
		size += 1;
		sentinel.next = new StuffNode(item, sentinel, sentinel.next);
		sentinel.next.next.prev = sentinel.next;
	}

	public void addLast(T item) {
		size += 1;
        sentinel.prev.next = new StuffNode(item, sentinel.prev, sentinel);
        sentinel.prev = sentinel.prev.next;
	} 

	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	public int size() {
		return size;
	}

	public void printDeque() {
		StuffNode p = sentinel;

		while (p.next != sentinel) {
		    p = p.next;
			System.out.print(p.item + " ");
		}
		System.out.println();
	}

	public T removeFirst() {
		if (size == 0) {
			return null;
		}
		size -= 1;
		T i = sentinel.next.item;
		sentinel.next.next.prev = sentinel;
		sentinel.next = sentinel.next.next;
		return i;
	}

	public T removeLast() {
		if (size == 0) {
			return null;
		}
		size -= 1;
		T i = sentinel.prev.item;
		sentinel.prev.prev.next = sentinel;
		sentinel.prev = sentinel.prev.prev;
		return i;
	}

	public T get(int index) {
		if (index < 0 || index > size - 1) {
			return null;
		}

		StuffNode p = sentinel;

	    while (index >= 0) {
	        p = p.next;
	        index -= 1;
        }
		return p.item;
	}

	public T getRecursive(int index) {
	    if (index < 0 || index > size - 1) {
			return null;
		}
        return getRecursiveHelper(sentinel.next, index);
	}

	private T getRecursiveHelper(StuffNode p, int index) {
	    if (index == 0)
	        return p.item;
	    return getRecursiveHelper(p.next, index - 1);
    }
}
