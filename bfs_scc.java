// Pre-Process answers with a BFS (only possible if the graph is undirected)
int[] answers = new int[n];
ArrayDeque<Integer> queue = new ArrayDeque<>(n); // Queue
for (int s = n-1; s >= 0; --s)
{
    if (answers[s] < s)
        answers[s] = s;
    queue.addFirst(s);
    g.seen[s] = true;
    while(!queue.isEmpty())
    {
        int u = queue.removeLast();
        this.edges(u).forEach((w) -> {
            if (!g.verts[w].seen)
            {
                queue.addFirst(w);
                g.seen[w] = true;
                answers[w] = s;
            }
        }
    }
}
