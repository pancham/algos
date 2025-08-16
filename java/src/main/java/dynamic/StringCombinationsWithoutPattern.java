package dynamic;

public class StringCombinationsWithoutPattern {
    public void main(String[] args) {

    }

    // Declare as member variable to avoid putting the value on stack in recursive calls
    // private String patternToExclude;
    private int n;


    StringCombinationsWithoutPattern(int n) {
        this.n = n;
    //    this.patternToExclude = patternToExclude;
    }

    int countStrings(StringBuffer buf) {
        // check if goal reached
        if (buf.length() >= n) {
            return 1;
        }

        int count = 0;
        char[] validChars = new char[]{'A', 'B', 'C'};
        for (int i = 0; i < validChars.length; i++) {
            char c = validChars[i];
            // apply constraint
            if (c == 'C') {
                if (buf.length() >= 3
                        && buf.charAt(i-1) == 'B'
                        && buf.charAt(i-2) == 'A') {
                    continue;
                }
            }
            // modify state and make choice
            buf.append(c);
            count += countStrings(buf);

            // restore state
            buf.deleteCharAt(buf.length() - 1);
        }
        return count;
    }

}
