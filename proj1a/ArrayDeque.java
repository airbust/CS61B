public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int front;
    private int back;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        front = 0;
        back = -1;
    }

    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public void addFirst(Item item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        size += 1;
        front = (front - 1 + items.length) % items.length;
        items[front] = item;
    }

    public void addLast(Item item) {
        if (size == items.length) {
            resize(2 * items.length);
        }
        size += 1;
        back = (back + 1) % items.length;
        items[back] = item;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int i = front;
        while (i != back) {
            System.out.print(items[i] + " ");
            i = (i + 1) % items.length;
        }
        System.out.println(items[i]);
    }

    public Item removeFirst() {
        Item x = items[front];
        if (size / items.length < 0.25) {
            resize(items.length / 2);
        }
        size -= 1;
        items[front] = null;
        front = (front + 1) % items.length;
        return x;
    }

    public Item removeLast() {
        Item x = items[back];
        if (size / items.length < 0.25) {
            resize(items.length / 2);
        }
        size -= 1;
        items[back] = null;
        back = (back - 1 + items.length) % items.length;
        return x;
    }

    public Item get(int index) {
        if (index < 0 || index > size - 1) {
            return null;
        }
        index += front;
        return items[index % items.length];
    }
}
