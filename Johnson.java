// You have a graph G (original graph), with edge weight values v(u->w)
// You must call this function with a helper graph with a special node
// indexed 0 and zero-weight edges 0->w for all vertices w
// Time complexity: O(N^2 * lg N + N*#Edges)
// Returns APSP indexed as expected in the original graph (i.e.,
// shifting everything -1 to exclude the special node of the helper graph)
private static int[][]
int_johnson (DirectedWeightedGraph<Integer> hg) throws Exception
{
    // Finish the helper graph by running Bellman-Ford on it
    int[] h = hg.int_bellman_ford(0);

    // Generate the tweaked graph
    for (int u = 1; u < hg.n_nodes; ++u) {
        hg.edges(u).forEach((w) -> {
            int edge_cost = (int) hg.weight(u, w).get(0);
            // Update edge's cost
            hg.link(u, w, edge_cost + h[u] - h[w]);
        }
    }

    int [][] D = new int[hg.n_nodes-1][hg.n_nodes-1];

    // Dijkstra on the tweaked graph
    for (int u = 0; u < g.n_nodes-1; ++u) {
        for (int w = 0; w < g.n_nodes-1; ++w) {
            int[] delta = hg.int_dijkstra(u+1);
            D[u][w] = delta[w+1] + h[w+1] - h[u+1];
        }
    }

    // Johnson drops mic.
    return D;
}
