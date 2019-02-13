package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Stack<WorldState> path;

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState world;
        private int moves;
        private SearchNode prev;
        private int priority;

        private SearchNode(WorldState w, int m, SearchNode p) {
            world = w;
            moves = m;
            prev = p;
            priority = moves + w.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode node) {
            return this.priority - node.priority;
        }

        private WorldState world() {
            return world;
        }

        private int moves() {
            return moves;
        }

        private SearchNode prev() {
            return prev;
        }
    }

    public Solver(WorldState initial) {
        path = new Stack<>();
        MinPQ<SearchNode> PQ = new MinPQ<>();
        PQ.insert(new SearchNode(initial, 0, null));

        SearchNode goal = null;

        while (!PQ.isEmpty()) {
            SearchNode min = PQ.delMin();
            WorldState minWS = min.world();
            int minMoves = min.moves();
            SearchNode prev = min.prev();
            if (minWS.isGoal()) {
                goal = min;
                break;
            }
            for (WorldState s : minWS.neighbors()) {
                if (prev == null || !s.equals(prev.world())) {
                    PQ.insert(new SearchNode(s, minMoves + 1, min));
                }
            }
        }

        while (goal != null) {
            path.push(goal.world);
            goal = goal.prev;
        }
    }

    public int moves() {
        return path.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return path;
    }
}
