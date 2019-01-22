public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = (items.length - size) / 2;
        nextLast = nextFirst + 1;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int first = (nextFirst + 1) % items.length;
        int tempLength = items.length - first;
        System.arraycopy(items, first, a, a.length / 2 - 1, tempLength);
        if (tempLength != items.length) {
            System.arraycopy(items, 0, a, a.length / 2 - 1 + tempLength, first);
        }
        items = a;
        nextFirst = (items.length / 2 - 1 - 1 + items.length) % items.length;
        nextLast = (nextFirst + size) % items.length;
    }

    private void resizeDown(int capacity) {
        if (capacity < 8) {
            return;
        }
        T[] a = (T[]) new Object[capacity];
        int first = (nextFirst + 1) % items.length;
        int tempLength = items.length - first;
        if (tempLength < size) {
            System.arraycopy(items, first, a, a.length / 2 - 1, tempLength);
            System.arraycopy(items, nextLast - 1, a, a.length / 2 - 1 + tempLength, size - tempLength);
        } else {
            System.arraycopy(items, first, a, a.length / 2 - 1, size);
        }
        items = a;
        nextFirst = (a.length / 2 - 1 - 1 + items.length) % items.length;
        nextLast = (nextFirst + size) % items.length;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        size += 1;
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        size += 1;
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = (nextFirst + 1) % items.length;
        T x = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        if (size / items.length < 0.25) {
            resizeDown(items.length / 2);
        }
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        T x = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if (size / items.length < 0.25) {
            resizeDown(items.length / 2);
        }
        return x;
    }

    public T get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        index += (nextFirst + 1) % items.length;
        return items[index % items.length];
    }
}
