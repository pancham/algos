package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiktokInfluencersSolution {

    public static void main(String[] args) {
        // Since it is a tree, Diameter.treeLongestPathElements can be called to get the influencers on the list
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Diameter diameter = new Diameter();
        List<Integer> diameterNodes = diameter.treeLongestPathElements(graph);

        System.out.println("Diameter nodes " + diameterNodes);
    }

}
