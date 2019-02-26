public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        Object x = or.readObject();
        Object y = or.readObject();
        Object z = or.readObject();
        BinaryTrie root;
        int inputSymbolsLength;
        BitSequence result;

        if (z == null) {
            System.out.println("no number of symbols");
            root = (BinaryTrie) x;
            result = (BitSequence) y;
            inputSymbolsLength = result.length();
        } else {
            root = (BinaryTrie) x;
            inputSymbolsLength = (int) y;
            result = (BitSequence) z;
        }

        char[] symbols = new char[inputSymbolsLength];

        for (int i = 0; i < inputSymbolsLength; i += 1) {
            Match match = root.longestPrefixMatch(result);
            symbols[i] = match.getSymbol();
            result = result.allButFirstNBits(match.getSequence().length());
        }
        
        FileUtils.writeCharArray(args[1], symbols);
    }
}
