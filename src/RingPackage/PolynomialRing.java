package RingPackage;

import java.util.List;
import java.util.Objects;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on Polynomials
 */
public final class PolynomialRing<T> implements Ring<Polynomial<T>> {

    private Ring<T> baseRing; //a private field storing a ring 

    /**
     * a constructor for the PolynomialRing class
     * @param ring
     */
    private PolynomialRing(Ring<T> ring) {
        this.baseRing = ring;
    }

    /**
     * a static method to return a new polynomial
     * @param <T> a ring
     * @return a new polynomial
     */
    public static <T> PolynomialRing<T> instance(Ring<T> ring) {
        //null check
        Objects.requireNonNull(ring, "ring cannot be null");

        return new PolynomialRing<>(ring);
    }

    /**
     * overriding the zero method
     */
    @Override
    public Polynomial<T> zero() {
        return Polynomial.from(List.of());
    }

    /**
     * overriding the identity method
     */
    @Override
    public Polynomial<T> identity() {
        return Polynomial.from(List.of(baseRing.identity()));
    }

    /**
     * overriding the sum method
     */
    @Override
    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x.plus(y, baseRing);
    }

    /**
     * overriding the product method
     */
    @Override
    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x.times(y, baseRing);
    }
}
