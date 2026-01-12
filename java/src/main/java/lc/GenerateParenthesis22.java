package lc;

import java.util.ArrayList;
import java.util.List;

public class GenerateParenthesis22 {
    List<String> res = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        generate(new StringBuffer(), 0, 0, n);
        return res;
    }

    
    private void generate(StringBuffer s, int open, int close, int max) {
        if (s.length() == max * 2) {
            res.add(s.toString());
            return;
        }

        if (open < max) {
            s.append('(');
            generate(s, open + 1, close, max);
            s.deleteCharAt(s.length() - 1);
        }

        if (close < open) {
            s.append(')');
            generate(s, open, close + 1, max);
            s.deleteCharAt(s.length() - 1);
        }
    }

    public static void main(String[] args) {
        GenerateParenthesis22 gp = new GenerateParenthesis22();
        System.out.println(gp.generateParenthesis(3)); // "((()))","(()())","(())()","()(())","()()()"
    }
}
