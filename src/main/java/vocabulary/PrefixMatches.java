package vocabulary;

import trie.RWayTrie;
import trie.Trie;
import trie.Tuple;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class <code>vocabulary.PrefixMatches</code> implements vocabulary.
 * Class implements adding, deleting and searching words by prefix.
 */
public class PrefixMatches {

    /**
     * In-memory implementation of vocabulary.
     */
    private Trie trie = new RWayTrie();

    /**
     * Add words to vocabulary. Ignore small words (at least 2 symbols).
     * @param strings array of words to add
     * @return added words number
     */
    public int add(String ... strings){
        int addedWords = 0;
        for(String word: strings){
            if (word.length() > 1 && !trie.contains(word)) {
                trie.add(new Tuple(word, word.length()));
                addedWords++;
            }

        }
        return addedWords;
    }

    /**
     * Check if word contains in the vocabulary.
     * @param word
     * @return <code>true</code> if word contains in vocabulary, else return <code>false</code>.
     */
    public boolean contains(String word){
        return trie.contains(word);
    }

    /**
     * Delete word from the vocabulary.
     * @param word
     */
    public void delete(String word){
         trie.delete(word);
    }

    /**
     * Get size of vocabulary.
     * @return size
     */
    public int size(){
        return trie.size();
    }

    /**
     * Get words which have according prefix.
     * If prefix is longer than or equals 2 symbols returns set of words with k different lengths from minimum length
     * and begins with according prefix.
     * @param pref prefix
     * @param k
     * @return list of suitable words
     */
    public Iterable<String> wordsWithPrefix(String pref, int k){
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return new PrefixMatchesIterator(trie.wordsWithPrefix(pref), k);
            }
        };
    }

    private class PrefixMatchesIterator implements Iterator<String>{
        Iterator<String> iterator;
        int k;
        public PrefixMatchesIterator(Iterable<String> iterable,int k){
            this.iterator = iterable.iterator();
            this.k = k;
        }

        String word;
        boolean polledNext = false;
        @Override
        public boolean hasNext() {
            if (polledNext) return true;
            while  (iterator.hasNext()){
                word = iterator.next();
                if (word.length() <= k + 2){
                    polledNext = true;
                    return true;
                }
            }

            return false;
        }

        @Override
        public String next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            polledNext = false;
            return word;
        }
    }



    /**
     * Get words which have according prefix.
     * If prefix is longer than or equals 2 symbols returns set of words with 3 different lengths from minimum length
     * and begins with according prefix.
     * @param pref prefix
     * @return list of suitable words
     */
    public Iterable<String> wordsWithPrefix(String pref){
        return wordsWithPrefix(pref, 3);
    }

    /**
     * Input words from file.
     * @param fileName File name.
     * @throws FileNotFoundException if file isn't found.
     */
    public int addWordsFromFile(String fileName) throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        int addedWords = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()){
            String word = scanner.nextLine();
            if (word.length() > 1) {
                trie.add(new Tuple(word, word.length()));
                addedWords++;
            }
        }
        return addedWords;
    }

    /**
     * Get trie for words.
     * @return trie.
     */
    public Trie getTrie() {
        return trie;
    }

    /**
     * Set trie for words.
     * @param trie
     */
    public void setTrie(Trie trie) {
        this.trie = trie;
    }
}
