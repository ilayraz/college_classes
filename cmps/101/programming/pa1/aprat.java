/*
  CREATED: Ilay Raz 4/20/18
  aprat class file
 */
class aprat {
    private apint numerator;
    private apint denominator;
    private boolean isNeg;

    /*
      Creates an aprat initialized to value of 0
      Input: none
      Output: new aprat
    */
    public aprat() {
        this.numerator = new apint(0);
        this.denominator = new apint(1);
        this.isNeg = false;
    }

    /*
      Creates a clone of an existing aprat
      Input: aprat
      Output: new aprat
     */
    public aprat(aprat myAprat) {
        this.numerator = new apint(myAprat.numerator);
        this.denominator = new apint(myAprat.denominator);
        this.isNeg = myAprat.isNeg;
    }

    /*
      Creates a normalized aprat from the given numerator and denominator
      Input: int numerator, int denominator
      Output: new aprat
     */
    public aprat(int myNumerator, int myDenominator) {
        this.numerator = new apint(Math.abs(myNumerator));
        this.denominator = new apint(Math.abs(myDenominator));
        this.isNeg = (Integer.signum(myNumerator) * Integer.signum(myDenominator)) == -1;
        this.normalize();
    }

    public aprat(apint myNumerator, apint myDenominator) {
        this.numerator = new apint(myNumerator);
        this.denominator = new apint(myDenominator);
        this.numerator.setSign(false);
        this.denominator.setSign(false);
        this.isNeg = myNumerator.getSign() ^ myDenominator.getSign();
        this.normalize();
    }

    aprat(double num, int precision) {
        double fractional = num % 1;
        double integral = num - fractional;
        String Sfractional = String.format(String.format("%%.%df", precision), fractional);
        aprat temp = new aprat(new apint(Sfractional.substring(2)), new apint(Math.pow(10, Sfractional.length() - 2)));
        temp = temp.add(new aprat(new apint(integral), new apint(1)));

        this.numerator = temp.numerator;
        this.denominator = temp.denominator;
        this.isNeg = Math.signum(num) == -1;

    }


    /*
      Adds two aprats together.
      Input: aprat
      Output: aprat
     */
    public aprat add(aprat num) {
        aprat ret = new aprat();

        // Create new aprats in order to not modify the originals
        aprat t1 = new aprat(this);
        aprat t2 = new aprat(num);

        // Set the appropriate signs on the numerators
        this.numerator.setSign(this.isNeg);
        num.numerator.setSign(num.isNeg);

        ret.denominator = this.denominator.multiply(num.denominator);
        ret.numerator = this.numerator.multiply(num.denominator).add(num.numerator.multiply(this.denominator));

        // Return numerators to normal
        this.numerator.setSign(false);
        num.numerator.setSign(false);

        // Set the sign to be equal to the numerator
        ret.isNeg = ret.numerator.getSign();
        ret.numerator.setSign(false);

        ret.normalize();
        return ret;
    }

    /*
      Subtracts num from this by flipping sign and calling add
      Input: aprat
      Output: aprat
    */
    public aprat subtract(aprat num) {
        num.isNeg = !num.isNeg;

        aprat ret = this.add(num);

        num.isNeg = !num.isNeg;

        return ret;
    }

    /*
      Multiplies this by num.
      Input: aprat
      Output: aprat
     */
    public aprat multiply(aprat num) {
        aprat ret = new aprat();

        // Set numerators' signs correctly
        this.numerator.setSign(this.isNeg);
        num.numerator.setSign(num.isNeg);

        ret.numerator = this.numerator.multiply(num.numerator);
        ret.denominator = this.denominator.multiply(num.denominator);

        // Set ret's sign
        ret.isNeg = ret.numerator.getSign();
        ret.numerator.setSign(false);

        ret.normalize();
        return ret;
    }

    /*
      Divides this by num by multiplying this by the inverse of num
      Input: aprat
      Output: aprat
     */
    public aprat divide(aprat num) {
        aprat temp = new aprat();
        temp.numerator = num.denominator;
        temp.denominator = num.numerator;
        return this.multiply(temp);
    }

    public String toString() {
        this.numerator.setSign(this.isNeg);
        String ret =  this.numerator.toString() + "/" + this.denominator.toString();
        this.numerator.setSign(false);
        return ret;
    }

    public void print() {
        System.out.println(this.toString());
    }

    /*
      Compares two aprats by first comparing signs and then cross multiplying and calling apint's compareTo method.
      Input: aprat
      Output: int (1 if this > num. 0 if this == num. -1 if num > this)
     */
    public int compareTo(aprat num) {
        if (this.isNeg != num.isNeg)
            return this.isNeg ? -1 : 1;
        return this.numerator.multiply(num.denominator).compareTo(num.numerator.multiply(this.denominator));
    }

    /* Normalizes the fraction */
    private void normalize() {
        apint gcd;
        if (this.numerator.compareTo(this.denominator) >= 0)
            gcd = aprat.gcd(this.numerator, this.denominator);
        else
            gcd = aprat.gcd(this.denominator, this.numerator);

        this.numerator = this.numerator.divide(gcd);
        this.denominator = this.denominator.divide(gcd);
    }

    /*
      Finds the gcd of two apints by Euclid's algorithm
      Input: apint a, apint b, where a >= b
      Output: apint
     */
    private static apint gcd(apint a, apint b) {
        if (b.compareTo(apint.ZERO) == 0)
            return a;
        return gcd(b, a.modulus(b));
    }
}
