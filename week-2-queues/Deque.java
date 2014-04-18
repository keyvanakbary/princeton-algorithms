import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size()  {
        return size;
    }

    public void addFirst(Item item) {
        assertNotNull(item);
        Node oldFirst = first;
        first = new Node();
        first.next = oldFirst;
        first.item = item;
        if (size > 0) oldFirst.prev = first;
        else last = first;
        size++;
    }

    private void assertNotNull(Item item) {
        if (item == null) throw new NullPointerException();
    }

    public void addLast(Item item) {
        assertNotNull(item);
        Node oldLast = last;
        last = new Node();
        last.prev = oldLast;
        last.item = item;
        if (size > 0) oldLast.next = last;
        else first = last;
        size++;
    }

    public Item removeFirst() {
        assertNotEmpty();
        Item item = first.item;
        if (size > 1) {
            first = first.next;
            first.prev = null;
        } else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    private void assertNotEmpty() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    public Item removeLast() {
        assertNotEmpty();
        Item item = last.item;
        if (size > 1) {
            last = last.prev;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (current == null) throw new NoSuchElementException();
            Item value = current.item;
            current = current.next;
            return value;
        }
    }
}