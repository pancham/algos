import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class SentryOATest {

    @Test
    void multipleWords_leftBiasExtra() {
        SentryOA s = new SentryOA();
        // letters=3, spaces=5, gaps=2 -> even=2, extra=1 (extra to LEFT gap)
        List<String> out = s.harderTextJustification(new String[]{"a", "b", "c"}, 8);
        assertEquals(List.of("a   b  c"), out);
        assertEquals(8, out.get(0).length());
    }

    @Test
    void singleWord_center_extraToRight() {
        SentryOA s = new SentryOA();
        // "hello" len=5, width=8 -> spaces=3 -> left=1, right=2
        List<String> out = s.harderTextJustification(new String[]{"hello"}, 8);
        assertEquals(List.of(" hello  "), out);
        assertEquals(8, out.get(0).length());
    }

    @Test
    void twoWords_wrap_when_width_only_allows_single_gap_space() {
        SentryOA s = new SentryOA();
        // width=4 cannot hold "ab" + at least one space + "cd" (2+1+2=5>4) -> wrap
        List<String> out = s.harderTextJustification(new String[]{"ab", "cd"}, 4);
        assertEquals(List.of(" ab ", " cd "), out); // centered single words on each line
        assertEquals(4, out.get(0).length());
        assertEquals(4, out.get(1).length());
    }

    @Test
    void twoWords_single_line_when_width_allows_one_gap() {
        SentryOA s = new SentryOA();
        // width=5 can hold "ab cd" -> exactly one space between words
        List<String> out = s.harderTextJustification(new String[]{"ab", "cd"}, 5);
        assertEquals(List.of("ab cd"), out);
        assertEquals(5, out.get(0).length());
    }

    @Test
    void hyphenSplit_midLine_prefixTaken_remainderNextLine() {
        SentryOA s = new SentryOA();
        // width=12; line 1: "foo" + one space + "sister-" -> "foo__sister-" (3+2+7=12)
        // line 2: "in-law" + 3 spaces + "bar"
        List<String> out = s.harderTextJustification(
                new String[]{"foo", "sister-in-law", "bar"}, 12);

        assertEquals(2, out.size());
        assertEquals("foo  sister-", out.get(0));
        assertEquals("in-law   bar", out.get(1));
        assertEquals(12, out.get(0).length());
        assertEquals(12, out.get(1).length());
    }

    @Test
    void hyphenSplit_whenWordStartsLine() {
        SentryOA s = new SentryOA();
        // width=8; first line takes "sister-" (7) and centers (extra 1 -> right)
        // second line: "in-law x" with one space
        List<String> out = s.harderTextJustification(
                new String[]{"sister-in-law", "x"}, 8);

        assertEquals(2, out.size());
        assertEquals("sister- ", out.get(0));
        assertEquals("in-law x", out.get(1));
        assertEquals(8, out.get(0).length());
        assertEquals(8, out.get(1).length());
    }

    @Test
    void multipleLines_mixedContent_strict() {
        SentryOA s = new SentryOA();
        String[] words = {"This","is","a","demo","with","mother-in-law","cases"};
        int W = 14;

        List<String> out = s.harderTextJustification(words, W);

        assertEquals(List.of(
            "This is a demo",
            "with   mother-",
            "in-law   cases"
        ), out, "lines differ");

        for (String line : out) assertEquals(W, line.length(), "bad width: [" + line + "]");
    }

    @Test
    void multiHyphenatedWordSplitsAcrossSeveralLines() {
        SentryOA s = new SentryOA();
        String[] words = {"x-y-z"};
        int W = 3;
        List<String> out = s.harderTextJustification(words, W);
        assertEquals(List.of("x- ", "y-z"), out);
    }

}
