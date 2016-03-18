package trie;

import java.util.*;

/**
 * Class <code>RWayTrie</code> implements interface <code>Trie</code>.
 */
public class RWayTrie implements Trie{


    private class RWayTrieIterator implements Iterator<String>{
        Queue<String> prefixes = new LinkedList<>();
        Queue<Node> nodes = new LinkedList<>();

        Node currentNode;
        String currentPrefix;
        boolean flag = false;

        public RWayTrieIterator(String prefix){
            this.currentNode = get(root, prefix, 0);
            this.currentPrefix = prefix;
            nodes.add(currentNode);
            prefixes.add(currentPrefix);
        }

        @Override
        public boolean hasNext() {
            if (!nodes.isEmpty()) return true;
            else{
                for (int i = 0; i < ALPHABET; i++) {
                    if (currentNode.next[i] != null) {
                        return true;
                    }
                }
                return false;
            }
        }

        @Override
        public String next() {
            while (true) {
                if (!flag) {
                    currentNode = nodes.poll();
                    currentPrefix = prefixes.poll();
                    if (currentNode.length != 0) {
                        flag = true;
                        return currentPrefix;
                    }
                }
                else{
                    flag = false;
                }
                        for (int i = 0; i < ALPHABET; i++) {
                            if (currentNode.next[i] != null) {
                                nodes.add(currentNode.next[i]);
                                char c = (char) (i + 'a');
                                prefixes.add(currentPrefix + c);
                            }
                        }


            }
        }
    }

    /**
     * Class <code>Node</code> has information about word ending and next letter in word.
     */
    private static class Node{
        /**
         * Length of the word. Not equals 0 when word ends.
         */
        private int length;
        /**
         * Array of pointer to next nodes.
         */
        private Node[] next = new Node[ALPHABET];
    }

    /**
     * Each word can consist of lower case 26 letter.
     */
    private static final int ALPHABET = 26;
    /**
     * Root node of the trie.
     */
    private Node root = new Node();
    /**
     * Number of words in the trie.
     */
    private int size;

    public void add(Tuple tuple) {
        root = add(root, tuple.word, tuple.length, 0);
        size++;
    }

    /**
     * Recursive word adding letter-by-letter.
     * @param node current node
     * @param word
     * @param length of word
     * @param d letter index (from 0 to 25)
     * @return root of the trie with added word
     */
    private Node add(Node node, String word, int length, int d){
        if (node == null) node = new Node();
        if (d == word.length()){
            node.length = length;
            return node;
        }

        char c = word.charAt(d);
        node.next[c - 'a'] = add(node.next[c - 'a'], word, length, d + 1);
        return node;
    }

    public void delete(String word) {
        root = delete(root, word, 0);
    }
    /**
     * Recursive word deleting letter-by-letter.
     * Also deletes chain of nodes which defines word in trie.
     * @param node current node
     * @param word
     * @param d letter index (from 0 to 25)
     * @return root of the trie with deleted word
     */
    private Node delete(Node node, String word, int d) {
        if (node == null) return null;
        if (d == word.length()) {
            if (node.length != 0) size--;
            node.length = 0;
        }
        else {
            char c = word.charAt(d);
            node.next[c - 'a'] = delete(node.next[c - 'a'], word, d+1);
        }

        if (node.length != 0) return node;
        for (int i = 0; i < ALPHABET; i++)
            if (node.next[i] != null)
                return node;
        return null;
    }

    public boolean contains(String word) {
        return get(word) != 0;
    }
    /**
     * Recursive word searching letter-by-letter.
     * @param word
     * @return length of found word
     */
    private int get(String word){
        Node node = get(root, word, 0);
        if (node == null) return 0;
        return node.length;
    }
    /**
     * Recursive word adding letter-by-letter.
     * @param node current node
     * @param word
     * @param d letter index (from 0 to 25)
     * @return node where word ends.
     */
    private Node get(Node node, String word, int d){
        if (node == null) return null;
        if (d == word.length()) return node;
        char c = word.charAt(d);
        return get(node.next[c - 'a'], word, d + 1);
    }



    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    public Iterable<String> wordsWithPrefix(String prefix){
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new RWayTrieIterator(prefix);
            }
        };
    }

    public int size() {
        return size;
    }
}
