/* Input */
// Information about the group
int n_elements = stdin.nextInt(),
    start = stdin.nextInt(),
    budget = stdin.nextInt(),
// Information about the network
    n_nodes = stdin.nextInt(),
    n_edges = stdin.nextInt(),
    NO_EDGE = 1000000000; // 1e9 to avoid overflow
// Graph as a adjacency-matrix
int[][] adj = new int[n_nodes][n_nodes];
for (int i = 0; i < n_nodes; ++i)
{
        Arrays.fill(adj[i], NO_EDGE);
        adj[i][i] = 0;
}
for (int i = 0; i < n_edges; ++i)
{
        int source = stdin.nextInt()-1,
                dest = stdin.nextInt()-1,
                spots = stdin.nextInt(),
                cost = stdin.nextInt();

        if (spots >= n_elements
                && cost <= budget) // prevents stupidly expensive edges
        {
                adj[source][dest] = cost;
        }
        else
        {
                adj[source][dest] = NO_EDGE;
        }
}

// Let's keep a fast access to the path
int[][] path = new int[n_nodes][n_nodes];
for (int i = 0; i < n_nodes; ++i)
{
        for (int j = 0; j < n_nodes; ++j)
        {
                if (adj[i][j] == NO_EDGE)
                {
                        path[i][j] = -1;
                }
                else
                {
                        path[i][j] = i;
                }
        }
}

/* Floyd-Warshall */
for (int k = 0; k < n_nodes; ++k)
{
        for (int i = 0; i < n_nodes; ++i)
        {
                for (int j = 0; j < n_nodes; ++j)
                {
                        if (adj[i][j] > adj[i][k] + adj[k][j])
                        {
                                adj[i][j] = adj[i][k] + adj[k][j];
                                path[i][j] = path[k][j];
                        }
                }
        }
}

// Path from a given `start` to every other node in the graph
ArrayDeque<Integer> indexes = new ArrayDeque<>();
next_route:
for (int end = 0; end < n_nodes; ++end)
{
        indexes.clear();
        indexes.addFirst(end);

        int current_end = end,
            previous;
        while (path[start][current_end] != start)
        {
                previous = path[start][current_end];
                if (previous == -1) // No possible path
                {
                        continue next_route;
                }
                indexes.addFirst(previous);
                current_end = previous;
        }
        indexes.addFirst(start);
        System.out.println("Get to node " + end + " costs " + adj[start][end] + ".");
}
