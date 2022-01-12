package hw2;

public class PercolationFactory {
    public Percolation make(int N) {
        return new Percolation(N, WeightedQuickUnion.class);
    }

    public <U extends IUnionFind> Percolation make(int N, Class<U> unionClass) {
        return new Percolation(N, unionClass);
    }
}
