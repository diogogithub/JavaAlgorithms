/******************************************************************************
 *
 *  A segment tree data structure.
 *
 ******************************************************************************/

import java.util.Arrays;

/**
 * The {@code SegmentTree} class is a structure for efficient search of cumulative data.
 * It performs Range Min, Max, Sum and Multiplication Queries in O(log(n)) time.
 * <p>
 * Also it has been develop with  {@code LazyPropagation} for range updates, which means
 * when you perform update operations over a range, the update process affects the least nodes as possible
 * so that the bigger the range you want to update the less time it consumes to update it. Eventually those changes will be propagated
 * to the children and the whole array will be up to date.
 * <p>
 * Example:
 * <p>
 * SegmentTreeHeap st = new SegmentTreeHeap(new Integer[]{1,3,4,2,1, -2, 4});
 * st.update(0,3, 1)
 * In the above case only the node that represents the range [0,3] will be updated (and not their children) so in this case
 * the update task will be less than n*log(n)
 * <p>
 * Memory usage:  O(n)
 *
 * @author Ricardo Pacheco (base code)
 * @author Diogo Cordeiro (implemented range max and multiplication queries, plus other minor changes)
 * @link https://algs4.cs.princeton.edu/99misc/SegmentTree.java.html
 */
public class SegmentTree {

    private final STNode[] heap;
    private final int[] array;

    /**
     * Time-Complexity:  O(n*log(n))
     *
     * @param array the Initialization array
     */
    public SegmentTree(int[] array) {
        this.array = Arrays.copyOf(array, array.length);
        // The max size of this array is about 2 * 2 ^ log2(n) + 1
        int size = (int) (2 * Math.pow(2.0, Math.floor((Math.log(array.length) / Math.log(2.0)) + 1)));
        heap = new STNode[size];
        build(1, 0, array.length);
    }

    public int size() {
        return array.length;
    }

    // Initialize the Nodes of the Segment tree
    private void build(int v, int from, int size) {
        heap[v] = new STNode();
        heap[v].from = from;
        heap[v].to = from + size - 1;

        if (size == 1) {
            heap[v].sum = array[from];
            heap[v].mul = array[from];
            heap[v].min = array[from];
            heap[v].max = array[from];
        } else {
            // Build childs
            build(2 * v, from, size / 2);
            build(2 * v + 1, from + size / 2, size - size / 2);

            heap[v].sum = heap[2 * v].sum + heap[2 * v + 1].sum;
            heap[v].mul = heap[2 * v].mul * heap[2 * v + 1].mul;
            // min = min of the children
            heap[v].min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
            // max = max of the children
            heap[v].max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
        }
    }

    /**
     * Range Sum Query
     * <p>
     * Time-Complexity: O(log(n))
     *
     * @param from from index
     * @param to   to index
     * @return sum
     */
    public int rsq(int from, int to) {
        return rsq(1, from, to);
    }

    private int rsq(int v, int from, int to) {
        STNode n = heap[v];

        // If you did a range update that contained this node, you can infer the Sum without going down the tree
        if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
            return (to - from + 1) * n.pendingVal;
        }

        if (contains(from, to, n.from, n.to)) {
            return heap[v].sum;
        }

        if (intersects(from, to, n.from, n.to)) {
            propagate(v);
            int leftSum = rsq(2 * v, from, to);
            int rightSum = rsq(2 * v + 1, from, to);

            return leftSum + rightSum;
        }

