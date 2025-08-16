package lc;

public class ValidSudoku36 {
    public static void main(String[] args) {
//        char[][] board = new char[][]
//                {{'8','3','.','.','7','.','.','.','.'}
//                ,{'6','.','.','1','9','5','.','.','.'}
//                ,{'.','9','8','.','.','.','.','6','.'}
//                ,{'8','.','.','.','6','.','.','.','3'}
//                ,{'4','.','.','8','.','3','.','.','1'}
//                ,{'7','.','.','.','2','.','.','.','6'}
//                ,{'.','6','.','.','.','.','2','8','.'}
//                ,{'.','.','.','4','1','9','.','.','5'}
//                ,{'.','.','.','.','8','.','.','7','9'}};

        char[][] board = new char[][]
                {{'5','3','.','.','7','.','.','.','.'}
                ,{'6','.','.','1','9','5','.','.','.'}
                ,{'.','9','8','.','.','.','.','6','.'}
                ,{'8','.','.','.','6','.','.','.','3'}
                ,{'4','.','.','8','.','3','.','.','1'}
                ,{'7','.','.','.','2','.','.','.','6'}
                ,{'.','6','.','.','.','.','2','8','.'}
                ,{'.','.','.','4','1','9','.','.','5'}
                ,{'.','.','.','.','8','.','.','7','9'}};
        ValidSudoku36 v = new ValidSudoku36();
        System.out.println("valid sudoku = " + v.isValidSudoku(board));
    }

    public boolean isValidSudoku(char[][] board) {
        int[] r = new int[9];
        int[] c = new int[9];
        int[] sb = new int[9];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char ch = board[i][j];

                if (ch == '.')
                    continue;

                int chVal = ch - '0';
                int val = 1 << chVal; // same as 2 ^ chVal
                if ((r[i] & val) == val) {
                    return false;
                }
                r[i] |= val;

                if ((c[j] & val) == val) {
                    return false;
                }
                c[j] |= val;

                int sbi = sbIndex(i, j);
                if ((sb[sbi] & val) == val) {
                    return false;
                }
                sb[sbi] |= val;
            }

        }

        return true;
    }

    int sbIndex(int row, int col) {
        int ro =  ((int)(row/3)) * 3;
        int co = (int) col/3;

        return ro + co;
    }
}
