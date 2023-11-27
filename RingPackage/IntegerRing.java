package RingPackage;

import java.util.Objects;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on Integers
 */
public class IntegerRing implements Ring<Integer>{

    /**
     * overriding the zero method
     */
    @Override
    public Integer zero() {
        return 0;
    }

    /**
     * overriding the identity method
     */
    @Override
    public Integer identity() {
        return 1;
    }

    /**
     * overriding the sum method
     */
    @Override
    public Integer sum(Integer x, Integer y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x + y;
    }

    /**
     * overriding the product method
     */
    @Override
    public Integer product(Integer x, Integer y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        
        return x * y;
    }
}