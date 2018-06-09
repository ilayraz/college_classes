class VectorTest {
    public static void main(String[] args) throws Exception {
        // Test each one of the constructors
        System.out.println("Testing constructors");
        System.out.println("Testing empty constructor");
        new Vector().print();
        System.out.println("Testing (x,y) constructor");
        new Vector(5, 7).print();
        System.out.println("Testing n-element constructor");
        new Vector(32).print();
        System.out.println("Testing clone constructor");
        new Vector(new Vector()).print();
        System.out.println("Testing polar constructor");
        // Should result in (0, 5)
        Vector.polarVector((float)Math.PI/2, 5).print();
        System.out.println("Testing cylidrical constructor");
        // Should result in (0, 5, 7)
        Vector.cylindricalVector(5, (float)Math.PI/2, 7).print();
        System.out.println("Testing spherical constructor");
        // Should result in (-5, 0, 0)
        Vector.sphericalVector(5, (float)Math.PI, (float)Math.PI / 2).print();

        // Test getters
        System.out.println("Testing getX function");
        System.out.println(new Vector(5, 3).getX());
        System.out.println("Testing getY function");
        System.out.println(new Vector(5, 3).getY());
        System.out.println("Testing getNthElement function");
        System.out.println(new Vector(5,3).getNthElement(1));
        System.out.println("Testing length function");
        System.out.println(new Vector(17).length());
        System.out.println("Testing getAngle function");
        System.out.println(Vector.polarVector((float)Math.PI/2, 5).getAngle());
        System.out.println("Testing getMagnitude function");
        System.out.println(new Vector(5, 5).getMagnitude());

        // Test functions
        System.out.println("Testing add");
        new Vector(5, 7).add(new Vector(5, 3)).print();
        System.out.println("Testing subtract function");
        new Vector(5, 3).subtract(new Vector(-5, -7)).print();
        System.out.println("Testing dot product function");
        System.out.print("(5,7) . (2, -5) = ");
        System.out.println(new Vector(5, 3).dotProduct(new Vector(4, -5)));
        System.out.println("Testing scalar multiply function");
        System.out.print("3*(5,7) = ");
        System.out.println(new Vector(5,7).scalarMultiply(3));
        System.out.println("Testing normalize function");
        new Vector(5, 5).normalize().print();
    }
}
