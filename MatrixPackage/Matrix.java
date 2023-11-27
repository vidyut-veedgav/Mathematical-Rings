package MatrixPackage;

import java.util.function.BinaryOperator;
import RingPackage.Ring;

/**
 * a generic matrix interface which can be used to instantiate 
 * MatrixMap and SparseMatrixMap
 * @author Vidyut Veedgav
 */
public interface Matrix<T> {
    
    Indexes size();
    T value(Indexes indexes);
    Matrix<T> plus(Matrix<T> other, BinaryOperator<T> plus);
    Matrix<T> times(Matrix<T> other, Ring<T> ring);
    String toString();
    
}
