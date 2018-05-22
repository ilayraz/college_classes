/*
  CREATED: Ilay Raz 4/20/18
  apint class file
 */
import java.util.*;

class apint {
    // Declare some constants
    public static final apint ZERO = new apint(0);
    public static final apint ONE = new apint(1);

    private ArrayList<Byte> data; // Each byte stores one digit
    private boolean isNeg; // Stores sign

    /*
       Creates a new apint initalized to value 0
       Input: null
       Output: apint object
    */
    public apint() {
        this.data = new ArrayList<Byte>();
        this.addDigit((byte)0);
        this.isNeg = false;
    }

    /*
      Creates a new apint from an integer
      Input: int
      Output: apint object
    */
    public apint(int num) {
        this.data = new ArrayList<Byte>();
        this.isNeg = Integer.signum(num) == -1;
        if (num == 0)
            this.addDigit((byte)0);
        else {
            num = this.isNeg ? num*-1 : num;
            while (num != 0) {
                this.addDigit((byte)(num % 10));
                num /= 10;
            }
        }
    }

    /*
      Creates a new apint from a double
      Input: double
      Output: apint object
    */
    public apint(double num) {
        this(String.format("%.0f", num));
    }

    /*
      Creates a new apint from a string formatted according to regex "[+-]?[0-9]+"
      Input: string formatted according to regex "[+-]?[0-9]+"
      Output: apint object
    */
    public apint(String num) {
        this.data = new ArrayList<Byte>(num.length());

        int endPoint = Character.isDigit(num.charAt(0)) ? 0 : 1;
        for (int i = num.length() - 1; i >= endPoint; i--)
            this.addDigit((byte)Character.digit(num.charAt(i), 10));

        if (this.digitCount() == 1 && this.getDigit(0) == (byte)0)
            this.isNeg = false;
        else
            this.isNeg = num.charAt(0) == '-' ? true : false;
    }

    /*
      Clones an existing apint
      Input: apint object
      Output: apint object
    */
    public apint(apint num) {
        this.data = new ArrayList<Byte>(num.data);
        this.isNeg = num.isNeg;
    }

    /*
      Adds 2 apints together and returns the result. Does not modify the original apint.
      Input: apint to be added
      Output: apint resulted
     */
    public apint add(apint num) {

        // Alias this and num in order to not have to check sign and length. Set ret=bigger number
        apint ret, other;
        boolean thisSign = this.getSign();
        boolean numSign = num.getSign();
        this.setSign(false);
        num.setSign(false);
        if (this.compareTo(num) >= 0) {
            ret = new apint(this);
            other = num;
            ret.setSign(thisSign);
            other.setSign(numSign);
        } else {
            ret = new apint(num);
            other = this;
            ret.setSign(numSign);
            other.setSign(thisSign);
        }
        this.setSign(thisSign);
        num.setSign(numSign);

        byte carry = 0;

        // If both have the same sign, add with carry
        if (ret.isNeg == other.isNeg) {
            for (int i = 0; i < ret.digitCount(); i++) {
                if (i >= other.digitCount())
                    carry += ret.getDigit(i);
                else
                    carry += ret.getDigit(i) + other.getDigit(i);
                ret.setDigit(i, (byte)(carry % 10));
                carry /= 10;
            }
            if (carry != 0)
                ret.addDigit(carry);
        } else {
            // If both have different signs, subtract with borrowing (don't have to worry about signs because ret is always the bigger number
            for (int i = 0; i < ret.digitCount(); i++) {
                if (i >= other.digitCount())
                    carry += ret.getDigit(i);
                else
                    carry += ret.getDigit(i) - other.getDigit(i);
                if (carry < 0) {
                    ret.setDigit(i,(byte)(carry + 10));
                    carry = -1;
                } else {
                    ret.setDigit(i, carry);
                    carry = 0;
                }
            }
        }

        return ret;
    }

    /*
      Multiply two apints together
      Input: apint
      Output: apint whose value is (this*num)
     */
    public apint multiply(apint num) {
        apint ret, temp, me, other;
        byte carry, currentDigit;

        // Set me to the apint that has the most digits
        if (this.digitCount() > num.digitCount()) {
            me = this;
            other = num;
        } else {
            me = num;
            other = this;
        }

        // Initialize variables
        ret = new apint();
        temp = new apint();
        ret.data.ensureCapacity(me.digitCount() + other.digitCount());
        temp.data.ensureCapacity(me.digitCount() + other.digitCount());

        for (int i = 0; i < other.digitCount(); i++) {
            carry = 0;
            temp.data.clear();
            currentDigit = other.getDigit(i);

            // Ensure significant digit placment is corrent
            for (int j = 0; j < i; j++)
                temp.addDigit((byte)0);

            // Multiply
            for (int j = 0; j < me.digitCount(); j++) {
                carry += currentDigit * me.getDigit(j);
                temp.addDigit((byte)(carry % 10));
                carry /= 10;
            }
            if (carry != 0)
                temp.addDigit((byte)carry);

            // Add intermidiate result
            ret = ret.add(temp);
        }

        // Set the sign of the new number. Ensure that 0 is always positive
        if (ret.digitCount() == 1 && ret.getDigit(0) == (byte)0)
            ret.isNeg = false;
        else
            ret.isNeg = other.isNeg ^ me.isNeg;
        return ret;
    }

