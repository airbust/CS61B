package lab11.graphs;

import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private ArrayDeque<Integer> q = new ArrayDeque<>();

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        marked[s] = true;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int hDist = Math.abs(maze.toX(t) - maze.toX(v));
        int vDist = Math.abs(maze.toY(t) - maze.toY(v));
        return hDist + vDist;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        int min = Integer.MAX_VALUE;
        int res = q.peek();

        for (int w : q) {
            if (h(w) < min) {
                min = h(w);
                res = w;
            }
        }

        return res;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        q.add(s);
        while (!q.isEmpty()) {
            s = findMinimumUnmarked();
            q.remove(s);
            announce();
            if (s == t) {
                targetFound = true;
            }
            if (targetFound) {
                return;
            }
            for (int w : maze.adj(s)) {
                if (!marked[w]) {
                    edgeTo[w] = s;
                    distTo[w] = distTo[s] + 1;
                    marked[w] = true;
                    q.add(w);
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }
}


