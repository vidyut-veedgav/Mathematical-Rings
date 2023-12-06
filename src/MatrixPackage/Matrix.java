package MatrixPackage;

import java.util.function.BinaryOperator;
import RingPackage.Ring;

/**
 * This is a generic matrix interface which can be used to instantiate a matrix data type such as MatrixMap and SparseMatrixMap.
 * @author Vidyut Veedgav
 */
public interface Matrix<T> {
    
    /**
     * Gets the size of a matrix, represented by an Indexes object.
     * @return the Indexes object representing the greatest index of the matrix.
     */
    Indexes size();

    /**
     * Gets the value of a matrix at a specific index.
     * @param indexes the index conatining a mapping the value that this method returns.
     * @return the value in a matrix corresponding to the argument index.
     */
    T value(Indexes indexes);

    /**
     * Adds the current Matrix object with another.
     * @param other the other addend.
     * @param plus the functional interface which computes the addition operation.
     * @return a new Matrix object which represents the sum of this matrix and other.
     */
    Matrix<T> plus(Matrix<T> other, BinaryOperator<T> plus);

    /**
     * Multiplies the current matrix object with another.
     * @param other the other factor.
     * @param ring a ring used to compute intermediate operations.
     * @return a new Matrix object which represents the product of this matrix and other.
     */
    Matrix<T> times(Matrix<T> other, Ring<T> ring);

    /**
     * Returns a string representation of the object that "textually represents" a MatrixMap.
     * @return The String representation of this Matrix object.
     */
    String toString();
}
