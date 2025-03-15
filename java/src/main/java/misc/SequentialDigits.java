package misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Refer https://algo.monster/liteproblems/1291
 * An integer has sequential digits if and only if each digit in the number is one more than the previous digit.
 *
 * Return a sorted list of all the integers in the range [low, high] inclusive that have sequential digits.
 */
public class SequentialDigits {
    public List<Integer> sequentialDigits(int low, int high) {
        // Initialize the answer list to hold sequential digit numbers
        List<Integer> sequentialNumbers = new ArrayList<>();

        // Start generating numbers from each digit 1 through 8
        // A sequential digit number cannot start with 9 as it would not have a consecutive next digit
        for (int startDigit = 1; startDigit < 9; ++startDigit) {
            // Initialize the sequential number with the current starting digit
            int sequentialNum = startDigit;

            // Append the next digit to the sequential number, starting from startDigit + 1
            for (int nextDigit = startDigit + 1; nextDigit < 10; ++nextDigit) {
                // Append the next digit to the current sequential number
                sequentialNum = sequentialNum * 10 + nextDigit;

                // Check if the newly formed sequential number is within the range [low, high]
                if (sequentialNum >= low && sequentialNum <= high) {
                    // If it is within the range, add it to the answer list
                    sequentialNumbers.add(sequentialNum);
                }
            }
        }

        // Sort the list of sequential numbers
        Collections.sort(sequentialNumbers);

        // Return the list containing all valid sequential digit numbers in the range
        return sequentialNumbers;
    }
}
