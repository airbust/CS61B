import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();

        for (char ch : inputSymbols) {
            if (frequencyTable.containsKey(ch)) {
                frequencyTable.put(ch, frequencyTable.get(ch) + 1);
            } else {
                frequencyTable.put(ch, 1);
            }
        }

        return frequencyTable;
    }

    public static void main(String[] args) {
        char[] inputSymbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie root = new BinaryTrie(frequencyTable);

        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(root);
        ow.writeObject(inputSymbols.length);

        Map<Character, BitSequence> lookupTable = root.buildLookupTable();
        List<BitSequence> bitSequences = new ArrayList<>();

        for (char ch : inputSymbols) {
            bitSequences.add(lookupTable.get(ch));
        }

        BitSequence result = BitSequence.assemble(bitSequences);
        ow.writeObject(result);
    }
}
