import java.util.*;

class AnagramMap {
    /*
      A map strucuture is used to store the anagrams. Maps from Integer to List of anagrams.
     */
    private Map<Integer, List<Anagram>> map;
    private static List<Anagram> emptyList = new ArrayList<Anagram>();

    public AnagramMap() {
        this.map = new HashMap<Integer, List<Anagram>>();
    }

    /*
      Adds an Anagram to the map
     */
    public void addAnagram(Anagram anagram) {
        this.map.putIfAbsent(anagram.code, new ArrayList<Anagram>());
        this.map.get(anagram.code).add(anagram);
    }

    /*
      Prints all anagrams of an Anagram (not including itself)
     */
    public void printAnagrams(Anagram anagram) {
        for (Anagram an : this.map.getOrDefault(anagram.code, emptyList))
            if (!an.value.equals(anagram.value))
                an.print();
    }
}
