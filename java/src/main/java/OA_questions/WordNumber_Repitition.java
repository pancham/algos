

import java.util.Stack;
/* 
 * Problem: given a string formated with a word enclosed by (), 
 * followed by a number enclose by {}, return the word repeated that many times
 * 
 * !!!!!!!!!!!!!!!!!!!!!!! NOTE : THERE IS NESTING!
 * examples: 
 * (ab){3} -> ababab
 * (ab){3}(cd){2} -> abababcdcd
 * 
 * Nested example: (ef((ab){2}cd){2}){2}
 * (ab){2} -> abab
 * (ab{2}cd){2} -> (ababcd){2} -> ababcdababcd
 * (ef((ab){2}cd){2}){2} -> (efababcdababcd){2} -> efababcdababcdefababcdababcd
 * 
*/

public class WordNumber_Repitition{ 
    public String wordNumber(String s) {
        int n = s.length();
        Stack<StringBuilder> stack = new Stack<>();
        StringBuilder cur = new StringBuilder();
        
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push(cur);
                cur = new StringBuilder();
            }
            else if (ch == ')') {
                i += 2; // skip the '{' before the number
                int num = 0;
                while (i < n && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                } // now s.charAt(i) == '}'

                String chunk = cur.toString();
                StringBuilder prev = stack.isEmpty() ? new StringBuilder() : stack.pop();
                for (int k = 0; k < num; k++) prev.append(chunk);
                cur = prev;
            } 
            else cur.append(ch);
        }
        return cur.toString();
    }

    public static void main(String[] args) {
        WordNumber_Repitition w = new WordNumber_Repitition();
        System.out.println(w.wordNumber("(ab){3}(cd){2}"));
        System.out.println(w.wordNumber("((ab){2}cd){2}"));
        System.out.println(w.wordNumber("(ef((ab){2}cd){2}){2}"));
    }

}

