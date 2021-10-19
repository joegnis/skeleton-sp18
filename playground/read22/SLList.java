public class SLList {
    public class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode sentinel = new IntNode(0, null);
    private int size;

    public SLList() {
        size = 0;
    }

    public SLList(int x) {
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return sentinel.next.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        IntNode lastNode = sentinel;
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