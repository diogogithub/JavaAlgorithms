public class bfs_shortest_path
{
    public static void main (String[] args)
    {
        int n_nodes = 42;
        boolean[] come_from = new boolean[n_nodes];
        DirectedMultiGraph graph = new DirectedMultiGraph(n_nodes, true);

        // Input
        Scanner stdin = new Scanner(System.in);
        int a = stdin.nextInt(), b, graph_root = a;
        while ((b = stdin.nextInt()) != 0)
        {
            graph.link_first(a, b);

            a = b;
        }
        int destination = a;

        // BFS
        ArrayDeque<Integer> to_explore = new ArrayDeque<>(); // Queue
        to_explore.addFirst(graph_root);
        graph.seen[graph_root] = true;
        while (!to_explore.isEmpty())
        {
            int u = to_explore.removeLast();
            this.edges(u).forEach((w) ->
            {
                if (!g.seen[w])
                {
                    to_explore.addFirst(w);
                    g.seen[w] = true;
                    come_from[w] = v;
                }
            }
        }

        // Output
        ArrayDeque<Integer> path = new ArrayDeque<>();
        path.addFirst(destination);
        while(path.getFirst() != graph_root)
        {
            path.addFirst(graph.verts[path.getFirst()].come_from);
        }
        System.out.println("Output:");
        for (int i : path)
        {
            System.out.println(i);
        }

        // No leaks
        stdin.close();
    }
}
