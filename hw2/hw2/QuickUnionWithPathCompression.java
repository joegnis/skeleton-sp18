package hw2;

public class QuickUnionWithPathCompression implements IUnionFind {
    private final int[] parents;
    private int numComponents;

    public QuickUnionWithPathCompression(int n) {
        parents = new int[n];
        numComponents = n;

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
            numComponents--;
        }
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int find(int p) {
        validate(p);
        if (p == parents[p]) return p;
        parents[p] = find(parents[p]);
        return parents[p];
    }

    @Override
    public int count() {
        return numComponents;
    }

    private void validate(int p) {
        if (p < 0 || p >= parents.length) {
            throw new IllegalArgumentException("Index out of bound");
        }
    }
}
