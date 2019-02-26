import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    private Node root;
    private HashMap<Character, BitSequence> table;

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();

        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            pq.insert(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        if (pq.size() == 1) {
            pq.insert(new Node('\0', 0, null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }

        root = pq.delMin();
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        Node node = root;

        for(int i = 0; i < querySequence.length(); i += 1) {
            if (node.isLeaf()) {
                return new Match(querySequence.firstNBits(i), node.ch);
            } else {
                if (querySequence.bitAt(i) == 0) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
        }

        return new Match(querySequence, node.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        table = new HashMap<>();
        buildLookupTableHelper(table, root, "");
        return table;
    }

    private void buildLookupTableHelper(Map<Character, BitSequence> table, Node node, String s) {
        if (node.isLeaf()) {
            table.put(node.ch, new BitSequence(s));
        } else {
            buildLookupTableHelper(table, node.left, s + '0');
            buildLookupTableHelper(table, node.right, s + '1');
        }
    }

    private class Node implements Serializable, Comparable<Node> {
        private char ch;
        private int freq;
        private Node left, right;

        private Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
}
