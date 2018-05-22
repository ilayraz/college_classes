// Ilay Raz
// ilraz
// CMPS12B-02
// 2/6/18
// Dictionary.java

public class Dictionary implements DictionaryInterface {

    private class Node {
        public String key;
        public String value;
        public Node next;
        public Node parent;

        public Node(String myKey, String myValue) {
            key = myKey;
            value = myValue;
            next = null;
            parent = null;
        }

        public Node(String myKey, String myValue, Node myParent) {
            key = myKey;
            value = myValue;
            next = null;
            parent = myParent;
        }
    }

    private int size;
    private Node head;
    private Node tail;

    public Dictionary() {
        size = 0;
        head = null;
        tail = head;
    }

    private Node findKey(String key) {
        for (Node node = head; node != null; node = node.next)
            if (node.key == key)
                return node;
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public String lookup(String key) {
        Node node = findKey(key);
        return node == null ? null : node.value;
    }

    public void insert(String key, String value) throws DuplicateKeyException {
        if (lookup(key) != null)
            throw new DuplicateKeyException("cannot insert duplicate keys");
        if (size == 0) {
            head = new Node(key, value);
            tail = head;
            size++;
        } else {
            tail.next = new Node(key, value, tail);
            tail = tail.next;
            size++;
        }
    }

    public void delete(String key) throws KeyNotFoundException {
        Node node = findKey(key);
        if (node == null)
            throw new KeyNotFoundException("cannot delete non existent key");
        size--;
        if (size == 0)
            head = tail = null;
        else if (node == head)
            head = head.next;
        else if (node == tail) {
            tail = tail.parent;
            tail.next = null;
        } else {
            node.parent.next = node.next;
            node.next.parent = node.parent;
        }
    }

    public void makeEmpty() {
        head = tail = null;
        size = 0;
    }

    public String toString() {
        String string = "";
        for (Node node = head; node != null; node = node.next)
            string += node.key + " " + node.value + "\n";
        return string;
    }
}
