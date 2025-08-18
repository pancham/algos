package test;
import java.math.BigInteger;

public class DrawingEdge {
    static final int MOD = 1_000_000_007;

    // Function for modular exponentiation (x^y % p)
    static long powerMod2(long base, long exp, int mod) {
        long result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }
        return result;
    }

    static long powerMod(long base, long exp) {
        BigInteger b = BigInteger.valueOf(base);
        BigInteger e = BigInteger.valueOf(exp);
        BigInteger m = BigInteger.valueOf(MOD);

        BigInteger result = b.modPow(e, m);

        return result.longValue();
    }

    public static int drawingEdge(int n) {
        long edges = (long) n * (n - 1) / 2; // Compute n(n-1)/2
        return (int) powerMod(2, edges); // Compute 2^(n(n-1)/2) % MOD
    }

    public static void main(String[] args) {
        System.out.println(drawingEdge(5));
    }
}
