public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int first;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = (items.length - size) / 2;
        nextLast = nextFirst + 1;
        first = nextFirst;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int tempLength = items.length - first;
        System.arraycopy(items, first, a, a.length / 2 - 1, tempLength);
        if (tempLength != items.length) {
            System.arraycopy(items, 0, a, a.length / 2 - 1 + tempLength, first);
        }
        items = a;
        nextFirst = (items.length / 2 - 1 - 1 + items.length) % items.length;
        first = (nextFirst + 1) % items.length;
        nextLast = (nextFirst + size + 1) % items.length;
    }

    private void resizeDown(int capacity) {
        if (capacity < 8) {
            return;
        }
        T[] a = (T[]) new Object[capacity];
        int tempLength = items.length - first;
        if (tempLength < size) {
            System.arraycopy(items, first, a, a.length / 2 - 1, tempLength);
            int left = size - tempLength;
            System.arraycopy(items, nextLast - 1, a, a.length / 2 - 1 + tempLength, left);
        } else {
            System.arraycopy(items, first, a, a.length / 2 - 1, size);
        }
        items = a;
        nextFirst = (items.length / 2 - 1 - 1 + items.length) % items.length;
        first = (nextFirst + 1) % items.length;
        nextLast = (nextFirst + size + 1) % items.length;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        size += 1;
        first = nextFirst;
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        size += 1;
        items[nextLast] = item;
        if (items[first] == null) {
            first = nextLast;
        }
        nextLast = (nextLast + 1) % items.length;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = items[first];
        items[first] = null;
        first = (first + 1) % items.length;
        nextFirst = (nextFirst + 1) % items.length;
        size -= 1;
        if ((double) size / items.length < 0.25) {
            resizeDown(items.length / 2);
        }
        return x;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        T x = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if ((double) size / items.length < 0.25) {
            resizeDown(items.length / 2);
        }
        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        index += first;
        return items[index % items.length];
    }
}
