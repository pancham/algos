# algos

# Euler path - visits every edge of the graph only once

# Hamilton path - visits every vertex of the path only once

# cartesian tree

# pigeonhole principle

# cartesian tree equivalence

Applications in Engineering
Network Design: Hamiltonian cycles optimize routing and minimize network costs (e.g., fiber optic networks).
Circuit Design: Euler paths reduce traces on circuit boards (e.g., efficient circuit layouts).
DNA Sequencing: Hamiltonian paths help reconstruct DNA from overlapping fragments.
Robotics: Euler paths plan routes to cover areas without retracing (e.g., robot vacuums).
Logistics: Hamiltonian cycles optimize delivery routes and reduce travel costs.

LeetCode Problems Using Eulerian Path

LeetCode 332 - Reconstruct Itinerary

Given flight tickets, find the itinerary
Uses directed Eulerian path


LeetCode 753 - Cracking the Safe

Uses De Bruijn sequence (Eulerian circuit in directed graph)


LeetCode 2097 - Valid Arrangement of Pairs

Find valid arrangement using Eulerian path in directed graph

Tarjan's algorithm is a graph algorithm, most famously used to find Strongly Connected Components (SCCs) in directed graphs efficiently (linear time, O(|V|+|E|)) by performing a single Depth-First Search (DFS). It identifies groups of vertices where every vertex can reach every other vertex within that group, using discovery times, low-link values, and a stack to track nodes in the current path, popping them off to form an SCC when a root node is identified (its discovery time equals its low-link value). 