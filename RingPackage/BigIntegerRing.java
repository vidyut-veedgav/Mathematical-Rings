package RingPackage;

import java.math.BigInteger;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on BigIntegers
 */
public class BigIntegerRing implements Ring<BigInteger> {

    @Override
    public BigInteger zero() {
        return BigInteger.ZERO;
    }

    @Override
    public BigInteger identity() {
        return BigInteger.ONE;
    }

    @Override
    public BigInteger sum(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger product(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }
}



