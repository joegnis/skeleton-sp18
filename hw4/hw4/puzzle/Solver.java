package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.*;

/**
 * Immutable
 */
public class Solver {
    private final Map<WorldState, Integer> estimatedDistanceCache;
    private final Set<WorldState> usedStates;
    private SearchNode lastSearchNode;
    // Debugging only
    private int totalInsertToFringe;
    private int countCacheHits;
    private int countSkipsUsedState;
    private int countSkipsEqualToGrandparent;
    private int countSkipsBothUsedStateAndEqualToGrandparent;

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
        usedStates = new HashSet<>();

        final MinPQ<SearchNode> fringe = new MinPQ<>();
        fringe.insert(new SearchNode(initial, 0, null));
        totalInsertToFringe = 1;
        countCacheHits = 0;
        countSkipsUsedState = 0;
        countSkipsEqualToGrandparent = 0;
        countSkipsBothUsedStateAndEqualToGrandparent = 0;
        while (!fringe.isEmpty()) {
            SearchNode node = fringe.delMin();
            WorldState state = node.state;
            if (state.isGoal()) {
                lastSearchNode = node;
                break;
            }
            // Only marks a state as used after it is dequeued
            // This approach uses more memory than simply checking equality to grandparent, but it does skip more
            // This approach actually includes the situations where a state is equal to its grandparent
            // Both approaches are used for study purpose
            // Run EightPuzzleSolver.java to see more info
            usedStates.add(state);

            SearchNode prevNode = node.prevNode;
            for (WorldState neighbor : state.neighbors()) {
                boolean equalsToGrandparent = prevNode != null && neighbor.equals(prevNode.state);
                boolean used = usedStates.contains(neighbor);
                if (prevNode == null || (!equalsToGrandparent && !used)) {
                    SearchNode neighborNode = new SearchNode(neighbor, node.moves + 1, node);
                    fringe.insert(neighborNode);
                    totalInsertToFringe++;
                }
                if (equalsToGrandparent) {
                    countSkipsEqualToGrandparent++;
                }
                if (used) {
                    countSkipsUsedState++;
                }
                if (used && equalsToGrandparent) {
                    countSkipsBothUsedStateAndEqualToGrandparent++;
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