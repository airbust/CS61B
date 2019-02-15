package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private int s;
    private boolean cycleFound = false;
    private int cycle;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1, 1);
        edgeTo[s] = s;
    }

    @Override
    public void solve() {
        dfs(s);
        showCycle(cycle);
    }

    // Helper methods go here
    private void dfs(int v) {
        marked[v] = true;

        if (cycleFound) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(w);
                if (cycleFound) {
                    return;
                }
            } else {
                if (edgeTo[v] != w) {
                    edgeTo[w] = v;
                    cycle = w;
                    cycleFound = true;
                }
            }
        }
    }

    private void showCycle(int c) {
        if (!cycleFound) {
            for (int i = 0; i < maze.V(); i += 1) {
                edgeTo[i] = i;
            }
            announce();
            return;
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(c);
        int v = edgeTo[c];
        while (v != c) {
            stack.push(v);
            v = edgeTo[v];
        }

        for (int i = 0; i < maze.V(); i += 1) {
            edgeTo[i] = i;
        }

        while (!stack.isEmpty()) {
            v = stack.pop();
            edgeTo[v] = c;
            c = v;
        }
        announce();
    }
}

