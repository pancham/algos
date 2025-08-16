package misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinimumHealth {
    public static void main(String[] args) throws IOException {
        int[] damage = readNumbers();

        int armor = 76736;
        MinimumHealth health = new MinimumHealth();
        long min = health.minimumHealth(damage, armor);
        System.out.println(min);
    }

    public static int[] readNumbers() throws IOException {
        Path filePath = Paths.get("./minimumhealth.txt"); // Replace with your file path

        try (Stream<String> lines = Files.lines(filePath)) {
            List<Integer> numbers = lines
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            return numbers.stream().mapToInt(Integer::intValue).toArray();
        }

    }

    public long minimumHealth(int[] damage, int armor) {
        long max = Long.MIN_VALUE;
        // int maxIndex = -1;
        long total = 0;

        for (int i =0; i < damage.length; i++) {
            if (damage[i] > max) {
                max = damage[i];
                // maxIndex = i;
            }
            total += damage[i];
        }

        long min;
        if (max > armor) {
            min = total - armor + 1;
        } else {
            min = total - max + 1;
        }

        return min;
    }
}
