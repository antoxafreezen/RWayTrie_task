package vocabulary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import trie.RWayTrie;
import trie.Trie;
import trie.Tuple;

import java.io.FileNotFoundException;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PrefixMatchesTest {

    @Mock
    private Trie trie;

    @InjectMocks
    private PrefixMatches prefixMatches;

    @Test
    public void addOneWord(){
        int addedWords = prefixMatches.add("anton");
        verify(trie, times(1)).add(any(Tuple.class));
        assertEquals(1, addedWords);
    }


    @Test
    public void addManyWordsAndOneShouldBeAdded(){
        Trie trie = spy(new RWayTrie());
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.setTrie(trie);
        int addedWords = prefixMatches.add("anton", "anton", "e", "anton");
        verify(trie, times(1)).add(any(Tuple.class));
        assertEquals(1, addedWords);
    }


    @Test(expected = NullPointerException.class)
    public void addNullWordShouldRiseException(){
        when(prefixMatches.add(null)).thenThrow(NullPointerException.class);
    }

    @Test
    public void addSmallWord(){
        int addedWord = prefixMatches.add("a");
        verify(trie, never()).add(any(Tuple.class));
        assertEquals(0, addedWord);
    }

    @Test
    public void containsChecksOnce(){
        prefixMatches.contains("anton");
        verify(trie, times(1)).contains("anton");
    }

    @Test
    public void isWordContainsInTrie(){
        Trie trie = spy(new RWayTrie());
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.setTrie(trie);
        prefixMatches.add("anton");
        boolean actualContainsStatus = trie.contains("anton");
        verify(trie, times(2)).contains("anton");
        assertEquals(true, actualContainsStatus);
        actualContainsStatus = trie.contains("ant");
        assertEquals(false, actualContainsStatus);
    }

    @Test(expected = NullPointerException.class)
    public void containsNullWordShouldRiseException(){
        doThrow(new NullPointerException()).when(trie).contains(null);
        prefixMatches.contains(null);
    }

    @Test
    public void deleteShouldDoOnce(){
        prefixMatches.add("anton");
        prefixMatches.delete("anton");
        verify(trie, times(1)).delete("anton");
    }

    @Test(expected = NullPointerException.class)
    public void deleteNullWordShouldRiseException(){
        doThrow(new NullPointerException()).when(trie).delete(null);
        prefixMatches.delete(null);
    }

    @Test
    public void checkDeletingWordFromTrie(){
        Trie trie = spy(new RWayTrie());
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.setTrie(trie);
        prefixMatches.add("anton");
        assertEquals(1, prefixMatches.size());
        prefixMatches.delete("anton");
        verify(trie, times(1)).delete("anton");
        assertEquals(0, prefixMatches.size());
    }

    @Test
    public void getWordsStartWithPrefix(){

        Trie trie = spy(new RWayTrie());
        PrefixMatches prefixMatches = new PrefixMatches();
        prefixMatches.setTrie(trie);

        prefixMatches.add("anton");
        prefixMatches.add("ant");
        prefixMatches.add("babak");
        prefixMatches.add("back");

        Iterator<String> iterator = prefixMatches.wordsWithPrefix("an", 5).iterator();

        int actual = 0;
        while(iterator.hasNext()){
            iterator.next();
            actual++;
        }

        assertEquals(2, actual);
    }

    @Test(expected = NullPointerException.class)
    public void wordsStartWithNullPrefixShouldRiseException(){
        PrefixMatches prefixMatches = mock(PrefixMatches.class);
        doThrow(new NullPointerException()).when(prefixMatches).wordsWithPrefix(null);
        prefixMatches.wordsWithPrefix(null);
    }

    @Test(expected = FileNotFoundException.class)
    public void inputWordsFromNonexistentFileShouldRiseException() throws FileNotFoundException {
        PrefixMatches prefixMatches = mock(PrefixMatches.class);
        doThrow(new FileNotFoundException()).when(prefixMatches).addWordsFromFile("any");
        prefixMatches.addWordsFromFile("any");
    }

    @Test(expected = NullPointerException.class)
    public void inputWordsFromFileShouldRiseException() throws FileNotFoundException {
        prefixMatches = mock(PrefixMatches.class);
        doThrow(new NullPointerException()).when(prefixMatches).addWordsFromFile(null);
        prefixMatches.addWordsFromFile(null);
    }

    @Test
    public void inputWordsFromFile() throws FileNotFoundException {
        int addedWords = prefixMatches.addWordsFromFile("test.txt");
        verify(trie, times(4)).add(any(Tuple.class));
        assertEquals(4, addedWords);

    }

}
