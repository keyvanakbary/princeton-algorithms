public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        int num = Integer.parseInt(args[0]);
        while(num-- > 0) {
            StdOut.println(rq.dequeue());
        }
    }
}
