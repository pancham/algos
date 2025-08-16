package lc;

import java.util.Stack;

/**
 * https://leetcode.com/problems/min-stack/
 *
 * https://neetcode.io/solutions/min-stack
 */
public class MinStack155 {
    private Stack<Integer> stack;

    // High level goal is to maintain minimum for each element in the stack.
    // This can be done by maintaining a value in min stack for each value in the stack.
    // However, this will be inefficient since there could be many repetitions.
    // Just maintaining the minimum up until that point serves the same purpose.
    private Stack<Integer> minStack;

    public MinStack155() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }

    public void pop() {
        if (stack.isEmpty()) return;
        int top = stack.pop();
        if (top == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
