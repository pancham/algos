package finalloc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
