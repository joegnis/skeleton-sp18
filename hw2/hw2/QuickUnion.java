package hw2;

public class QuickUnion implements IUnionFind {
    private final int[] parents;
    private int count;

    public QuickUnion(int n) {
        parents = new int[n];
        count = n;

        for (int i = 0; i < n; i++) {
            parents[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ) {
            parents[rootP] = rootQ;
            count--;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int find(int p) {
        validate(p);
        while (p != parents[p]) {
            p = parents[p];
        }
        return p;
    }

    @Override
    public int count() {
        return count;
    }

    private void validate(int p) {
        if (p < 0 || p >= parents.length) {
            throw new IllegalArgumentException("Index out of bound");
        }
    }
}
