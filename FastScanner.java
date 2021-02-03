package ProgrammingChallenges;
// FastScanner
// Pedro Ribeiro (DCC/FCUP)
// Minor edits by Diogo Cordeiro

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FastScanner {
    BufferedReader br;
    StringTokenizer st;

    public FastScanner(InputStream stream) {
        br = new BufferedReader(new InputStreamReader(stream));
    }

    public String next() {
        while (st == null || !st.hasMoreElements()) {
            String ffs = null;
            try {
                ffs = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (ffs != null)
                st = new StringTokenizer(ffs);
            else return null;
        }
        return st.nextToken();
    }

    //public Integer nextInt() {
    public int nextInt() {
        //String n = next();
        //if (n == null) return null;
        //return Integer.parseInt(n);
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
