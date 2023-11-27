package RingPackage;

import java.util.Objects;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on Doubles
 */
public class DoubleRing implements Ring<Double>{

    /**
     * overriding the zero method
     */
    @Override
    public Double zero() {
        return Double.valueOf(0.0);
    }

    /**
     * overriding the identity method
     */
    @Override
    public Double identity() {
        return Double.valueOf(1.0);
    }

    /**
     * overriding the sum method
     */
    @Override
    public Double sum(Double x, Double y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return Double.valueOf(x + y);
    }

    /**
     * overriding the product
     */
    @Override
    public Double product(Double x, Double y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        
        return Double.valueOf(x * y);
    }
}
