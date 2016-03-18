import vocabulary.PrefixMatches;

import java.io.FileNotFoundException;
import java.util.Iterator;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PrefixMatches matches = new PrefixMatches();
        matches.addWordsFromFile("words.txt");
        Iterator<String> iterator = matches.wordsWithPrefix("go", 5).iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next() + " ");
        }
    }

}
