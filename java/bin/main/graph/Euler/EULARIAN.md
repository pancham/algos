# Eulerian circuit and path
An Eulerian path (or trail) is a route in a graph that traverses every edge exactly once, starting and ending at different vertices if there are two odd-degree vertices, or forming an Eulerian circuit (starting and ending at the same vertex) if all vertices have even degrees.
<br>An additional requirement is that all vertices with nonzero degree need to belong to a single connected component.

# Eulerian Circuit and Path Conditions

## Undirected Graph

### Eulerian Circuit
Every vertex has an even degree.

### Eulerian Path
Either every vertex has even degree **OR** exactly two vertices have odd degree.

---

## Directed Graph

### Eulerian Circuit
Every vertex has equal indegree and outdegree.

### Eulerian Path
At most one vertex has:
- (outdegree) - (indegree) = 1

AND at most one vertex has:
- (indegree) - (outdegree) = 1

AND all other vertices have equal in and out degrees.

---

## Quick Reference

| Graph Type | Eulerian Circuit | Eulerian Path |
|------------|------------------|---------------|
| **Undirected** | All vertices have even degree | Either every vertex has even degree or exactly two vertices have odd degree  |
| **Directed** | All vertices: in-degree = out-degree | One start vertex (out > in by 1)<br>One end vertex (in > out by 1)<br>Others: in-degree = out-degree |

---

## Additional Notes

### Connectivity Requirement
For both circuit and path to exist, all vertices with non-zero degree must be connected (in the same component).

### Finding Start Vertex
- **Eulerian Circuit**: Start from any vertex
- **Eulerian Path (Undirected)**: Start from a vertex with odd degree
- **Eulerian Path (Directed)**: Start from the vertex where outdegree - indegree = 1

### Key Insight
**Eulerian problems focus on EDGES, not vertices** - you must visit every edge exactly once.