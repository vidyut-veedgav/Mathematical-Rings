package RingPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

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

        List<Indexes<T>> keys = new ArrayList<>(matrix.keySet());
        Indexes<T> maxIndex = keys.get(0);

        for (Indexes<T> key : keys)  {
            if (maxIndex.compareTo(key) > 0) {
                maxIndex = key;
            }
        }
        return maxIndex;
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

    /* Indexes<T> Indexes::value(MatrixMap<T> matrix) {
        //wtf is this method
        return null;
    }
    */
    
    static class InvalidLengthException extends Exception {

          enum Cause {
            ROW, 
            COLUMN
        }

        private Cause cause;
        private Integer length;

        public InvalidLengthException(Cause cause, Integer length) {
            this.cause = cause;
            this.length = length;
        }

        /*
        public Cause getCause() {
            return cause;
        }
        */

        public Integer getLength() {
            return length;
        }

        public static int requireNonEmpty(Cause cause, int length) {

            if (length > 0) {
                throw new IllegalArgumentException(); //must make cause of this IllegalLengthException
            }
            return length;
        }
    }

    public static <S> MatrixMap<S> instance (int rows, int columns, Function<Indexes<?>, S> valueMapper) {
        return null;
    }
}

