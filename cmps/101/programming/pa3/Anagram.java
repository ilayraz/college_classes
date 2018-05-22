import java.util.*;

class Anagram {
    public final String value;
    protected final int code;

    // Map from character to prime number. Order was choosing by calculating letter frequency in the text list.
    private static final Map<Character, Integer> codeMap = new HashMap<Character, Integer>();
    static {
        codeMap.put('e',2);
        codeMap.put('s',3);
        codeMap.put('i',5);
        codeMap.put('a',7);
        codeMap.put('r',11);
        codeMap.put('n',13);
        codeMap.put('t',17);
        codeMap.put('o',19);
        codeMap.put('l',23);
        codeMap.put('c',29);
        codeMap.put('d',31);
        codeMap.put('u',37);
        codeMap.put('p',41);
        codeMap.put('m',43);
        codeMap.put('g',47);
        codeMap.put('h',53);
        codeMap.put('b',59);
        codeMap.put('y',61);
        codeMap.put('f',67);
        codeMap.put('v',71);
        codeMap.put('k',73);
        codeMap.put('w',79);
        codeMap.put('z',83);
        codeMap.put('x',89);
        codeMap.put('q',97);
        codeMap.put('j',101);
    }

    public Anagram(String value) {
        this.value = value;
        this.code = this.hash(value.toLowerCase());
    }

    public Anagram(char[] value) {
        this(String.valueOf(value));
    }

    /*
      Calculates a unique anagram hash by using the prime factorization theorm. Multiplies together the prime maping for each letter to create a value that is unique for every anagram.
     */
    private static int hash(String value) {
        int sum = 1;
        for (char chr : value.toCharArray())
            sum *= codeMap.get(chr);
        return sum;
    }

    public void print() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Anagram))
            return false;
        return this.code == ((Anagram)obj).code;
    }
}
