package RingPackage;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on Polynomials
 */
public final class PolynomialRing<T> implements Ring<Polynomial<T>>{

    private Ring<T> ring; //a private field storing a ring 

    private PolynomialRing(Ring<T> ring) {
        this.ring = ring;
    }

    /**
     * a static instance method to return a new polynomial
     * @param <T>
     * @return a new polynomial
     */
    public static <T> PolynomialRing<T> buildRing() {
        return null;
    }

    /**
     * overriding the zero method
     */
    @Override
    public Polynomial<T> zero() {
        return null;
    }

    @Override
    public Polynomial<T> identity() {
        return null;
    }

    @Override
    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        return x.plus(y, ring);
    }

    @Override
    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        return x.times(y, ring);
    }
    
}
