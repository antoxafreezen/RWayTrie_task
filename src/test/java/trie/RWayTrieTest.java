package trie;

import org.junit.Before;
import org.junit.Test;
import trie.RWayTrie;
import trie.Trie;
import trie.Tuple;

import java.util.Iterator;

import static org.junit.Assert.*;
/**
 * Created by User on 16.03.2016.
 */

public class RWayTrieTest {

    Trie trie = new RWayTrie();


    @Before
    public void addWordToTrie(){
        trie.add(new Tuple("anton", 5));
        trie.add(new Tuple("ant", 3));
        trie.add(new Tuple("babak", 5));
        int actualSize = trie.size();
        int expectedSize = 3;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void deleteWordFromTrie() {
        trie.delete("anton");
        int expectedSize = 2;
        assertEquals(expectedSize, trie.size());
    }

    @Test
    public void isWordContainsInTrie(){
        assertEquals(true, trie.contains("ant"));
        trie.delete("anton");
        assertEquals(false, trie.contains("anton"));
    }

    @Test
    public void getWordsStartWithPrefix(){

        int expectedSize = 3;
        Iterator<String> iterator = trie.words().iterator();
        int actualSize = 0;
        while (iterator.hasNext()){
            iterator.next();
            actualSize++;
        }
        assertEquals(expectedSize, actualSize);
    }
}
