package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.*;

/**
 * Immutable
 */
public class Solver {
    private final Map<WorldState, Integer> estimatedDistanceCache;
    private SearchNode lastSearchNode;
    private int totalInsertFringe;  // Debugging only

    private class SearchNode implements Comparable<SearchNode> {
        private final WorldState state;
        private final int moves;
        private final SearchNode prevNode;

        private SearchNode(WorldState state, int moves, SearchNode prevNode) {
            this.state = state;
            this.moves = moves;
            this.prevNode = prevNode;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SearchNode that = (SearchNode) o;
            return moves == that.moves && state.equals(that.state) && Objects.equals(prevNode, that.prevNode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, moves, prevNode);
        }

        @Override
        public int compareTo(SearchNode o) {
            estimatedDistanceCache.putIfAbsent(state, state.estimatedDistanceToGoal());
            int distThis = estimatedDistanceCache.get(state);
            estimatedDistanceCache.putIfAbsent(o.state, o.state.estimatedDistanceToGoal());
            int distOther = estimatedDistanceCache.get(o.state);
            return (moves + distThis) - (o.moves + distOther);
        }
    }

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists.
     */
    public Solver(WorldState initial) {
        estimatedDistanceCache = new HashMap<>();

        final MinPQ<SearchNode> fringe = new MinPQ<>();
        fringe.insert(new SearchNode(initial, 0, null));
        totalInsertFringe = 1;
        while (!fringe.isEmpty()) {
            SearchNode node = fringe.delMin();
            WorldState state = node.state;
            if (state.isGoal()) {
                lastSearchNode = node;
                break;
            }

            SearchNode prevNode = node.prevNode;
            for (WorldState neighbor : state.neighbors()) {
                if (prevNode == null || !neighbor.equals(prevNode.state)) {
                    fringe.insert(new SearchNode(neighbor, node.moves + 1, node));
                    totalInsertFringe++;
                }
            }
        }
    }

    /**
     * Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState.
     */
    public int moves() {
        return lastSearchNode.moves;
    }

    /**
     * Returns a sequence of WorldStates from the initial WorldState
     * to the solution.
     */
    public Iterable<WorldState> solution() {
        Stack<WorldState> stack = new Stack<>();
        SearchNode node = lastSearchNode;
        while (node != null) {
            stack.push(node.state);
            node = node.prevNode;
        }
        List<WorldState> results = new ArrayList<>();
        stack.forEach(results::add);
        return results;
    }

    public int getTotalInsertFringe() {
        return totalInsertFringe;
    }
}