        return 0;
    }

    /**
     * Range Multiplication Query
     * <p>
     * Time-Complexity: O(log(n))
     *
     * @param from from index
     * @param to   to index
     * @author Diogo Peralta Cordeiro <diogo@fc.up.pt>
     * @return multiplication
     */
    public int rmq(int from, int to) {
        return rmq(1, from, to);
    }

    private int rmq(int v, int from, int to) {
        STNode n = heap[v];

        // If you did a range update that contained this node, you can infer the Product without going down the tree
        if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
            return (to - from + 1) * n.pendingVal;
        }

        if (contains(from, to, n.from, n.to)) {
            return heap[v].mul;
        }

        if (intersects(from, to, n.from, n.to)) {
            propagate(v);
            int leftMul = rmq(2 * v, from, to);
            int rightMul = rmq(2 * v + 1, from, to);

            return leftMul * rightMul;
        }

        return 1; // neutral element
    }

    /**
     * Range Min Query
     * <p>
     * Time-Complexity: O(log(n))
     *
     * @param from from index
     * @param to   to index
     * @return min
     */
    public int rMinQ(int from, int to) {
        return rMinQ(1, from, to);
    }

    private int rMinQ(int v, int from, int to) {
        STNode n = heap[v];


        // If you did a range update that contained this node, you can infer the Min value without going down the tree
        if (n.pendingVal != null && contains(n.from, n.to, from, to)) {
            return n.pendingVal;
        }

        if (contains(from, to, n.from, n.to)) {
            return heap[v].min;
        }

        if (intersects(from, to, n.from, n.to)) {
            propagate(v);
            int leftMin = rMinQ(2 * v, from, to);
            int rightMin = rMinQ(2 * v + 1, from, to);

            return Math.min(leftMin, rightMin);
        }

        return 1000000000; // 1e9 to avoid overflow
    }

    /**
     * Range Max Query
     * <p>
     * Time-Complexity: O(log(n))
     *
     * @param from from index
     * @param to   to index
     * @author Diogo Peralta Cordeiro <diogo@fc.up.pt>
     * @return max
     */
    public int rMaxQ(int from, int to) {
        return rMaxQ(1, from, to);
    }

    private int rMaxQ(int v, int from, int to) {
        STNode n = heap[v];

        // If you did a range update that contained this node, you can infer the Max value without going down the tree
        if (n.pendingVal != null && contains(n.from, n.to, from, to))
            return n.pendingVal;
        if (contains(from, to, n.from, n.to))
            return heap[v].max;
        if (intersects(from, to, n.from, n.to)) {
            propagate(v);
            int leftMax = rMaxQ(2 * v, from, to);
            int rightMax = rMaxQ(2 * v + 1, from, to);
            return Math.max(leftMax, rightMax);
        }
        return -1000000000; // 1e9 to avoid overflow
    }

    /**
     * Range Update Operation.
     * With this operation you can update either one position or a range of positions with a given number.
     * The update operations will update the less it can to update the whole range (Lazy Propagation).
     * The values will be propagated lazily from top to bottom of the segment tree.
     * This behavior is really useful for updates on portions of the array
     * <p>
     * Time-Complexity: O(log(n))
     *
     * @param from  from index
     * @param to    to index
     * @param value value
     */
    public void update(int from, int to, int value) {
        update(1, from, to, value);
    }

    private void update(int v, int from, int to, int value) {

        // The Node of the heap tree represents a range of the array with bounds: [n.from, n.to]
        STNode n = heap[v];

        /**
         * If the updating-range contains the portion of the current Node  We lazily update it.
         * This means We do NOT update each position of the vector, but update only some temporal
         * values into the Node; such values into the Node will be propagated down to its children only when they need to.
         */
        if (contains(from, to, n.from, n.to)) {
            change(n, value);
        }

        if (n.size() == 1) return;

        if (intersects(from, to, n.from, n.to)) {
            /**
             * Before keeping going down to the tree We need to propagate the
             * the values that have been temporally/lazily saved into this Node to its children
             * So that when We visit them the values  are properly updated
             */
            propagate(v);

            update(2 * v, from, to, value);
            update(2 * v + 1, from, to, value);

            n.sum = heap[2 * v].sum + heap[2 * v + 1].sum;
            n.mul = heap[2 * v].mul * heap[2 * v + 1].mul;
            n.min = Math.min(heap[2 * v].min, heap[2 * v + 1].min);
            n.max = Math.max(heap[2 * v].max, heap[2 * v + 1].max);
        }
    }

    // Propagate temporal values to children
    private void propagate(int v) {
        STNode n = heap[v];

        if (n.pendingVal != null) {
            change(heap[2 * v], n.pendingVal);
            change(heap[2 * v + 1], n.pendingVal);
            n.pendingVal = null; //unset the pending propagation value
        }
    }

    // Save the temporal values that will be propagated lazily
    private void change(STNode n, int value) {
        n.pendingVal = value;
        n.sum = n.size() * value;
        n.mul = n.size() * value;
        n.min = value;
        n.max = value;
        array[n.from] = value;

    }

    // Test if the range1 contains range2
    private boolean contains(int from1, int to1, int from2, int to2) {
        return from2 >= from1 && to2 <= to1;
    }

    // check inclusive intersection, test if range1[from1, to1] intersects range2[from2, to2]
    private boolean intersects(int from1, int to1, int from2, int to2) {
        return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
    }

    // The Node class represents a partition range of the array.
    static class STNode {
        int sum, mul;
        int min, max;
        // Here we store the value that will be propagated lazily
        Integer pendingVal = null;
        int from;
        int to;

        int size() {
            return to - from + 1;
        }

    }

}
