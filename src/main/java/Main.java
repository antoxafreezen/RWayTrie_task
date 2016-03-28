import vocabulary.PrefixMatches;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        PrefixMatches matches = new PrefixMatches();
        matches.addWordsFromFile("words.txt");
        Iterator<String> iterator = matches.wordsWithPrefix("go", 3).iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
    }

}
