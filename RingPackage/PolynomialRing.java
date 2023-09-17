package RingPackage;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on Polynomials
 */
public final class PolynomialRing<T> implements Ring<Polynomial<T>>{

    private Ring<T> ring;

    private PolynomialRing(Ring<T> ring) {
        this.ring = ring;
    }

    public static <T> PolynomialRing<T> buildRing() {
        //returns a new PolynomialRing
        return null;
    }
    @Override
    public Polynomial<T> zero() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'zero'");
    }

    @Override
    public Polynomial<T> identity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'identity'");
    }

    @Override
    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sum'");
    }

    @Override
    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        return x.plus(y, ring);
    }
    
}
