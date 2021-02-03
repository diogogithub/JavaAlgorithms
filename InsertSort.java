import java.util.Arrays;

public class insertsort
{
        public static void main (String[] args)
        {
                int[] in = {5,1,2,3};

                System.out.println("Before insertion sort: " + Arrays.toString(in));

                for (int i = 1; i < in.length; ++i)
                {
                        int j = i - 1; // Ensure we don't get in a off by one error
                        while (j >= 0 && in[j] > in[j + 1])
                        {
                                int t = in[j];
                                in[j] = in[j+1];
                                in[j+1] = t;
                                --j; // Ensure that all the positions before are still correct
                        }
                }

                System.out.println("After insertion sort: " + Arrays.toString(in));
        }
}

