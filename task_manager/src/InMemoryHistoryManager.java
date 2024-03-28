import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{

    private static InMemoryHistoryManager instance;
    public static class DlinkedList<T> {
        private Node<T> head;

        public DlinkedList() {
            this.head = null;
        }
        public void linkLast(T data) {
            Node<T> newNode = new Node<>(data);
            if (head == null) {
                head = newNode;
            } else {
                Node<T> tail = head;
                while (tail.next != null) {
                    tail = tail.next;
                }
                tail.next = newNode;
                newNode.prev = tail;
            }
        }

        public ArrayList<T> getTasks() {
            ArrayList<T> arrayList = new ArrayList<>();
            Node<T> current = head;
            while (current != null) {
                arrayList.add(current.data);
                current = current.next;
            }
            return arrayList;
        }
        public void removeNode(Node<T> nodeToRemove) {
            Node<T> current = head;
            while (current != null) {
                if (current == nodeToRemove) {
                    if (current.prev != null) {
                        current.prev.next = current.next;
                    } else {
                        head = current.next;
                    }
                    if (current.next != null) {
                        current.next.prev = current.prev;
                    }
                    break;
                }
                current = current.next;
            }
        }
        public Node<T> getNodeByValue(T value) {
            Node<T> current = head;
            while (current != null) {
                if (current.data.equals(value)) {
                    return current;
                }
                current = current.next;
            }
            return null;
        }

    }
    private final DlinkedList<Subtask> list = new DlinkedList<>();
    private InMemoryHistoryManager() {}

    public static synchronized InMemoryHistoryManager getInstance() {
        if (instance == null) {
            instance = new InMemoryHistoryManager();
        }
        return instance;
    }

    @Override
    public void add(Subtask task) {
        Node<Subtask> curr = list.getNodeByValue(task);
        list.removeNode(curr);
        list.linkLast(task);
    }

    @Override
    public ArrayList<Subtask> getHistory() {
        return list.getTasks();
    }

}
