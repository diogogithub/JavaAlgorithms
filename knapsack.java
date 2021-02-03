import java.util.Arrays;

public class DAA011 {
    public static void
    main(String[] args)
    {
        FastScanner stdin = new FastScanner(System.in);
        int n = stdin.nextInt();
        int[] dist = new int[n];
        int max = 0;

        for (int i = 0; i < n; ++i) {
            dist[i] = stdin.nextInt();
            max += dist[i];
        }

        for (int p = stdin.nextInt(); p > 0; --p) {
            FastPrint.out.println(cond_bsearch(dist, 0, max, stdin.nextInt()));
        }

        FastPrint.out.close();
    }

    public static int
    cond_bsearch(int[] haystack, int l, int r, int k)
    {
        //System.out.println(Arrays.toString(haystack));
        //System.out.println("l: "+l+" | r: "+r+" | k: "+k);
        while (l < r) {
            int mid = l + (r - l) / 2;

            if (cond(haystack, mid, k)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }

        //System.out.println("l: "+l+" | r: "+r+" | k: "+k);
        //return cond(haystack, l, k) ? l : -1;
        return l;
    }

    public static boolean
    cond(int[] dist, int needle, int k)
    {
        //System.out.println("needle: "+needle);
        int acc = 0;
        int partitions = 0;
        for (int i : dist) { // 10
            acc += i; // 18
            if (acc > needle) { // 18 > 8 = true
                acc = i; // This one isn't part of the partition we've just created = 10
                if (i > needle) return false;
                if (++partitions > k) { //part = 7 > 10 = false
                    //System.out.println("last number of partitions: "+partitions);
                    return false; // early break to save time
                }
            }
            //System.out.println("i: "+i+" | acc: "+acc+" | part: "+partitions);
        }
        //System.out.println("last_number of partitions: "+partitions);
        return partitions < k;
    }
}
