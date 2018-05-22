// Ilay Raz
// ilraz
// CMPS12B-02
// 2/6/18
// DictionaryTest.java

public class DictionaryTest {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();

        System.out.println("empty: " + dict.isEmpty());
        System.out.println("size: " + dict.size());
        dict.insert("a", "1");
        dict.insert("b", "2");
        dict.insert("c", "3");
        dict.insert("d", "4");
        System.out.println(dict);
        System.out.println("empty: " + dict.isEmpty());
        System.out.println("size: " + dict.size());
        // System.out.println(dict.lookup("b"));
        // dict.insert("b", "3");
        // dict.delete("a");
        dict.delete("c");
        // System.out.println(dict);
        // dict.delete("k");
        // System.out.println(dict.lookup("d"));
        // dict.delete("b");
        // dict.delete("d");
        dict.delete("a");
        dict.insert("k", "7");
        dict.insert("a", "a");
        System.out.println(dict);
        System.out.println("empty: " + dict.isEmpty());
        System.out.println("size: " + dict.size());
        dict.makeEmpty();
        System.out.println("empty: " + dict.isEmpty());
        System.out.println("size: " + dict.size());
    }
}
