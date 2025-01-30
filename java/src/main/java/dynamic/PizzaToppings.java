package dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * A pizza shop offers n pizzas along with m toppings. A customer plans to spend around x coins. The customer should
 * order exactly one pizza, and may order zero, one or two toppings. Each topping may be ordered only once.
 *
 * Given the lists of prices of available pizzas and toppings, what is the price closest to x of possible orders?
 * Here, a price is said to be closer to x when the difference from x is smaller. Note the customer is allowed to
 * make an order that costs more than x.
 *
 * Function Description
 *
 * Complete the function closestCost in the editor.
 * closestCost has the following parameters:
 *
 * 1. int[] pizzas: an array of integers representing the prices of pizzas
 * 2. int[] toppings: an array of integers representing the prices of toppings
 * 3. int x: the budget in coins
 * Returns
 *
 * int: the price closest to x of possible orders
 *
 * Constraints:
 * Customer's budget: 1 <= x <= 10000
 * Number of pizzas: 1 <= n <= 10
 * Number of toppings: 0 <= m <= 10
 * Price of each pizza: 1 <= pizzas[i] <= 10000
 * Price of each topping: 1 <= toppings[i] <= 10000
 * The total price of all toppings does not exceed 10000.
 *
 * Example 1:
 *
 * Input:  pizzas = [800, 850, 900], toppings = [100, 150], x = 1000
 * Output: 1000
 * Explanation:
 *       The customer can spend exactly 1000 coins (two possible orders).
 *
 * Example 2:
 *
 * Input:  pizzas = [850, 900], toppings = [200, 250], x = 1000
 * Output: 1050
 * Explanation:
 *       The customer may make an order more expensive than 1000 coins.
 *
 * Example 3:
 *
 * Input:  pizzas = [1100, 900], toppings = [200], x = 1000
 * Output: 900
 * Explanation:
 *       The customer should prefer 900 (lower) over 1100 (higher).
 * Example 4:
 *
 * Input:  pizzas = [800, 800, 800, 800], toppings = [100], x = 1000
 * Output: 900
 * Explanation:
 *       The customer may not order 2 same toppings to make it 1000.
 */
public class PizzaToppings {
    public static int closestValue(int[] pizzas, int[] toppings, int max) {
        for (int pizza: pizzas) {
            if (pizza == max) {
                return max;
            }
        }

        // Since there are only up to 10 toppings, max total combinations are 100, which is
        // small.
        List<Integer> allToppings = new ArrayList<>(toppings.length * toppings.length);
        for (int i = 0; i < toppings.length; i++) {
            allToppings.add(toppings[i]);
            for (int j = i + 1; j < toppings.length; j++) {
                allToppings.add(toppings[i] + toppings[j]);
            }
        }

        int minDiff = Integer.MAX_VALUE;
        for (int pizza: pizzas) {
            for (int topping: allToppings) {
                int diff = max - (pizza + topping);
                if (Math.abs(diff) < Math.abs(minDiff)) {
                    minDiff = diff;
                    if (minDiff == 0) {
                        return max;
                    }
                }
            }
        }

        return max - minDiff;
    }


    public static int closestValueWithDP(int[] pizzas, int[] toppings, int max) {
        // If the number of toppings is large and a large or variable number of toppings could be selected,
        // such as there are 100 toppings and upto 10 toppings could be selected, then the `closestValue`
        // method will be inefficient.
        // In such case dynamic programming can be used to find the optimal toppings. The optimal toppings along with
        // pizzas can be used to get the closest value.

        // TODO: This will be done later

        return max;
    }

    public static void main(String[] args) {
        int[] pizzas = {800, 850, 900};
        int[] toppings = {100, 150};
        int max = 1000;
        System.out.println(closestValue(pizzas, toppings, max));

        pizzas = new int[]{850, 900};
        toppings = new int[]{200, 250};
        max = 1000;
        System.out.println(closestValue(pizzas, toppings, max));

        pizzas = new int[]{1100, 900};
        toppings = new int[]{200};
        max = 1000;
        System.out.println(closestValue(pizzas, toppings, max));

        pizzas = new int[]{800, 800, 800, 800};
        toppings = new int[]{100};
        max = 1000;
        System.out.println(closestValue(pizzas, toppings, max));
    }
}
