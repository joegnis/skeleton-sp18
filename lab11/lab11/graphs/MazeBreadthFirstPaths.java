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
        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.offer(source);
        distTo[source] = 0;
        edgeTo[source] = source;
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

