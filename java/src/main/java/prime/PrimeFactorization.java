package prime;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactorization {

    public static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        if (n <= 1)
            return factors; // negative, 0, 1 -> empty

        while (n % 2 == 0) {
            factors.add(2);
            n /= 2;
        }
        for (int i = 3; (long) i * i <= n; i += 2) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        if (n > 1)
            factors.add(n); // leftover prime
        return factors;
    }

    public static void main(String[] args) {
        System.out.println(primeFactors(6)); // [2, 3]
        System.out.println(primeFactors(24)); // [2, 2, 2, 3]
        System.out.println(primeFactors(1)); // []
        System.out.println(primeFactors(-10)); // []
        System.out.println(primeFactors(97)); // [97]
    }
}
