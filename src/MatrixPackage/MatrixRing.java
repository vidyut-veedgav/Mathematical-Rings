package MatrixPackage;
import java.util.Objects;

import MatrixPackage.MatrixMap.InconsistentSizeException;
import MatrixPackage.MatrixMap.NonSquareException;
import RingPackage.Ring;

/**
 * This is a class defining a Ring for matrix operations, specifically addition and multiplication. 
 * @author Vidyut Veedgav
 */
public final class MatrixRing<T> {
    
    private final Ring<T> baseRing; //a private field storing a ring 

    /**
     * Constructor for the MatrixRing class, called by the static factory method (instance)
     * @param ring a Ring object 
     */
    private MatrixRing(Ring<T> ring) {
        this.baseRing = ring;
    }

    /**
     * Returns a new MatrixRing instance
     * @param <T> the static type T
     * @param ring a Ring object used for mapping and intermediate operations. 
     * @return a new MatrixRing object which can be used to compute Matrix addition and multiplication
     */
    public static <T> MatrixRing<T> instance(Ring<T> ring) {
        //null check
        Objects.requireNonNull(ring, "ring cannot be null");
        return new MatrixRing<>(ring);
    }

    /**
     * Handles Matrix addition.
     * @param x the first Matrix addend.
     * @param y the second Matrix addend.
     * @return a new Matrix object which is the sum of its parameters.
     * @throws InconsistentSizeException a precondition is that x and y must be of equal size. 
     */
    public Matrix<T> sum(Matrix<T> x, Matrix<T> y) {
        //null check
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        return x.plus(y, (a, b) -> baseRing.sum(a, b));
    }

    /**
     * Handles Matrix multiplication.
     * @param x the first Matrix factor.
     * @param y the second Matrix factor.
     * @return a new Matrix object which is the product of its parameters.
     * @throws NonSquareException a precondition is that x and y must both be square matrices. 
     * @throws InconsistentSizeException a precondition is that x and y must be of equal size. 
     */
    public Matrix<T> product(Matrix<T> x, Matrix<T> y) {
        //null check
        Objects.requireNonNull(x, "x cannot be null");
        Objects.requireNonNull(y, "y cannot be null");
        return x.times(y, baseRing);
    }
}
