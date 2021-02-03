import java.util.*;

/**
 * The DirectedWeightedGraph class is a structure to store a n-weighted
 * graph of nodes named 0 through N-1.
 * The implementation uses an adjacency-lists representation for the graph
 * and a HashMap indexed by strings of the form "u-v", u and v being nodes,
 * the map's values are a List of generic type.
 * Each edge can have a different number of weights (or even none).
 * @author Diogo Peralta Cordeiro <diogo@fc.up.pt>
 */
public class
DirectedWeightedGraph<T>
{
    private final List<LinkedList<Integer>> edges;
    private final Map<String, List<T>> edges_weights;
    public int n_nodes, n_edges; // N, #Edges, respectively

    // O(n) to construct the structure
    public
    DirectedWeightedGraph (int n_nodes)
    {
        this.n_nodes = n_nodes;
        this.n_edges = 0;
        this.edges = new ArrayList<>(n_nodes);
        this.edges_weights = new HashMap<>(n_nodes);
        for (int i = 0; i < n_nodes; ++i)
        {
            this.edges.add(i, new LinkedList<>());
        }
    }

    // O(1) to get the adjacency-list for a node
    public LinkedList<Integer>
    edges (int u)
    {
        return this.edges.get(u);
    }

    // O(1) to get the weights list
    public List<T>
    weight (int u, int w)
    {
        return this.edges_weights.get(u + "-" + w);
    }

    // O(1) to add a node to the last position of the adjacency-list
    // O(1) to update the weight of an existing edge
    @SafeVarargs
    public final void
    link (int u, int w, T... weight)
    {
        // O(1) to ensure we don't make a MultiGraph
        if (!this.edges(u).contains(w)) {
            // O(1) to add an adjacency
            this.edges(u).addLast(w);
            
            ++this.n_edges;
        }

        // O(1) to put or update a weight, if we want to
        this.edges_weights.put(u + "-" + w, Arrays.asList(weight));
    }

    // O(n) to remove a node
    public void
    delete_node (int u)
    {
        for (int w = 0; w < this.n_nodes; ++w)
        {
            this.unlink(u, w);
            this.unlink(w, u);
        }
    }

    // O(n) to remove an adjacency
    public void
    unlink (int u, int w)
    {
        // O(n)
        this.edges(u).remove((Integer) w);

        // O(1)
        this.edges_weights.remove(u + "-" + w);

        --this.n_edges;
    }

    /* UTILS */

    // O(#Edges + N*lg(N))
    public List<Object>
    int_dijkstra(int s) {
        /* Set Data Structures */
        int[] dist = new int[this.n_nodes];
        List<List<Integer>> come_from = new ArrayList<>(this.n_nodes);
        for (int i = 0; i < this.n_nodes; ++i) {
            dist[i] = 1000000000; // 1e9 to avoid overflow
            come_from.add(i, new LinkedList<>());
        }
        // Compare by distance, then by index
        TreeSet<Integer> to_explore = new TreeSet<>(Comparator.comparingInt((Integer i) -> dist[i]).thenComparingInt(i -> i));

        // Let's find distances from s
        dist[s] = 0;
        to_explore.add(s);

        // Dijkstra's Algorithm
        while (!to_explore.isEmpty()) {
            int u = to_explore.pollFirst();
            // Assert: u.distance is the true shortest distance from s to u
            // Assert: u is never put back into to_explore
            this.edges(u).forEach((w) -> {
                int edge_cost = (int) this.weight(u, w).get(0);
                int dist_w = dist[u] + edge_cost;
                if (dist_w < dist[w]) {
                    come_from.get(w).clear();
                    come_from.get(w).add(u);
                    // Decrease key with new distance
                    to_explore.remove(w);
                    dist[w] = dist_w;
                    to_explore.add(w);
                } else if (dist_w == dist[w]) {
                    come_from.get(w).add(u);
                }
            });
        }

        // That's all folks.
        return Arrays.asList(dist, come_from);
    }
    public List<Object>
    double_dijkstra(int s) {
        /* Set Data Structures */
        double[] dist = new double[this.n_nodes];
        List<List<Integer>> come_from = new ArrayList<>(this.n_nodes);
        for (int i = 0; i < this.n_nodes; ++i) {
            dist[i] = 1000000000; // 1e9 to avoid overflow
            come_from.add(i, new LinkedList<>());
        }
        // Compare by distance, then by index
        TreeSet<Integer> to_explore = new TreeSet<>(Comparator.comparingDouble((Integer i) -> dist[i]).thenComparingInt(i -> i));

        // Let's find distances from s
        dist[s] = 0;
        to_explore.add(s);

        // Dijkstra's Algorithm
        while (!to_explore.isEmpty()) {
            int u = to_explore.pollFirst();
            // Assert: u.distance is the true shortest distance from s to u
            // Assert: u is never put back into to_explore
            this.edges(u).forEach((w) -> {
                double edge_cost = (double) this.weight(u, w).get(0);
                double dist_w = dist[u] + edge_cost;
                if (Double.compare(dist_w, dist[w]) < 0) {
                    come_from.get(w).clear();
                    come_from.get(w).add(u);
                    // Decrease key with new distance
                    to_explore.remove(w);
                    dist[w] = dist_w;
                    to_explore.add(w);
                } else if (Double.compare(dist_w, dist[w]) == 0) {
                    come_from.get(w).add(u);
                }
            });
        }

        // That's all folks.
        return Arrays.asList(dist, come_from);
    }

    // O(#Edges + N*lg(N))
    public int[]
    int_prim() {
        /* Set Data Structures */
        int[] dist = new int[this.n_nodes];
        boolean[] in_tree = new boolean[this.n_nodes];
        int[] come_from = new int[this.n_nodes];
        Arrays.fill(dist, 1000000000); // 1e9 to avoid overflow
        TreeSet<Integer> to_explore = new TreeSet<>(Comparator.comparingInt((Integer i) -> dist[i]).thenComparingInt(i -> i));

        // Let's find distances from s
        int s = 0;
        dist[s] = 0;
        to_explore.add(s);

        while (!to_explore.isEmpty()) {
            int u = to_explore.pollFirst();
            in_tree[u] = true;
            // Let t be the graph made of nodes with in_tree = true,
            // and edges {(w - come_from[w]), for w in g.nodes excluding s}
            // Assert: t is part of an MST for g
            this.edges(u).forEach((w) -> {
                int edge_cost = (int) this.weight(u, w).get(0);
                if (!in_tree[w] && edge_cost < dist[w]) {
                    come_from[w] = u;
                    to_explore.remove(w);
                    dist[w] = edge_cost;
                    to_explore.add(w);
                }
            });
        }

        // That's all folks.
        return come_from;
    }
    public int[]
    double_prim() {
        /* Set Data Structures */
        double[] dist = new double[this.n_nodes];
        boolean[] in_tree = new boolean[this.n_nodes];
        int[] come_from = new int[this.n_nodes];
        Arrays.fill(dist, 1000000000); // 1e9 to avoid overflow
        TreeSet<Integer> to_explore = new TreeSet<>(Comparator.comparingDouble((Integer i) -> dist[i]).thenComparingInt(i -> i));

        // Let's find distances from s
        int s = 0;
        dist[s] = 0;
        to_explore.add(s);

        while (!to_explore.isEmpty()) {
            int u = to_explore.pollFirst();
            in_tree[u] = true;
            // Let t be the graph made of nodes with in_tree = true,
            // and edges {(w - come_from[w]), for w in g.nodes excluding s}
            // Assert: t is part of an MST for g
            this.edges(u).forEach((w) -> {
                double edge_cost = (double) this.weight(u, w).get(0);
                if (!in_tree[w] && Double.compare(edge_cost, dist[w]) < 0) {
                    come_from[w] = u;
                    to_explore.remove(w);
                    dist[w] = edge_cost;
                    to_explore.add(w);
                }
            });
        }

        // That's all folks.
        return come_from;
    }

    // O(N*#Edges)
    public int[]
    int_bellman_ford (int s) throws Exception {
        int[] min_weight = new int[this.n_nodes];
        // best estimate so far of minimum weight from s to another vertex
        Arrays.fill(min_weight, 1000000000); // 1e9 to avoid overflow
        min_weight[s] = 0;

        // Repeat
        for (int i = 0; i < this.n_nodes; ++i) {
            // relax all the edges
            for (int u = 0; u < this.n_nodes; ++u) {
                for (int w : this.edges(u)) {
                    int edge_cost = (int) this.weight(u, w).get(0);
                    min_weight[w] = Math.min(min_weight[u] + edge_cost, min_weight[w]);
                    // Assert: v.min_weight >= true minimum weight from s to v
                }
            }
        }

        for (int u = 0; u < this.n_nodes; ++u) {
            for (int w : this.edges(u)) {
                int edge_cost = (int) this.weight(u, w).get(0);
                if (min_weight[u] + edge_cost < min_weight[w]) {
                    throw new Exception("Negative-weight cycle detected.");
                }
            }
        }

        // That's all folks.
        return min_weight;
    }
}
