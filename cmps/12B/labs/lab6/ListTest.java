// Ilay Raz
// ilraz
// CMPS12M-02
// 2/9/18
// ListTest.java

class ListTest {
    public static void main(String[] args) {
        List<int> A = new List<int>();
        List<String> B = new List<String>();

        assert(A.isEmpty() && B.isEmpty());
        assert(A.size() == 0 && B.size() == 0);
        A.add(1, 5); B.add(1, "test");
        assert(!A.isEmpty() && !B.isEmpty());
        assert(A.size() == 1 && B.size() == 1);
        assert(A.get(1) == 5 && B.get(1) == "test");
        A.add(2, 6); B.add(2, "cat");
        System.out.printf(A);
        System.out.printf("-----------------------------");
        System.out.printf(B);
        A.remove(2);
        B.remove(1);
        assert(A.size() == 1 && B.size() == 1);
        A.removeAll();
        assert(A.size == 0 && A.isEmpty());
        System.out.printf(A);
    }
}
