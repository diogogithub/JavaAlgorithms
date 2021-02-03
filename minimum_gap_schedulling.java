import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Activity implements Comparable<Activity>
{
    int start, end;

    Activity(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "["+start+","+end+"]";
    }

    @Override
    // Early Start
    // Longest size
    public int compareTo(Activity b) {
        int early_start = Integer.compare(this.start, b.start);
        if (early_start == 0)
            return Integer.compare(b.end-b.start, this.end-this.start);
        return early_start;
    }
    
    public boolean
    intersects (Activity b)
    {
        return this.start <= b.start ||
               b.end < this.start;
    }
}

class
DAA013
{
    public static void
    main (String[] args)
    {
        FastScanner stdin = new FastScanner(System.in);

        int M = stdin.nextInt(), // tamanho do segmento a cobrir
            N = stdin.nextInt(); // qtd de segmentos

        Activity[] acts = new Activity[N];
        for (int i = 0; i < N; ++i)
        {
            int l = stdin.nextInt(),
                r = stdin.nextInt();
            acts[i] = new Activity(l, r);
        }

        // Sort them
        Arrays.sort(acts);
        //System.out.println(Arrays.toString(acts));

        // Cobertura mínima
        int end = 0;
        Activity x = acts[0];
        List<Activity> path = new ArrayList<>();
        while (end < M)
        {
            for (Activity candidate : acts)
            {
                if (candidate.start <= end)
                {
                    if (candidate.end >= x.end)
                    {
                        x = candidate;
                    }
                }
            }
            end = x.end;
            path.add(x);
        }
        //System.out.println(path);

        FastPrint.out.println(path.size());
        FastPrint.out.close();
    }
}
