package prime;

import java.util.ArrayList;
import java.util.List;

public class Prime {

    public static void main(String[] args) {
        List<Integer> primes = generateNPrimes(26);
        System.out.println(primes);
    }

    /**
     * Generates n prime numbers
     */
    public static List<Integer> generateNPrimes(int n) {
        List<Integer> primes = new ArrayList<>(n);
        int num = 2; // Start checking from 2
        while (primes.size() < n) {
            // Refer to the explanation of isPrime for the logic
            if (isPrime(num)) {
                primes.add(num);
            }
            num++;
        }
        return primes;
    }

    /**
     * The Divisor Pair Property:
     *
     *      If a number n is not prime, it means it has at least one divisor other than 1 and itself.
     *      If n has a divisor d, then it also has a corresponding divisor n/d.
     *      These two divisors, d and n/d, form a "divisor pair."
     *
     * If d is greater than sqrt(n):
     *
     *      Then n/d must be less than sqrt(n).
     *      In other words, if you find a divisor greater than the square root, there must be a corresponding divisor
     *      smaller than the square root.
     *
     * Mathematical Proof:
     *
     *      Given: We start with the assumption that d > sqrt(n).
     *      d > sqrt(n)
     *      => d/n > sqrt(n)/n
     *      => n/d < n/sqrt(n)
     *      => n/d < sqrt(n)
     *
     */
    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
