package RingPackage;

import java.util.Objects;

/**
 * This is a class to demonstrate the concept of Rings on Doubles
 * @author Vidyut Veedgav
 */
public class DoubleRing implements Ring<Double>{

    /**
     * Returns the zero property of data type Double
     * @return zero of type Double
     */
    @Override
    public Double zero() {
        return Double.valueOf(0.0);
    }

    /**
     * Returns the multiplicative identity property of data type Double
     * @return the identity of type Double
     */
    @Override
    public Double identity() {
        return Double.valueOf(1.0);
    }

    /**
     * Returns the sum of two objects of type Double
     * @param x the first addend
     * @param y the second addend
     * @return a new Double object representing the sum of x and y
     */
    @Override
    public Double sum(Double x, Double y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return Double.valueOf(x + y);
    }

    /**
     * Returns the product of two objects of type Double
     * @param x the first factor
     * @param y the second factor
     * @return a new Double object representing the product of x and y
     */
    @Override
    public Double product(Double x, Double y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        
        return Double.valueOf(x * y);
    }
}
