package graph;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    // Using a Map for children allows supporting any character, 
    // though arrays are faster for fixed sets like a-z.
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie */
    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            // computeIfAbsent creates a new node only if it doesn't exist
            current = current.children.computeIfAbsent(ch, k -> new TrieNode());
        }
        current.isEndOfWord = true;
    }

    /** Returns true if the word is in the trie */
    public boolean search(String word) {
        TrieNode node = getLastNode(word);
        return node != null && node.isEndOfWord;
    }

    /** Returns true if there is any word in the trie that starts with the given prefix */
    public boolean startsWith(String prefix) {
        return getLastNode(prefix) != null;
    }

    /** Helper method to navigate to the end of a given string path */
    private TrieNode getLastNode(String s) {
        TrieNode current = root;
        for (char ch : s.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) return null;
        }
        return current;
    }

    // --- Main Method for Testing ---
    public static void main(String[] args) {
        Trie trie = new Trie();

        System.out.println("--- Testing Trie Operations ---");

        // Test Case 1: Standard Insertion and Search
        trie.insert("apple");
        printTest("Search 'apple'", true, trie.search("apple"));
        printTest("Search 'app'", false, trie.search("app")); // "app" is just a prefix

        // Test Case 2: Prefix Matching
        printTest("StartsWith 'app'", true, trie.startsWith("app"));

        // Test Case 3: Overlapping Words
        trie.insert("app");
        printTest("Search 'app' (after insert)", true, trie.search("app"));
        
        // Test Case 4: Non-existent Words
        printTest("Search 'orange'", false, trie.search("orange"));
        printTest("StartsWith 'ora'", false, trie.startsWith("ora"));

        // Test Case 5: Complex overlaps
        trie.insert("bat");
        trie.insert("ball");
        printTest("Search 'bat'", true, trie.search("bat"));
        printTest("Search 'ball'", true, trie.search("ball"));
        printTest("StartsWith 'ba'", true, trie.startsWith("ba"));
    }

    private static void printTest(String description, boolean expected, boolean actual) {
        String result = (expected == actual) ? "PASS" : "FAIL";
        System.out.printf("%-30s | Expected: %-5b | Actual: %-5b | Result: %s%n",
                description, expected, actual, result);
    }
}

