package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int source;
    private final int target;
    private final Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        for (int v = 0; v < maze.V(); v++) {
            edgeTo[v] = Integer.MAX_VALUE;
            distTo[v] = Integer.MAX_VALUE;
            marked[v] = false;
        }
        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.offer(source);
        distTo[source] = 0;
        edgeTo[source] = source;
        // We mark each vertex once they are in the queue
        // This is different from DFS iterative
        marked[source] = true;
        announce();

        while (!fringe.isEmpty()) {
            int vertex = fringe.poll();
            for (int neighbor : maze.adj(vertex)) {
                if (marked[neighbor]) {
                    continue;
                }
                fringe.offer(neighbor);
                distTo[neighbor] = distTo[vertex] + 1;
                edgeTo[neighbor] = vertex;
                marked[neighbor] = true;
                announce();
                if (neighbor == target) {
                    return;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

