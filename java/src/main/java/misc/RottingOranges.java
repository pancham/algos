package misc;

import java.util.LinkedList;
import java.util.Queue;

public class RottingOranges {
    public static void main(String[] args) {
        int[][] grid = new int[][] {
//                {2,1,1}, {1,1,0}, {0,1,1} // 4
//                {2,1,1},{0,1,1},{1,0,1}   // -1
//                {2,1,1},{1,1,1},{0,1,2}     // 2
        };

        RottingOranges r = new RottingOranges();
        int mins = r.orangesRotting(grid);
        System.out.println("mins: " + mins);
    }

    class Cell {
        int row;
        int col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public int orangesRotting(int[][] grid) {
        Queue<Cell> q = new LinkedList<>();

        for (int i = 0;  i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    q.add(new Cell(i, j));
                }
            }
        }

        // all are rotten
        if (q.size() == grid.length * grid[0].length) {
            return 0;
        }

        int mins = 0;
        do {
            // process current rotten oranges and collect the next ones to be processed
            q = process(grid, q);

            if (!q.isEmpty()) {
                mins++;
            }
        } while (!q.isEmpty());

        // if fresh orange is left, it means that it is not possible
        for (int i = 0;  i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }
        return mins;
    }

    Queue<Cell> process(int[][] grid, Queue<Cell> q) {
        Queue<Cell> nextBatch = new LinkedList<>();
        while (!q.isEmpty()) {
            Cell c = q.poll();

            if (c.col < grid[0].length - 1) { // right
                int row = c.row;
                int col = c.col + 1;
                if (grid[row][col] == 1) {
                    grid[row][col] = 2;
                    nextBatch.add(new Cell(row, col));
                }
            }

            if (c.row < grid.length - 1) { // bottom
                int row = c.row + 1;
                int col = c.col;
                if (grid[row][col] == 1) {
                    grid[row][col] = 2;
                    nextBatch.add(new Cell(row, col));
                }
            }

            if (c.col > 0) { // left
                int row = c.row;
                int col = c.col - 1;
                if (grid[row][col] == 1) {
                    grid[row][col] = 2;
                    nextBatch.add(new Cell(row, col));
                }
            }

            if (c.row > 0) { // top
                int row = c.row - 1;
                int col = c.col;
                if (grid[row][col] == 1) {
                    grid[row][col] = 2;
                    nextBatch.add(new Cell(row, col));
                }
            }
        }

        return nextBatch;
    }

}
