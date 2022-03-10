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
    private int totalInsertToFringe;  // Debugging only
    private int countCacheHits;  // Debugging only

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
            return compareTo(that) == 0;
        }

        @Override
        public int hashCode() {
            return getPriority(this);
        }

        @Override
        public int compareTo(SearchNode o) {
            return getPriority(this) - getPriority(o);
        }

        @Override
        public String toString() {
            return String.format("SearchNode{state=%s, moves=%s, prevNode=%s}",
                    state, moves, prevNode == null ? "null" : String.format("{state=%s, moves=%s}", prevNode.state, prevNode.moves));
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
        totalInsertToFringe = 1;
        countCacheHits = 0;
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
                    SearchNode neighborNode = new SearchNode(neighbor, node.moves + 1, node);
                    fringe.insert(neighborNode);
                    totalInsertToFringe++;
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

    private int getPriority(SearchNode node) {
        WorldState state = node.state;
        Integer estimate = estimatedDistanceCache.putIfAbsent(state, state.estimatedDistanceToGoal());
        if (estimate != null) {
            countCacheHits++;
        }
        return estimatedDistanceCache.get(state) + node.moves;
    }
}