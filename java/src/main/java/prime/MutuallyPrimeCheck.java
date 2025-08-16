package prime;

import java.util.Scanner;

// Two numbers are mutually prime if their GCD is 1
public class MutuallyPrimeCheck {
    // Function to compute GCD using Euclidean algorithm
    // The GCD of two numbers a and b is the same as the GCD of b and a % b.
    // gcd(a, b) = gcd(b, a % b)
    // Example:
    // gcd(48, 18)
    // = gcd(18, 48 % 18) → gcd(18, 12)
    // = gcd(12, 18 % 12) → gcd(12, 6)
    // = gcd(6, 12 % 6)  → gcd(6, 0)
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to check if two numbers are mutually prime
    public static boolean areMutuallyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter two numbers: ");
        int a = sc.nextInt();
        int b = sc.nextInt();

        if (areMutuallyPrime(a, b)) {
            System.out.println(a + " and " + b + " are mutually prime.");
        } else {
            System.out.println(a + " and " + b + " are NOT mutually prime.");
        }

        sc.close();
    }
}
