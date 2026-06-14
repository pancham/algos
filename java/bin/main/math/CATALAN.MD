# Catalan Numbers

In mathematics, the **Catalan numbers** form a sequence of natural numbers that occur in various counting problems, often involving recursive structures. They are named after the Belgian mathematician [Eugène Charles Catalan](www.britannica.com).

## 1. Mathematical Formula
The $n$-th Catalan number, $C_n$, is defined using the following formula:

$$C_n = \frac{1}{n+1} \binom{2n}{n} = \frac{(2n)!}{(n+1)!n!}$$

### Recurrence Relation
Catalan numbers can also be defined by the recursive property:
$$C_0 = 1 \quad \text{and} \quad C_{n+1} = \sum_{i=0}^{n} C_i C_{n-i} \quad \text{for } n \ge 0$$

---

## 2. Common Applications
The sequence is famous because it provides the solution to many combinatorial problems:

*   **Valid Parentheses**: The number of ways to arrange $n$ pairs of balanced parentheses (e.g., `(())`).
*   **Binary Trees**: The number of distinct [binary trees](en.wikipedia.org) that can be formed with $n$ nodes.
*   **Polygon Triangulation**: The number of ways to divide a convex polygon with $n+2$ sides into triangles.
*   **Dyck Paths**: The number of paths from $(0,0)$ to $(2n,0)$ using steps $(1,1)$ and $(1,-1)$ that never fall below the x-axis.

---

## 3. Sequence Table
The first few values for $n = 0$ to $9$ are:

