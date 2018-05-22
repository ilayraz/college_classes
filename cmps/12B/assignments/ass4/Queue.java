// Ilay Raz
// ilraz
// CMPS12B-02
// 2/23/18
// Queue.java

public class Queue implements QueueInterface {
    private class Node {
        public Object data;
        public Node next;

        public Node(Object myData) {
            data = myData;
            next = null;
        }
    }

    private int size;
    private Node head;
    private Node tail;

    public Queue() {
        size = 0;
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int length() {
        return size;
    }

    public void enqueue(Object newItem) {
        if (tail == null)
            head = tail = new Node(newItem);
        else {
            tail.next = new Node(newItem);
            tail = tail.next;
        }
        size++;
    }

    public Object dequeue() throws QueueEmptyException {
        if (isEmpty())
            throw new QueueEmptyException("Queue is empty");
        Node node = head;
        head = head.next;
        size--;
        if (head == null)
            tail = null;
        return node.data;
    }

    public Object peek() throws QueueEmptyException {
        if (isEmpty())
            throw new QueueEmptyException("Queue is empty");
        return head.data;
    }

    public void dequeueAll() throws QueueEmptyException {
        if (isEmpty())
            throw new QueueEmptyException("Queue is empty");
        head = null;
        tail = null;
        size = 0;
    }

    public Queue clone() {
        Queue queue = new Queue();
        for (Node node = head; node != null; node = node.next)
            queue.enqueue(node.data);
        return queue;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Node node = head; node != null; node = node.next)
            builder.append(node.data.toString() + " ");
        return builder.toString();
    }
}
