package RingPackage;

import java.util.List;
import java.util.Objects;

/**
 * This is a class to demonstrate the concept of Rings on Polynomials
 * @author Vidyut Veedgav
 */
public final class PolynomialRing<T> implements Ring<Polynomial<T>> {

    private Ring<T> baseRing; //a private field storing a ring 

    /**
     * Constructor for the PolynomialRing class, called by the static factory method (instance)
     * @param ring a Ring object 
     */
    private PolynomialRing(Ring<T> ring) {
        this.baseRing = ring;
    }

    /**
     * Returns a new PolynomialRing instance
     * @param <T> the static type T
     * @param ring a Ring object used for mapping and intermediate operations. 
     * @return a new MatrixRing object which can be used to compute Polynomial addition and multiplication
     */
    public static <T> PolynomialRing<T> instance(Ring<T> ring) {
        //null check
        Objects.requireNonNull(ring, "ring cannot be null");

        return new PolynomialRing<>(ring);
    }

    /**
     * Returns the zero property of data type Polynomial
     * @return zero of type Polynomial
     */
    @Override
    public Polynomial<T> zero() {
        return Polynomial.from(List.of());
    }

    /**
     * Returns the multiplicative identity property of data type Polynomial
     * @return the identity of type Polynomial
     */
    @Override
    public Polynomial<T> identity() {
        return Polynomial.from(List.of(baseRing.identity()));
    }

    /**
     * Handles Polynomial addition.
     * @param x the first Polynomial addend.
     * @param y the second Polynomial addend.
     * @return a new Polynomial object which is the sum of its parameters.
     */
    @Override
    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x.plus(y, baseRing);
    }

    /**
     * Handles Polynomial multiplication.
     * @param x the first Polynomial factor.
     * @param y the second Polynomial factor.
     * @return a new Polynomial object which is the sum of its parameters.
     */
    @Override
    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        //null checks
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");

        return x.times(y, baseRing);
    }
}
