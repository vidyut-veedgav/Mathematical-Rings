package MatrixPackage;
import java.util.Objects;

import RingPackage.Ring;

/**
 * a class defining a Ring for matrix operations
 */
public class MatrixRing<T> {
    
    private Ring<T> baseRing; //a private field storing a ring 

    /**
     * a constructor for the MatrixRing class
     * @param ring
     */
    private MatrixRing(Ring<T> ring) {
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

    /**
     * a ring method to handle matrix addition
     * @param x
     * @param y
     * @return
     */
    public Matrix<T> sum(Matrix<T> x, Matrix<T> y) {
        //null check
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        return x.plus(y, (a, b) -> baseRing.sum(a, b));
    }

    /**
     * a ring method to handle matrix multiplication
     * @param x
     * @param y
     * @return
     */
    public Matrix<T> product(Matrix<T> x, Matrix<T> y) {
        //null check
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        return x.times(y, baseRing);
    }
}
