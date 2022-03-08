package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 * @author Josh Hug
 */
public class MazeDepthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private final int s;
    private final int t;
    private final Maze maze;
    private boolean targetFound = false;


    public MazeDepthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private void dfs(int v) {
        marked[v] = true;
        announce();

        if (v == t) {
            targetFound = true;
        }

        if (targetFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                announce();
                distTo[w] = distTo[v] + 1;
                dfs(w);
                if (targetFound) {
                    return;
                }
            }
        }
    }

    private void dfsIterative(int startVertex) {
        for (int v = 0; v < maze.V(); v++) {
            distTo[v] = Integer.MAX_VALUE;
            edgeTo[v] = Integer.MAX_VALUE;
            marked[v] = false;
        }
        final Stack<Integer> fringe = new Stack<>();
        fringe.push(startVertex);
        distTo[startVertex] = 0;
        edgeTo[startVertex] = startVertex;

        while (!fringe.isEmpty()) {
            int vertex = fringe.pop();
            if (marked[vertex]) {
                // Only enters when there is any cycle
                System.out.printf("Skipped visited vertex (%d,%d)%n", maze.toX(vertex), maze.toY(vertex));
                continue;
            }
            // Only marks after visit
            // Otherwise it may become BFS
            marked[vertex] = true;
            announce();
            for (int neighbor : maze.adj(vertex)) {
                if (neighbor == edgeTo[vertex]) {
                    System.out.printf("Skipped parent vertex (%d,%d) of vertex (%d,%d)%n", maze.toX(neighbor), maze.toY(neighbor), maze.toX(vertex), maze.toY(vertex));
                    continue;
                }
                if (!marked[neighbor]) {
                    fringe.push(neighbor);
                    distTo[neighbor] = distTo[vertex] + 1;
                    edgeTo[neighbor] = vertex;
                }
            }
        }
    }

    @Override
    public void solve() {
        dfsIterative(s);
        System.out.println("DFS finished.");
    }
}

