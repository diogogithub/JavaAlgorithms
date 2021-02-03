package ProgrammingChallenges.Uva;

import java.util.*;
import java.util.function.IntPredicate;

class UVA11286 {
    static final int modules_per_term = 5;

    public static void
    main(String[] args) {
        Scanner stdin = new Scanner(System.in);

        // <Combination, Frequency>
        Map<List<Integer>, Integer> freshers_modules = new HashMap<>();

        while (stdin.hasNext()) {
            int number_of_freshers = stdin.nextInt();
            if (number_of_freshers == 0) {
                break; // This was the last test case
            }
            freshers_modules.clear(); // Reset our data structure

            for (int i = 0; i < number_of_freshers; ++i) {
                List<Integer> modules = new ArrayList<>();
                for (int j = 0; j < modules_per_term; ++j) {
                    int x = stdin.nextInt();
                    modules.add(x);
                }
                Collections.sort(modules);
                freshers_modules.merge(modules, 1, Integer::sum);
            }

            // Most popular pair
            Map.Entry<List<Integer>, Integer> most_popular_module = Collections.max(freshers_modules.entrySet(), Map.Entry.comparingByValue());
            // How many are as popular?
            int max_students = most_popular_module.getValue();
            int number_of_equally_popular_modules = collections_count_if(freshers_modules.values(), (a) -> a == max_students);
            // This is the sh1ttiest prompt ever...
            //System.out.println("max_students = "+max_students+"\nnumber_of_equally_popular_modules = "+number_of_equally_popular_modules+"\nnumber_of_freshers = "+number_of_freshers);
            System.out.println(max_students * number_of_equally_popular_modules);
        }
        stdin.close();
    }

    public static int
    collections_count_if(Collection<Integer> coll, IntPredicate op) {
        int count = 0;
        for (Integer v : coll) {
            if (op.test((v))) {
                ++count;
            }
        }
        return count;
    }

}
