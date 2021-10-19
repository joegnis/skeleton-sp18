public class SLList {
    public class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first;
    private int size;

    public SLList() {
        size = 0;
        first = null;
    }

    public SLList(int x) {
        first = new IntNode(x, null);
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, first);
        size += 1;
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return first.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        if (size == 0) {
            first = new IntNode(x, null);
            size = 1;
            return;
        }

        IntNode lastNode = first;
        while (lastNode.next != null) {
            lastNode = lastNode.next;
        }
        lastNode.next = new IntNode(x, null);
        size += 1;
    }

    public int size() {
        return size;
    }
}