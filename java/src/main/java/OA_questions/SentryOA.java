package OA_questions;
import java.util.*;

public class SentryOA {

    // if multiple words on a line, space evenly with extra space going on left
    // if single word on line, align in center, and exptra space goes to right
    // Hyphenated words, like 'sister-in-law', should ideally be on one line
        // but if they don't fit, break the word 
    public List<String> harderTextJustification(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();

        Deque<String> wordList = new ArrayDeque<>();
        for (String word : words) wordList.add(word);

        while (!wordList.isEmpty()) {
            List<String> lineWords = new ArrayList<>();

            int letters = 0;
            while (!wordList.isEmpty() && (letters + lineWords.size() + wordList.getFirst().length() <= maxWidth)) {
                String word = wordList.removeFirst(); 
                lineWords.add(word);
                letters += word.length();
            }

            // account for hyphenated word
            if (!wordList.isEmpty() && wordList.getFirst().contains("-")) {
                String hyphenWord = wordList.removeFirst();
                String[] hypenatedWords = hyphenWord.split("(?<=-)");

                // split hyphenated word, make left split as big as possible
                int splitIdx = 0;
                StringBuilder leftWord = new StringBuilder();
                for (String word : hypenatedWords) {
                    if (letters + lineWords.size() + word.length() <= maxWidth) {
                        leftWord.append(word);
                        letters += word.length();
                        splitIdx += word.length();
                    }
                    else break;
                }
                if (!leftWord.isEmpty()) {
                    lineWords.add(leftWord.toString()); 

                    // remaining hyphenated word should go on next line
                    if (splitIdx != hyphenWord.length()) {
                        String rightWord = hyphenWord.substring(splitIdx, hyphenWord.length());
                        wordList.addFirst(rightWord);
                    }
                }
                else wordList.addFirst(hyphenWord);
            }

            
            int totalSpaces = maxWidth - letters;
            StringBuilder line = new StringBuilder();
            int wordsWithSpaces = lineWords.size() - 1;

            // one word on line - align in center, with extra space on right
            if (wordsWithSpaces == 0) { // assert (wordsWithSpaces == lineWords.size()-1)
                int leftSpacing = totalSpaces/2;
                int rightSpacing = totalSpaces - leftSpacing;
                line.append(" ".repeat(leftSpacing));
                line.append(lineWords.get(0));
                line.append(" ".repeat(rightSpacing));
            }
            else { // multipe words on lines. No spacing before first word, and no spacing after last word
                int normalSpacing = totalSpaces / wordsWithSpaces;
                int extraSpacing = totalSpaces % wordsWithSpaces;
                for (int j = 0; j < wordsWithSpaces; j++) {
                    int spacing = normalSpacing;
                    if (extraSpacing > 0) {
                        ++spacing; --extraSpacing;
                    }
                    line.append(lineWords.get(j)).append(" ".repeat(spacing));
                }
                line.append(lineWords.get(lineWords.size()-1));
            }
            res.add(line.toString());
        }

        return res;
    }
    
}
