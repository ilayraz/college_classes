/*
  CREATED: 4/20/18
  Test file for apint and aprat
 */
import java.io.*;

class Test {
    public static void main(String[] args) throws FileNotFoundException {
        { //apint tests
            apint temp;
            // default constructor
            assert(new apint().compareTo(apint.ZERO) == 0);

            // string constructor
            System.out.println("----------apints from strings----------");
            apint t1 = new apint("12345678901234567890");
            apint t2 = new apint("-12345678901234567890");
            t1.print();
            t2.print();

            // ints constructor
            System.out.println("----------apints from ints----------");
            apint t3 = new apint(100);
            apint t4 = new apint(-100);
            t3.print();
            t4.print();

            // double constructor
            System.out.println("----------apints from doubles----------");
            apint t5 = new apint(1234567.1234554);
            apint t6 = new apint(-1234567.1234554);
            t5.print();
            t6.print();

            System.out.println("----------operations----------");

            // Addition
            System.out.print(t1.toString() + " + " + t2.toString() + " = ");
            t1.add(t2).print();

            // Subtraction
            System.out.print(t1.toString() + " - " + t2.toString() + " = ");
            temp = t1.subtract(t2);
            temp.print();
            assert(temp.compareTo(new apint("24691357802469135780")) == 0);

            // Multiplication
            System.out.print(t1.toString() + " * " + t2.toString() + " = ");
            temp = t1.multiply(t2);
            temp.print();
            assert(temp.compareTo(new apint("-152415787532388367501905199875019052100")) == 0);

            // Division
            apint d1 = new apint("1234213412432142213");
            apint d2 = new apint("40442705098576436035584");
            System.out.print(d2.toString() + " / " + d1.toString() + " = ");
            temp = d2.divide(d1);
            temp.print();
            assert(temp.compareTo(new apint("32768")) == 0);
        }

        { // aprat tests
            System.out.println();
            System.out.println("--------------------Moving to aprats--------------------");


            aprat temp;

            // default constructor
            assert(new aprat().compareTo(new aprat(0,1)) == 0);

            // apints constructor
            System.out.println("----------aprats from apints----------");
            aprat t1 = new aprat(new apint("12345678901234567890"), new apint("1234567890"));
            aprat t2 = new aprat(new apint("-1234567890"), new apint("12345678901234567890"));
            t1.print();
            t2.print();

            // ints constructor
            System.out.println("----------aprats from ints----------");
            aprat t3 = new aprat(7654,1234);
            aprat t4 = new aprat(1234,7654);
            t3.print();
            t4.print();

            // doubles constructor
            System.out.println("----------aprats from doubles----------");
            aprat t5 = new aprat(15.222, 2);
            aprat t6 = new aprat(234.000025, 6);
            t5.print();
            t6.print();

            System.out.println("----------operations----------");

            // Addition
            System.out.print(t3.toString() + " + " + t4.toString() + " = ");
            temp = t3.add(t4);
            temp.print();
            assert(temp.compareTo(new aprat(15026618,2361259)) == 0);

            // Subtraction
            System.out.print(t3.toString() + " - " + t4.toString() + " = ");
            temp = t3.subtract(t4);
            temp.print();
            assert(temp.compareTo(new aprat(14265240,2361259)) == 0);

            // Multiplication
            System.out.print(t3.toString() + " * " + t4.toString() + " = ");
            temp = t3.multiply(t4);
            temp.print();
            assert(temp.compareTo(new aprat(1,1)) == 0);

            // Division
            System.out.print(t3.toString() + " / " + t4.toString() + " = ");
            temp = t3.divide(t4);
            temp.print();
            assert(temp.compareTo(new aprat(14645929, 380689)) == 0);
        }

        { // EXTRA CREDIT
            System.out.println("--------------------EXTRA CREDIT--------------------");
            System.out.println("Printing 1000! to factorial.txt");

            apint factorial = new apint(1);
            for (int i = 1; i <= 1000; i++)
                factorial = factorial.multiply(new apint(i));

            PrintWriter writer = new PrintWriter("factorial.txt");
            writer.println(factorial);
            writer.close();
        }
    }
}
