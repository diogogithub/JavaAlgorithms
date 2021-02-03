package CompetitiveProgramming.Uva;

import CompetitiveProgramming.DirectedMultiGraph;

import java.util.*;

public class
M11709
{
    static DirectedMultiGraph g, g_transpose;
    static int n_nodes, // P
            T,
            idx;
    static Map<String, Integer> name_idx;
    private static int number_of_scc = 0,
            current_scc_n_nodes = 0;

    /**
     * WARNING: Changes all graph nodes' visited state to true
     * temporary storage: current_scc_n_nodes won't be reset by the end
     *
     * Time complexity: O(E+N)
     * void operations: Will set number_of_scc
     *
     * @param g                  DirectedMultiGraph      The graph to be used
     * @param u                  int                     The start point
     * @param dfs_finished_stack Stack<int>              ending_sequence I/O (optional) If DAG is given,
     *                                                   it will correspond to a top sort (total order)
     */
    @SafeVarargs
    private static void
    dfs_visit(DirectedMultiGraph g, int u, ArrayDeque<Integer>... dfs_finished_stack) {
        g.seen[u] = true;
        ++current_scc_n_nodes;

        for (int w : g.edges(u)) {
            if (!g.seen[w]) {
                dfs_visit(g, w, dfs_finished_stack);
            }
        }

        // Check if a dfs_finished_stack was supplied to be filled
        if (dfs_finished_stack.length == 1) {
            dfs_finished_stack[0].addFirst(u);
        }
    }

    // Time complexity: O(E+N)
    private static void
    kosaraju_sharir(DirectedMultiGraph g, DirectedMultiGraph g_transpose, int n_nodes) {
        // DFS visit the graph
        ArrayDeque<Integer> dfs_finished_stack = new ArrayDeque<>();
        for (int v = 0; v < n_nodes; ++v) {
            if (!g.seen[v]) {
                dfs_visit(g, v, dfs_finished_stack);
            }
        }

        // Reset globals
        current_scc_n_nodes = 0;
        number_of_scc = 0;

        // DFS visit in transposed graph using dfs_finished_stack seq
        while (!dfs_finished_stack.isEmpty()) {
            int v = dfs_finished_stack.removeFirst();

            // New component is being discovered
            if (!g_transpose.seen[v]) {
                current_scc_n_nodes = 0;
                dfs_visit(g_transpose, v);

                // Actions
                ++number_of_scc;
            }
        }
    }

    public static int
    idx_in_graph(String name) {
        Integer u = name_idx.get(name);
        if (u == null) {
            u = idx++;
            name_idx.put(name, u);
        }
        return u;
    }

    public static boolean
    generate_structures(Scanner stdin) {
        if (n_nodes == 0 && T == 0) return false;
        stdin.nextLine(); // flush from int buffer
        name_idx = new HashMap<>(n_nodes);
        idx = 0;
        g = new DirectedMultiGraph(n_nodes, true);
        g_transpose = new DirectedMultiGraph(n_nodes, true);

        for (int p = 0; p < n_nodes; ++p) {
            String name = stdin.nextLine();
            idx_in_graph(name);
        }
        for (int t = 0; t < T; ++t) {
            String u_name = stdin.nextLine(),
                    w_name = stdin.nextLine();
            int u = idx_in_graph(u_name),
                    w = idx_in_graph(w_name);
            g.link_first(u, w);
            g_transpose.link_first(w, u);
        }

        return true;
    }

    public static void
    main(String[] args) {
        // Input and Data Representation
        Scanner stdin = new Scanner(System.in);
        while (true) {
            n_nodes = stdin.nextInt();
            T = stdin.nextInt();
            if (!generate_structures(stdin)) break;
            // Process
            kosaraju_sharir(g, g_transpose, n_nodes);
            // Output
            System.out.println(number_of_scc);
        }
        // That's all folks
        stdin.close();
    }
}
