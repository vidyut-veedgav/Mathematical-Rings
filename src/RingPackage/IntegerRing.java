package RingPackage;

import java.util.Objects;

/**
 * a class to demonstrate the concept of Rings on Integers
 * @author Vidyut Veedgav
 */
public class IntegerRing implements Ring<Integer>{

    /**
     * Returns the zero property of data type Integer
     * @return zero of type Integer
     */
    @Override
    public Integer zero() {
        return 0;
    }

    /**
     * Returns the multiplicative identity property of data type Integer such that a * 1 = a
     * @return the identity of type Integer
     */
    @Override
    public Integer identity() {
        return 1;
    }

    /**
     * Returns the sum of two objects of type Integer
     * @param x the first addend
     * @param y the second addend
     * @return a new Integer object representing the sum of x and y
     */
    @Override
    public Integer sum(Integer x, Integer y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x + y;
    }

    /**
     * Returns the product of two objects of type Integer
     * @param x the first factor
     * @param y the second factor
     * @return a new Integer object representing the product of x and y
     */
    @Override
    public Integer product(Integer x, Integer y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        
        return x * y;
    }
}