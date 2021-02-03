import java.util.*;

/**
 * The DirectedMultiGraph class is a structure to store a non-weighted
 * graph of nodes named 0 through N-1.
 * The implementation uses an adjacency-lists representation.
 * @author Diogo Peralta Cordeiro <diogo@fc.up.pt>
 */
public class
DirectedMultiGraph
{
    private final List<LinkedList<Integer>> edges;
    public final int[] out_degree, in_degree;
    public int n_nodes, n_edges; // N, #Edges, respectively

    // O(n) to construct the structure
    public
    DirectedMultiGraph (int n_nodes)
    {
        this.n_nodes = n_nodes;
        this.n_edges = 0;
        this.edges = new ArrayList<>(n_nodes);
        this.out_degree = new int[n_nodes];
        this.in_degree = new int[n_nodes];
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

    // O(1) to get the first adjacency of a node
    public int
    first_edge (int u)
    {
        return this.edges.get(u).getFirst();
    }

    // O(1) to add an adjacency
    public final void
    link_first (int u, int w)
    {
        // If we don't want a Multi-graph and can't avoid
        // duplicate links from the outside, uncomment the following
        // line
        //if (node_links(u).contains(w)) return;
        this.edges(u).addFirst(w);
        ++this.out_degree[u];
        ++this.in_degree[w];
        ++this.n_edges;
    }

    // O(1) to remove an adjacency
    public void
    unlink_first (int u)
    {
        try
        {
            int w = this.edges(u).removeFirst();
            --this.out_degree[u];
            --this.in_degree[w];
            --this.n_edges;
        }
        catch (NoSuchElementException e)
        {
            // void.
        }
    }

    // Convenience: In case the requester needs to hold such type of information about the nodes
    public boolean[] seen;
    public
    DirectedMultiGraph (int n_nodes, boolean have_seen)
    {
        this(n_nodes);
        if (have_seen)
            seen = new boolean[n_nodes];
    }
}
