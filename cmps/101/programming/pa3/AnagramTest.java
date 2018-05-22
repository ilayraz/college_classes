class AnagramTest {
    public static void main(String[] args) {
        // Test creating an anagram from String
        Anagram t1 = new Anagram("iTeMs");
        Anagram t2 = new Anagram("cat");

        // Test creating an anagram from char[]
        Anagram t3 = new Anagram("emits".toCharArray());

        // Test printing
        t1.print();
        t2.print();
        t2.print();

        //Test comparisons. Also uses a method that returns the word part of an anagram variable
        System.out.println(t1.toString() + " is an anagram of " + t3.toString() + "? " + t1.equals(t3));
        System.out.println(t1.toString() + " is an anagram of " + t2.toString() + "? " + t1.equals(t2));
    }
}
