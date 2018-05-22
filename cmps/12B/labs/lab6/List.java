// Ilay Raz
// ilraz
// CMPS12M-02
// 2/9/18
// List.java


@SuppressWarnings("overrides")
class List<T> implements ListInterface<T> {
    private class Node<T> {
        public T item;
        public Node<T> next;

        public Node(T myItem) {
            item = myItem;
        }
    }

    int size;
    Node<T> head;
    public List() {
        size = 0;
    }

    public boolean isEmpty() {
        return size ==0;
    }

    public int size() {
        return size;
    }

    private Node<T> getNode(int index) throws ListIndexOutOfBoundsException {
        Node <T> node = head;
        for (int i = 1; i != index; i++, node = node.next);
        return node;
    }

    public T get(int index) throws ListIndexOutOfBoundsException {
        if (index < 1 || index > size)
            throw new ListIndexOutOfBoundsException("get(): invalid index: " + index);
        return getNode(index).item;
    }

    public void add(int index, T newItem) throws ListIndexOutOfBoundsException {
        if (index < 1 || index > size + 1)
            throw new ListIndexOutOfBoundsException("add(): invalid index: " + index);
        Node<T> node = new Node<T>(newItem);
        if (index == 1) {
            node.next = head;
            head = node;
        } else {
            Node<T> prev = getNode(index - 1);
            node.next = prev.next;
            prev.next = node;
        }
        size++;
    }

    public void remove(int index) throws ListIndexOutOfBoundsException {
        if (index < 1 || index > size)
            throw new ListIndexOutOfBoundsException("remove(): invalid index: " + index);
        if (index == 1)
            head = head.next;
        else if (index == size)
            getNode(index - 1).next = null;
        else {
            Node<T> prev = getNode(index - 1);
            prev.next = prev.next.next;
        }
        size--;
    }

    public void removeAll() {
        size = 0;
        head = null;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Node<T> node = head; node != null; node  = node.next) {
            str.append(node.item.toString());
            str.append(" ");
        }
        return str.toString();
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object rhs) {
        if (this.getClass() == rhs.getClass()) {
            List<T> other = (List<T>)rhs;
            for (int i = 1; i <= size && i <= other.size(); i++)
                if (get(i) != other.get(i))
                    return false;
            return true;
        }
        return false;
    }
}
