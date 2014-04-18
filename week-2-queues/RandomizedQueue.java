import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        autoEnlarge();
        a[N++] = item;
    }

    private void autoEnlarge() {
        if (N == a.length) resize(2*a.length);
    }

    public Item dequeue() {
        assertNotEmpty();
        int index = randomIndex();
        Item item = a[index];
        a[index] = a[N-1];
        a[N-1] = null;
        N--;
        autoShrink();
        return item;
    }

    private void assertNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    private void autoShrink() {
        if (N > 0 && N == a.length/4) resize(a.length/2);
    }

    private int randomIndex()
    {
        return StdRandom.uniform(0, N);
    }

    public Item sample() {
        assertNotEmpty();
        return a[randomIndex()];
    }

    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private Item[] r;
        private int i;

        public RandomArrayIterator() {
            copyQueue();
            StdRandom.shuffle(r);
        }

        private void copyQueue() {
            r = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                r[i] = a[i];
            }
        }

        public boolean hasNext() {
            return i < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return r[i++];
        }
    }
}