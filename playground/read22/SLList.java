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

    public SLList(int x) {
        first = new IntNode(x, null);
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    /** Retrieves the front item from the list. */
    public int getFirst() {
        return first.item;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        IntNode lastNode = first;
        while (lastNode.next != null) {
            lastNode = lastNode.next;
        }
        lastNode.next = new IntNode(x, null);
    }

    /** Returns the number of items in the list using recursion. */
    public int size() {
        // needs a helper method to implement recursively
        return sizeHelper(first);
    }

    private static int sizeHelper(IntNode list) {
        // this method can be static
        // this method can be named help too
        if (list.next == null) {
            return 1;
        }

        return 1 + list.next.size();
    }

}