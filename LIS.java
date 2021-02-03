package ProgrammingChallenges.Uva;

import ProgrammingChallenges.FastPrint;

import java.io.BufferedOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.stream.IntStream;

public class
UVA10534 {
    // https://stackoverflow.com/a/17660641
    // Time complexity: O(n*lg(n))
    public static int[]
    LIS(int[] a) {
        int n = a.length;
        int INF = 1000000000;
        int[] seq = new int[n];
        int[] d = new int[n + 1];
        d[0] = -INF;
        for (int i = 1; i < n + 1; ++i) {
            seq[i - 1] = 1;
            d[i] = INF;
        }

        for (int i = 1; i < n + 1; ++i) {
            int low = 0,
                    high = i;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (d[mid] < a[i - 1]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            d[low] = a[i - 1];
            seq[i - 1] = low;
        }

        return seq;
    }

    public static int[]
    reverse_arr(int[] seq) {
        /*int[] reverse_seq = new int[seq.length];
        for (int i = 0, j = seq.length-1; i < seq.length; ++i, --j)
            reverse_seq[j] = seq[i];*/
        return IntStream.range(0, seq.length)
                .map(i -> seq[seq.length - 1 - i])
                .toArray();
    }

    public static int[]
    LDS(int[] seq) {
        /*int[] reverse_seq = IntStream.range(0, seq.length)
                .map(i -> seq[seq.length - 1 - i])
                .toArray();*/
        /*int[] reverse_seq = new int[seq.length];
        for (int i = 0, j = seq.length-1; i < seq.length; ++i, --j)
            reverse_seq[j] = seq[i];*/
        return reverse_arr(LIS(reverse_arr(seq)));
    }

    public static void
    main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        while (stdin.hasNext()) {
            // Input
            int N = stdin.nextInt();
            int[] seq = new int[N];
            for (int i = 0; i < N; ++i) {
                seq[i] = stdin.nextInt();
            }
            //System.out.println(Arrays.toString(seq));

            // Process
            int[] lis = LIS(seq);
            int[] lds = LDS(seq);
            int ans = 0;
            for (int i = 0; i < N; ++i) {
                //System.out.println(lis[i] + " " + lds[i]);
                int min = Math.min(lis[i], lds[i]);
                ans = Math.max(ans, 2 * min - 1);
            }
            // Output
            FastPrint.out.println(ans);
        }
        FastPrint.out.close();
    }
}
