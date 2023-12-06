package RingPackage;

import java.math.BigInteger;
import java.util.Objects;

/**
 * This is a class to demonstrate the concept of Rings on BigIntegers
 * @author Vidyut Veedgav
 */
public class BigIntegerRing implements Ring<BigInteger> {

    /**
     * Returns the zero property of data type BigInteger
     * @return zero of type BigInteger
     */
    @Override
    public BigInteger zero() {
        return BigInteger.ZERO;
    }

    /**
     * Returns the multiplicative identity property of data type BigInteger such that a * 1 = a
     * @return the identity of type BigInteger
     */
    @Override
    public BigInteger identity() {
        return BigInteger.ONE;
    }

    /**
     * Returns the sum of two objects of type BigInteger
     * @param x the first addend
     * @param y the second addend
     * @return a new BigInteger object representing the sum of x and y
     */
    @Override
    public BigInteger sum(BigInteger x, BigInteger y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x.add(y);
    }

    /**
     * Returns the product of two objects of type BigInteger
     * @param x the first factor
     * @param y the second factor
     * @return a new BigInteger object representing the product of x and y
     */
    @Override
    public BigInteger product(BigInteger x, BigInteger y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        
        return x.multiply(y);
    }
}



