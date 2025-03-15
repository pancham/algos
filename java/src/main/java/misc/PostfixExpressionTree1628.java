package misc;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given the postfix tokens of an arithmetic expression, build and return the binary expression tree that represents this expression.
 *
 * Postfix notation is a notation for writing arithmetic expressions in which the operands (numbers) appear before their operators. For example, the postfix tokens of the expression 4*(5-(7+2)) are represented in the array postfix = ["4","5","7","2","+","-","*"].
 *
 * The class Node is an interface you should use to implement the binary expression tree. The returned tree will be tested using the evaluate function, which is supposed to evaluate the tree's value. You should not remove the Node class; however, you can modify it as you wish, and you can define other classes to implement it if needed.
 *
 * A binary expression tree is a kind of binary tree used to represent arithmetic expressions. Each node of a binary expression tree has either zero or two children. Leaf nodes (nodes with 0 children) correspond to operands (numbers), and internal nodes (nodes with two children) correspond to the operators '+' (addition), '-' (subtraction), '*' (multiplication), and '/' (division).
 *
 * It's guaranteed that no subtree will yield a value that exceeds 109 in absolute value, and all the operations are valid (i.e., no division by zero).
 *
 * Follow up: Could you design the expression tree such that it is more modular? For example, is your design able to support additional operators without making changes to your existing evaluate implementation?
 * <p>
 * Input: s = ["3","4","+","2","*","7","/"]
 * Output: 2
 *</p>
 * <p>
 * Input: s = ["4","5","2","7","+","-","*"]
 * Output: -16
 * </p>
 */
public class PostfixExpressionTree1628 {
    public static void main(String[] args) {
        String[] postfix = new String[] {
//                "3","4","+","2","*","7","/"     // returns 2
                "4","5","2","7","+","-","*"    // returns -16
        };

        TreeBuilder treeBuilder = new TreeBuilder();
        Node expressionTree = treeBuilder.buildTree(postfix);
        int result = expressionTree.evaluate();
        System.out.println(result);
    }
}

abstract class Node {
    // Field for the value
    protected String value;
    // Left and right children
    protected Node left;
    protected Node right;

    // Abstract method to evaluate the tree
    public abstract int evaluate();
};

class MyNode extends Node {
    // Constructor to create a new node with a given value
    public MyNode(String value) {
        this.value = value;
    }

    // Method to evaluate the node
    public int evaluate() {
        // If the node is a number, parse and return its integer value
        if (isNumeric()) {
            return Integer.parseInt(value);
        }
        // Otherwise, evaluate the left and right subtrees
        int leftValue = left.evaluate();
        int rightValue = right.evaluate();
        // Perform the operation defined by the current node's value
        switch (value) {
            case "+": return leftValue + rightValue;
            case "-": return leftValue - rightValue;
            case "*": return leftValue * rightValue;
            case "/": return leftValue / rightValue;
            default: return 0; // If the operation is unknown
        }
    }

    // Helper method to determine if the node's value is numeric
    public boolean isNumeric() {
        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; // Not numeric if any character is not a digit
            }
        }
        return true; // All characters are digits, hence numeric
    }
}

class TreeBuilder {
    // Method to build the expression tree from a postfix expression
    Node buildTree(String[] postfix) {
        Deque<MyNode> stack = new ArrayDeque<>();
        for (String token : postfix) {
            MyNode currentNode = new MyNode(token);
            // If the token is an operator, pop two nodes and make them children
            if (!currentNode.isNumeric()) {
                currentNode.right = stack.pop();
                currentNode.left = stack.pop();
            }
            // Push the current node onto the stack
            stack.push(currentNode);
        }
        // The last node on the stack is the root of the expression tree
        return stack.peek();
    }
};