    /*
      Subtracts two apints by calling add on the negative of the argument.
      Input: apint
      Output: apint
     */
    public apint subtract(apint num) {
        apint temp = new apint(num);
        temp.isNeg = !temp.isNeg;
        return this.add(temp);
    }

    /*
      Divides this by num using the subtraction method and returns an apint array [div, remainder]
      Input: apint
      Output: apint[] = {div, remainder}
    */
    public apint[] divmod(apint num) throws ArithmeticException {
        if (num.compareTo(apint.ZERO) == 0)
            throw new ArithmeticException("Division by 0");

        apint remainder = new apint(this);
        apint numPositive = new apint(num);
        remainder.isNeg = numPositive.isNeg = false;
        apint quotient = new apint();
        ArrayDeque<Byte> Dquotient = new ArrayDeque<Byte>();
        apint temp = new apint();
        byte counter = 0;
        temp.data.ensureCapacity(num.digitCount());

        for (int i = this.digitCount() - num.digitCount(); i >= 0; i--) {
            temp.data.clear();
            counter = 0;

            // Align numbers
            for (int j = 0; j < i; j++)
                temp.addDigit((byte)0);
            temp.data.addAll(numPositive.data);

            // Subtract
            while (remainder.compareTo(temp) >= 0) {
                remainder = remainder.subtract(temp);
                counter++;
            }
            Dquotient.addFirst(counter);
        }

        /*while (remainder.compareTo(numPositive) >= 0) {
            ret = ret.add(apint.ONE);
            remainder = remainder.subtract(numPositive);
            }*/

        quotient.data = new ArrayList<Byte>(Dquotient);

        if (quotient.compareTo(apint.ZERO) != 0)
            quotient.isNeg = this.isNeg ^ num.isNeg;

        apint[] retArray = {quotient, remainder};
        return retArray;
    }

    /*
      Divides this by num. Throws an error if modulus is not 0
      Input: apint
      Output: apint
     */
    public apint divide(apint num) throws ArithmeticException {
        apint[] temp = this.divmod(num);

        if (temp[1].compareTo(apint.ZERO) != 0)
            throw new ArithmeticException("Numbers are not divisible");
        return temp[0];
    }

    /*
      Find modulus of this and num.
      Input: apint
      Output: apint
     */
    public apint modulus(apint num) {
        return this.divmod(num)[1];
    }

    /*
      Compares this to num.
      Input: apint
      Output: -1 if this < num, 0 if this == num, 1 if this > num
     */
    public int compareTo(apint num) {
        if (this.isNeg != num.isNeg)
            return this.isNeg ? -1 : 1;
        if (this.digitCount() != num.digitCount()) {
            if (this.isNeg)
                return num.digitCount() > this.digitCount() ? 1 : -1;
            return this.digitCount() > num.digitCount() ? 1 : -1;
        }
        for (int i = this.digitCount()-1; i >= 0; i--)
            if (this.getDigit(i) != num.getDigit(i))
                return this.getDigit(i) > num.getDigit(i) ? 1 : -1;
        return 0;
    }

    /*
      Returns string representation of apint.
      Input: none
      Output: String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(this.digitCount() + 1);
        for (int i = 0; i < this.digitCount(); i++)
            builder.append(this.getDigit(i));
        builder.append(this.isNeg ? "-" : "");
        return builder.reverse().toString();
    }

    /*
      Returns the absolute value of the apint.
      Input: none
      Output: apint
     */
    public apint abs() {
        apint temp = new apint(this);
        temp.isNeg = false;
        return temp;
    }

    /*
      Returns the sign of the apint. true = negative, false = positive.
      Input: none
      Output: boolean
    */
    public boolean getSign() {
        return this.isNeg;
    }

    public void setSign(boolean sign) {
        this.isNeg = sign;
    }

    public void print() {
        System.out.println(this.toString());
    }

    /*
      Returns number of digits
      Input: none
      Output: int
     */
    private int digitCount() {
        int max;
        for (max = this.data.size(); max > 1 && this.getDigit(max-1) == (byte)0; max--);
        return max;
    }

    /*
      Get a digit at specified index
      Input: int
      Output: byte
     */
    private byte getDigit(int i) {
        return this.data.get(i);
    }

    /*
      Appends a digit
      Input: byte
      Output: none
     */
    private void addDigit(byte val) {
        this.data.add(val);
    }

    /*
      Sets a digit at index to value
      Input: int index, byte value
      Output: none
     */
    private void setDigit(int i, byte val) {
        this.data.set(i, val);
    }
}
