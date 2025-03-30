package finalloc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Amazon stores its data on different servers at different locations. From time to time, due to several factors, Amazon needs to move its data from one location to another. This challenge involves keeping track of the locations of Amazon's data and reporting them at the end of the year.
 *
 * At the start of the year, Amazon's data was located at n different locations. Over the course of the year, Amazon's data was moved from one server to another m times. Precisely, in the ith operation, the data was moved from movedFrom[i] to movedTo[i]. Find the locations of the data after all m moving operations. Return the locations in ascending order.
 *
 * Note:
 *
 * It is guaranteed that for any movement of data:
 * There is data at movedFrom[i].
 * There is no data at movedTo[i].
 * Function Description
 *
 * Complete the function getFinalLocations in the editor.
 *
 * The function is expected to return an INTEGER_ARRAY. The function accepts the following parameters:
 *
 * INTEGER_ARRAY locations
 * INTEGER_ARRAY movedFrom
 * INTEGER_ARRAY movedTo
 * Example 1:
 * <pre>
 *     <img src="./finalloc.png" />
 * </pre>
 * Input:  locations = [1, 7, 6, 8], movedFrom = [1, 7, 2], movedTo = [2, 9, 5]
 * Output: [5, 6, 8, 9]
 * Explanation:
 *
 *
 *
 * Data begins at locations listed in locations. Over the course of the year, the data was moved three times. Data was first moved from movedFrom[0] to movedTo[0], from 1 to 2. Next, data was moved from 7 to 9, and finally, from location 2 to 5.
 *
 * In the end, the locations where data is present are [5, 6, 8, 9] in ascending order.
 */
public class GetFinalLocation {
    // https://www.fastprep.io/problems/amazon-get-final-locations
    public static void main(String[] args) {
        int[] locations = new int[] {1, 7, 6, 8};
        int[] movedFrom = new int[] {1, 7, 2};
        int[] movedTo = new int[] {2, 9, 5};

        int[] finalLocations = findMoves(locations, movedFrom, movedTo);
        System.out.println(Arrays.toString(finalLocations));
    }

    public static int[] findMoves(int[] locations, int[] movedFrom, int[] movedTo) {
        Map<Integer, Integer> movedLocations = new HashMap<>();

        for (int i = 0; i < movedFrom.length; i++) {
            movedLocations.put(movedFrom[i], movedTo[i]);
        }

        int[] finalLocations = new int[locations.length];
        for (int i = 0; i < locations.length; i++) {
            int location = locations[i];

            int movedLocation = location;
            Integer value = location;
            do {
                value = movedLocations.get(value);
                if (value != null) {
                    movedLocation = value;
                }
            } while (value != null);
            finalLocations[i] = movedLocation;
        }

        Arrays.sort(finalLocations);
        return finalLocations;
    }
}
