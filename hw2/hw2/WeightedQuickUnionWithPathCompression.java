package hw2;

public class WeightedQuickUnionWithPathCompression implements IUnionFind {
    private final int[] parents;
    private final int[] sizes;
    private int numComponents;

    public WeightedQuickUnionWithPathCompression(int n) {
        parents = new int[n];
        sizes = new int[n];
        numComponents = n;

        for (int i = 0; i < n; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ) {
            if (sizes[rootP] >= sizes[rootQ]) {
                parents[rootQ] = rootP;
                sizes[rootP] += sizes[rootQ];
            } else {
                parents[rootP] = rootQ;
                sizes[rootQ] += sizes[rootP];
            }
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
