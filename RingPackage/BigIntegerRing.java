package RingPackage;

import java.math.BigInteger;
import java.util.Objects;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on BigIntegers
 */
public class BigIntegerRing implements Ring<BigInteger> {

    /**
     * overriding the zero method
     */
    @Override
    public BigInteger zero() {
        return BigInteger.ZERO;
    }

    /**
     * overridng the identity
     */
    @Override
    public BigInteger identity() {
        return BigInteger.ONE;
    }

    /**
     * overriding the sum method
     */
    @Override
    public BigInteger sum(BigInteger x, BigInteger y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x.add(y);
    }

    /**
     * overriding the product method 
     */
    @Override
    public BigInteger product(BigInteger x, BigInteger y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        
        return x.multiply(y);
    }
}



