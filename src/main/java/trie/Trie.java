package trie;

/**
 * Interface <code>Trie</code>.
 * The trie is an in-memory implementation of vocabulary.
 * The user of this interface has precise control over words in the trie.
 * The user can add, delete , search by prefix and check containing words in the trie.
 */
public interface Trie {
    /**
     * Add tuple to the trie.
     * @param tuple has word and its length
     */
    void add(Tuple tuple);

    /**
     * Delete word from the trie.
     * @param word
     */
    void delete(String word);

    /**
     * Check containing word in vocabulary.
     * @param word
     * @return <code>true</code> if word contains in vocabulary, else return <code>false</code>.
     */
    boolean contains(String word);

    /**
     * Get all words from the trie.
     * @return list of suitable words
     */
    Iterable<String> words();
    /**
     * Get words which have according prefix.
     * @param pref prefix
     * @return list of suitable words
     */
    Iterable<String> wordsWithPrefix(String pref);

    /**
     * Get size of the trie.
     * @return size
     */
    int size();


}
