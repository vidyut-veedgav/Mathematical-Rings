package RingPackage;

import java.util.Map;
import java.util.Objects;

/**
 * @author Vidyut Veedgav
 * a class representing a matrix map class
 */
public final class MatrixMap<T> {
    
    private final Map<Indexes<T>, T>  matrix; //an instance field representing the matrix
    
    /**
     * a constructor for the matrix
     * @param matrix
     */
    private MatrixMap(Map<Indexes<T>, T> matrix) {
        //null check
        assert matrix != null : "matrix cannot be null";
        this.matrix = matrix;
    }

    /**
     * a method to return the matrix size
     * @return
     */
    public Indexes<T> size() {
        //returns the matrix size
        return null;
    }

    /**
     * a method to override the toString method
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * a method to return the element at the given indexes
     * @return the corresponding value
     */
    public T value(Indexes<T> indexes) {

        Objects.requireNonNull(indexes, "indexes cannot be null");
        return matrix.get(indexes);
    }

    /**
     * overloading the value method to account for unique indexes
     * @return the corresponding value
     */
    public T value(int row, int column) {

        Indexes<T> index  = new Indexes<>(row, column);
        return matrix.get(index);
    }

    //continue from here
}
