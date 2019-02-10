package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean[][] grid;
    private int OpenSitesNumber;
    private WeightedQuickUnionUF unionSet;
    private WeightedQuickUnionUF unionSetNoBot;
    private int bottom;
    private int top;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0");
        }
        this.N = N;
        grid = new boolean[N][N];
        for (int i = 0; i < grid.length; i += 1) {
            for (int j = 0; j < grid[i].length; j += 1) {
                grid[i][j] = false;
            }
        }

        OpenSitesNumber = 0;
        top = 0;
        bottom = N * N + 1;
        unionSet = new WeightedQuickUnionUF(N * N  + 2);
        unionSetNoBot = new WeightedQuickUnionUF(N * N + 2);
    }

    public void open(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("Invalid numbers for row and col");
        }

        if (grid[row][col]) {
            return;
        }
        grid[row][col] = true;
        OpenSitesNumber += 1;

        int coord = xyTo1D(row, col);
        if (coord <= N) {
            unionSet.union(top, coord);
            unionSetNoBot.union(top, coord);
        }

        unionTop(row, col);
        unionBottom(row, col);
        unionLeft(row, col);
        unionRight(row, col);

        if (coord >= N * N + 1 - N && coord <= N * N) {
            unionSet.union(bottom, coord);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("Invalid numbers for row and col");
        }

        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException("Invalid numbers for row and col");
        }

        int coord = xyTo1D(row, col);
        return isOpen(row, col) && unionSetNoBot.connected(top, coord);
    }

    public int numberOfOpenSites() {
        return OpenSitesNumber;
    }

    public boolean percolates() {
        return unionSet.connected(top, bottom);
    }

    private int xyTo1D(int row, int col) {
        return row * N + col + 1;
    }

    private void unionTop(int row, int col) {
        try {
            if (isOpen(row - 1, col)) {
                int coord = xyTo1D(row, col);
                int coord1 = xyTo1D(row - 1, col);
                unionSet.union(coord, coord1);
                unionSetNoBot.union(coord, coord1);
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    private void unionBottom(int row, int col) {
        try {
            if (isOpen(row + 1, col)) {
                int coord = xyTo1D(row, col);
                int coord1 = xyTo1D(row + 1, col);
                unionSet.union(coord, coord1);
                unionSetNoBot.union(coord, coord1);
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    private void unionLeft(int row, int col) {
        try {
            if (isOpen(row, col - 1)) {
                int coord = xyTo1D(row, col);
                int coord1 = xyTo1D(row, col - 1);
                unionSet.union(coord, coord1);
                unionSetNoBot.union(coord, coord1);
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    private void unionRight(int row, int col) {
        try {
            if (isOpen(row, col + 1)) {
                int coord = xyTo1D(row, col);
                int coord1 = xyTo1D(row, col + 1);
                unionSet.union(coord, coord1);
                unionSetNoBot.union(coord, coord1);
            }
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(5);
        test.open(3, 4);
        test.open(2, 4);
        test.open(2, 2);
        test.open(2, 3);
        test.open(0, 2);
        test.open(1, 2);
        test.open(4, 4);
        System.out.println(test.isFull(2, 2));
        System.out.println(test.percolates());
    }
}
