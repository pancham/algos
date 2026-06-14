# Hamiltonian Circuits and Paths

A **Hamiltonian path** is a path in a graph that visits every vertex exactly once. 
A **Hamiltonian circuit** (or cycle) is a closed loop that visits every vertex exactly once and returns to the starting vertex.

---

## Comparison with Eulerian Circuits

| Feature | Eulerian Circuit/Path | Hamiltonian Circuit/Path |
| :--- | :--- | :--- |
| **Primary Focus** | **Edges** (must visit every edge exactly once) | **Vertices** (must visit every vertex exactly once) |
| **Edge Visited Counts** | Must traverse all edges in the graph | Many edges may be left unvisited (exactly V edges are visited in a Hamiltonian circuit) |
| **Vertex Visited Counts** | Vertices can be visited multiple times | Vertices must be visited exactly once (except the start/end vertex in a circuit) |
| **Existence Criteria** | Simple necessary and sufficient conditions based on vertex degrees (e.g., all vertices have even degree) | No simple necessary/sufficient condition exists. Only sufficient conditions exist (e.g., Dirac's or Ore's theorems based on minimum degrees) |
| **Computational Complexity** | Solvable in linear time: **O(V + E)** (e.g., using Hierholzer's algorithm) | **NP-complete** (exponential time in the worst case) |

---

## Algorithms for Hamiltonian Paths and Circuits

### 1. Backtracking (Exact, for small graphs: V <= 15)
A simple depth-first search (DFS) with backtracking.
* **Approach**: 
  * Try to build a path vertex-by-vertex. 
  * If a choice leads to a dead-end before visiting all vertices, undo the last choice (backtrack) and try other neighbors.
* **Complexity**: **O(V!)** in the worst case.

### 2. Dynamic Programming with Bitmasking (Exact, for medium-small graphs: V <= 22)
Often referred to as the **Held-Karp algorithm**.
* **Approach**:
  * Use a bitmask to represent the set of visited vertices.
  * Define state `DP[mask][u]` as whether there exists a valid path visiting the subset of vertices represented by `mask`, ending at vertex `u`.
  * Transition by checking if any neighbor `v` was visited before visiting `u` in `mask`.
* **Complexity**: **O(V^2 * 2^V)**, which is significantly faster than O(V!) but still exponential.

### 3. Palmer's Algorithm / Bondy-Chvátal Closure (Sufficient Conditions)
If a graph is highly connected, we can find a Hamiltonian circuit in polynomial time.
* **Theorems**:
  * **Dirac's Theorem**: If every vertex in a graph of size V >= 3 has degree >= V/2, the graph is Hamiltonian.
  * **Ore's Theorem**: If for every pair of non-adjacent vertices u and v, `deg(u) + deg(v) >= V`, the graph is Hamiltonian.
* **Approach**:
  * Construct the closure of the graph by continually adding edges between non-adjacent vertices where `deg(u) + deg(v) >= V`.
  * Once the closure is complete, find the cycle in **O(V^3)** time.

### 4. Approximation Algorithms (For Weighted/Metric Graphs)
Used in real-world Traveling Salesperson Problem (TSP) applications.
* **Christofides-Serdyukov Algorithm**: Guarantees a Hamiltonian circuit that is at most **1.5 times** the optimal circuit length in metric graphs.
* **2-Opt / 3-Opt Heuristics**: Local search algorithms that start with a random cycle and swap edges to eliminate crossings and reduce overall length.
