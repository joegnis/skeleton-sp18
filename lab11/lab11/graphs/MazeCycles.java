package lab11.graphs;


import edu.princeton.cs.algs4.Stack;

import java.util.stream.IntStream;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final Maze maze;
    private final int[] edgeToLocal;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        edgeToLocal = new int[maze.V()];
    }

    @Override
    public void solve() {
        while (true) {
            int startVertex = IntStream.range(0, marked.length)
                    .filter(i -> !marked[i]).findFirst().orElse(-1);
            if (startVertex == -1 || dfs(startVertex)) {
                break;
            }
        }
    }

    private boolean dfs(int startVertex) {
        final Stack<Integer> fringe = new Stack<>();
        fringe.push(startVertex);
        distTo[startVertex] = 0;
        edgeToLocal[startVertex] = startVertex;

        boolean found = false;
        while (!found && !fringe.isEmpty()) {
            int vertex = fringe.pop();
            if (marked[vertex]) {
                continue;
            }
            // Only marks after visit
            marked[vertex] = true;
            announce();
            for (int neighbor : maze.adj(vertex)) {
                if (neighbor == edgeToLocal[vertex]) {
                    // Skips parent
                    continue;
                }
                if (marked[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    int v = vertex;
                    while (v != neighbor) {
                        edgeTo[v] = edgeToLocal[v];
                        v = edgeTo[v];
                    }
                    announce();
                    found = true;
                    break;
                } else {
                    fringe.push(neighbor);
                    distTo[neighbor] = distTo[vertex] + 1;
                    edgeToLocal[neighbor] = vertex;
                }
            }
        }
        return found;
    }
}

