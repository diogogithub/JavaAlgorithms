import java.util.Comparator;
import java.util.Map;

// from: https://stackoverflow.com/a/3074324
public class ValueThenKeyComparator<K extends Comparable<? super K>,
        V extends Comparable<? super V>>
        implements Comparator<Map.Entry<K, V>> {

    public int compare(Map.Entry<K, V> a, Map.Entry<K, V> b) {
        int cmp1 = b.getValue().compareTo(a.getValue());
        if (cmp1 != 0) {
            return cmp1;
        } else {
            return a.getKey().compareTo(b.getKey());
        }
    }
}
