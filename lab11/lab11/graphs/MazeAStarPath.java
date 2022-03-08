package lab11.graphs;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private final int source;
    private final int target;
    private final IndexMinPQ<Integer> fringe;
    private final Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        fringe = new IndexMinPQ<>(maze.V());
    }

    /** Estimate of the distance from v to the target. */
    private int heuristic(int v) {
        // Manhattan distance
        return Math.abs(maze.toX(v) - maze.toX(target)) + Math.abs(maze.toY(v) - maze.toY(target));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void aStar(int sourceVertex, int targetVertex) {
        for (int v = 0; v < maze.V(); v++) {
            edgeTo[v] = Integer.MAX_VALUE;
            distTo[v] = Integer.MAX_VALUE;
            marked[v] = false;
            fringe.insert(v, Integer.MAX_VALUE);
        }
        distTo[sourceVertex] = 0;
        edgeTo[sourceVertex] = sourceVertex;
        marked[sourceVertex] = true;
        fringe.decreaseKey(sourceVertex, heuristic(sourceVertex));
        announce();

        while (!fringe.isEmpty()) {
            int v = fringe.delMin();
            for (int neighbor : maze.adj(v)) {
                if (marked[neighbor]) {
                    // don't have to check but improves runtime
                    continue;
                }
                relax(v, neighbor);
                announce();
                if (neighbor == targetVertex) {
                    return;
                }
            }
        }
    }

    private void relax(int edgeStart, int edgeEnd) {
        final int weight = 1;
        int newDist = distTo[edgeStart] + weight;
        if (newDist < distTo[edgeEnd]) {
            distTo[edgeEnd] = newDist;
            edgeTo[edgeEnd] = edgeStart;
            marked[edgeEnd] = true;
            fringe.changeKey(edgeEnd, newDist + heuristic(edgeEnd));
        }
    }

    @Override
    public void solve() {
        aStar(source, target);
    }

}

