# JavaAlgorithms
These are some Java code I wrote throughout my first degree in Computer Science.
There is some third party code properly credited and with its associated license.

## Files Descriptions

### Code I wrote
* [Directed MultiGraph](DirectedMultiGraph.java) - Adjacency-list non-weighted directed multi-graphs (data structure)
* [Directed Weighted Graph](DirectedWeightedGraph.java) - Adjacency-list n-weighted directed graphs (data structure)
* [Dijkstra](DirectedWeightedGraph.java#L91) - Adjacency-list Dijkstra's algorithm implementation (SSC non-negative weights only)
* [Prim](DirectedWeightedGraph.java#L173) - Adjacency-list Prim's algorithm implementation (MST)
* [Bellman-Ford](DirectedWeightedGraph.java#L243) - Adjacency-list Bellman-ford (SSC)
* [Floyd Warshall](FloydWarshall.java) - Adjacency-matrix Floyd Warshall (APSP)
* [Johnson](Johnson.java) - Johnson's Algorithm (sparse graph APSP)
* [Insert Sort](InsertSort.java) - An Insertion Sort implementation (sorting)
* [DFS and Topological Sorting](DFS.java#L18) - Adjacency-list DFS with TopSort implementation (graph traversal and sorting)
* [Kosaraju-Sharir](DFS.java#L48) - Adjacency-list Kosaraju-Sharir implementation (SCC)
* [BFS non-directed SCC](bfs_scc.java) - Adjacency-list BFS implementation to identify SCC in a non-directed graph (graph traversal and SCC non-directed only)
* [BFS non-weighted SSC](bfs_shortest_path.java) - Adjacency-list BFS for shortest path in a non-weighted graph (SSC non-weighted)
* [LIS](LIS.java) - A Longest Increasing Sequence algorithm implmentation (substring)
* [Collections Count If](collections_count_if.java#L46) - Counts elements for which predicate p returns true (collection utility)


### Third party code
* [Couple](Couple.java) - A AOSP class to handle 2-tuples
* [Segment Tree](SegmentTree.java) - A Segment Tree data structure implementation derived from Segewick and Wayne's Algorithms, 4th ed. book
* [ValueThenKeyComparator](ValueThenKeyComparator.java) - A Comparator that sorts Map.Entry objects with Comparable keys and values, first by value, then by key
* [FastScanner](FastScanner.java) - Fast Input from STDIN in Java
* [FastPrint](FastPrint.java) - Fast Output from STDOUT in Java

### Example code
* [Activity Minimum Gap Schedulling](minimum_gap_schedulling.java) - Activity Minimum Gap Schedulling implementation (sorting)
* [Knapsack](knapsack.java) - Knapsack approximation solution using conditional binary search (search and optimization)
