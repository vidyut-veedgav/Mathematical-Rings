package MatrixPackage;
import java.util.Objects;

import RingPackage.Ring;

/**
 * a class defining a Ring for matrix operations
 */
public class MatrixRing<T> implements Ring<T> {
    
    private Ring<T> baseRing; //a private field storing a ring 

    /**
     * a constructor for the MatrixRing class
     * @param ring
     */
    private MatrixRing(Ring<T> ring) {
        //null check
        assert ring != null : "ring cannot be null";
        this.baseRing = ring;
    }

    /**
     * a static method to return a new polynomial
     * @param <T> a ring
     * @return a new polynomial
     */
    public static <T> MatrixRing<T> instance(Ring<T> ring) {
        //null check
        Objects.requireNonNull(ring, "ring cannot be null");

        return new MatrixRing<>(ring);
    }

    @Override
    public T zero() {
        return null;
    }

    @Override
    public T identity() {
        return null;
    }

    @Override
    public T sum(T x, T y) {
        return null;
    }

    @Override
    public T product(T x, T y) {
        return null;
    }
}
