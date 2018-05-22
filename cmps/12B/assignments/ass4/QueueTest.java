// Ilay Raz
// ilraz
// CMPS12B-02
// 2/23/18
// QueueTest.java

public class QueueTest {
    public static void main(String[] args) {
        Queue queue = new Queue();

        assert queue.isEmpty();
        assert queue.length() == 0;

        // queue.dequeue();
        // queue.dequeueAll();

        System.out.println(queue.toString());

        queue.enqueue(5);
        queue.enqueue(6);

        assert !queue.isEmpty();
        assert queue.length() == 2;

        System.out.println(queue.toString());

        assert (int)(queue.dequeue()) == 5;
        assert (int)(queue.peek()) == 6;
        assert queue.length() == 1;

        queue.dequeueAll();
        assert queue.isEmpty();
        assert queue.length() == 0;

    }
}
