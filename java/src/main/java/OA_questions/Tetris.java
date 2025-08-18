public class Tetris {
    public class Solution {

        // figure is 3x3 matrix
        public int solution (int[][] field, int[][] figure) {
            int n = field.length, m = field[0].length;
            if (n < 3 || m < 3) return -1;

            for (int c = 0; c <= m - 3; ++c) {
                // drop from this column as far down as possible
                int r = 0;
                while ((r <= n-3) && canGoDown(field, figure, r, c)) {
                    r++;
                } r--;

                if (r >= 0 && rowFilled(field, figure, r, c)) return c;
            }

            return -1;
        }

        private boolean canGoDown (int[][] field, int[][] figure, int fieldR, int fieldC) {
            for (int figRow = 0; figRow < 3; ++figRow) {
                for (int figCol = 0; figCol < 3; ++figCol) {
                    if (figure[figRow][figCol] == 1 && 
                        field[fieldR + figRow][fieldC + figCol] == 1) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean rowFilled (int[][] field, int[][] figure, int startRow, int fieldCol) {
            for (int r = startRow; r < startRow + 3; ++r) {
                boolean noZeros = true;
                for (int c = 0; c < field[0].length; ++c) {
                    if (field[r][c] == 0) {
                        if ((c >= fieldCol && c < fieldCol + 3)  && (figure[r - startRow][c - fieldCol] == 1)) {
                            continue;
                        }
                        noZeros = false; break;
                    } 
                }
                if (noZeros) return true;
            }
            return false;
        }
    }
}


