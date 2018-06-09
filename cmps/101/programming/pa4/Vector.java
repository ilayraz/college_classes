// Vector.java
// A class that implements the Vector ADT.
//
// For this assignment, you must complete this code skeleton.
// You may not change the function prototypes.
// You are expected to fill in the functions to make them work
// as expected, and you can add as much as you need or want.
// We recommend implementing the Vector ADT using x and y coordinates.

// Notes:
// Angles are always in radians, not degrees.

import java.util.Arrays;

class Vector {

    // Fields
    private float[] elements;

    // Constructors

    // The default constructor should create a new Vector with no magnitude.
    public Vector() {
        // create a new 2-tuple initialized to 0.
        this.elements = new float[2];
    }

    // This constructor takes an x and a y coordinate for the Vector.
    public Vector(float x, float y) {
        this.elements = new float[2];
        this.elements[0] = x;
        this.elements[1] = y;
    }

    // Creates a nth-dimensional Vector from an array of floats
    public Vector(float[] vector) {
        this.elements = vector.clone();
    }

    // Creates a nth-dimensional Vector
    public Vector(int n) {
        this.elements = new float[n];
    }

    // Creates a clone of an existing vector
    public Vector(Vector v) {
        this.elements = v.elements.clone();
    }

    // This "constructor" takes an angle and a magnitude for the Vector.
    // It is not a traditional constructor because only one function can have
    //   the signature Vector(float, float).
    public static Vector polarVector(float angle, float magnitude) {
        float x, y;
        x = magnitude * (float)Math.cos(angle);
        y = magnitude * (float)Math.sin(angle);
        return new Vector(x, y);
    }

    // Creates a 3-dimensional Vector from a cylindrical coordinate
    public static Vector cylindricalVector(float radial, float azimuth, float height) {
        Vector v = new Vector(3);
        v.elements[0] = radial * (float)Math.cos(azimuth);
        v.elements[1] = radial * (float)Math.sin(azimuth);
        v.elements[2] = height;
        return v;
    }

    // Create a 3-dimensional Vector from spherical coordinate
    public static Vector sphericalVector(float radial, float polar, float azimuth) {
        Vector v = new Vector(3);
        v.elements[0] = radial * (float)Math.cos(polar) * (float)Math.sin(azimuth);
        v.elements[1] = radial * (float)Math.sin(polar) * (float)Math.sin(azimuth);
        v.elements[2] = radial * (float)Math.cos(azimuth);
        return v;
    }


    // Transforms a vector into an n-th dimensional one.
    public Vector toNthDimension(int n) {
        Vector v = new Vector(n);
        for (int i = 0; i < (float)Math.min(n, this.length()); i++)
            v.elements[i] = this.getNthElement(i);
        return v;
    }

    // Access functions

    // Return the first coordinate from the vector
    public float getX() {
        return this.elements[0];
    }

    // Return the second coorinate from the vector
    public float getY() {
        return this.elements[1];
    }

    // Return the nth coorinate from the vector
    public float getNthElement(int n) {
        return this.elements[n];
    }

    // Return the length of the vector
    public int length() {
        return this.elements.length;
    }

    // Return angle of vector. Only works on 2-vectors
    public float getAngle() throws Exception {
        // If vector is not a 2-vector, error
        if (this.length() != 2)
            throw new Exception("getAngle only works with a 2-tuple. " + this.length() + "-tuple given.");

        return (float)Math.atan2(this.getNthElement(0), this.getNthElement(1));
    }

    // Returns the magnitude of the Vector.
    public float getMagnitude() {
        double sum = 0;
        for (double e : this.elements)
            sum += e * e;
        return (float)Math.sqrt(sum);
    }

    // Returns the sum of this Vector with the given Vector.
    public Vector add(Vector other) throws Exception {
        // If both vectors don't have the same length, throw an error
        if (this.length() != other.length())
            throw new Exception("Can't add " + this.length() + "-dimensional vector and " + other.length() + "-dimensional vector.");

        Vector v = new Vector(this.length());
        for (int i = 0; i < v.length(); i++)
            v.elements[i] = this.getNthElement(i) + other.getNthElement(i);
        return v;
    }

    // Returns the difference between this Vector and the given Vector.
    public Vector subtract(Vector other) throws Exception {
        // If both vectors don't have the same length, throw an error
        if (this.length() != other.length())
            throw new Exception("Can't subtract " + this.length() + "-dimensional vector and " + other.length() + "-dimensional vector.");

        Vector v = new Vector(this.length());
        for (int i = 0; i < v.length(); i++)
            v.elements[i] = this.getNthElement(i) - other.getNthElement(i);
        return v;
    }

    // Returns the dot product of this Vector and the given Vector.
    public float dotProduct(Vector other) throws Exception {
        // If both vectors don't have the same length, throw an error
        if (this.length() != other.length())
            throw new Exception("Can't subtract " + this.length() + "-dimensional vector and " + other.length() + "-dimensional vector.");

        float value = 0;
        for (int i = 0; i < this.length(); i++)
            value += this.getNthElement(i) * other.getNthElement(i);
        return value;
    }

    // Returns this Vector scaled by the given scalar.
    public Vector scalarMultiply(float scalar) {
        Vector v = new Vector(this);
        for (int i = 0; i < v.length(); i++)
            v.elements[i] *= scalar;
        return v;
    }

    // Returns the normalized version of this Vector, a Vector with the same
    public Vector normalize() {
        float magnitude = this.getMagnitude();
        Vector v = new Vector(this);

        for (int i = 0; i < v.length(); i++)
            v.elements[i] /= magnitude;
        return v;
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == null)
            return false;
        if (!Vector.class.isAssignableFrom(obj.getClass()))
            return false;

        return Arrays.equals(this.elements, ((Vector)obj).elements);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.elements);
    }

    public void print() {
        System.out.println(this);
    }
}
