package lab9;

import java.util.*;
import java.util.function.Consumer;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private Node getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            return p;
        } else if (cmp < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Node target = getHelper(key, root);
        if (target == null) return null;
        return target.value;
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }

        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }

        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private class BSTMapIterator implements Iterator<K> {
        private final Stack<Node> nodeStack;
        private Node curNode;

        public BSTMapIterator(Node root) {
            nodeStack = new Stack<>();
            curNode = root;
        }

        @Override
        public boolean hasNext() {
            return curNode != null || !nodeStack.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (curNode != null) {
                nodeStack.push(curNode);
                curNode = curNode.left;
            }
            Node nextNode = nodeStack.pop();
            curNode = nextNode.right;

            return nextNode.key;
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (K key : this) {
            set.add(key);
        }
        return set;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node toRemove = getHelper(key, root);
        if (toRemove == null) return null;
        V valueToRemove = toRemove.value;

        root = searchAndRemove(root, key);
        return valueToRemove;
    }

    private Node searchAndRemove(Node p, K key) {
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = searchAndRemove(p.left, key);
        } else if (cmp > 0) {
            p.right = searchAndRemove(p.right, key);
        } else {
            // leaf
            if (p.left == null && p.right == null) {
                return null;
            }

            // only child
            if (p.left == null || p.right == null) {
                return p.left != null ? p.left : p.right;
            }

            // two children
            Node successor = p.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            V pVal = p.value;
            p.key = successor.key;
            p.value = successor.value;
            successor.key = key;
            successor.value = pVal;

            p.right = searchAndRemove(p.right, key);
        }

        return p;
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node toRemove = getHelper(key, root);
        if (toRemove == null || toRemove.value != value) return null;
        V valueToRemove = toRemove.value;

        root = searchAndRemove(root, key);
        return valueToRemove;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator(root);
    }
}
