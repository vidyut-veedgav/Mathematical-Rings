package RingPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import RingPackage.MatrixMap.InvalidLengthException.Cause;

/**
 * @author Vidyut Veedgav
 * a class representing a matrix map class
 */
public final class MatrixMap<T> {
    
    private final Map<Indexes, T>  matrix; //an instance field representing the matrix
    
    /**
     * a constructor for the matrix
     * @param matrix
     */
    private MatrixMap(Map<Indexes, T> matrix) {
        //null check
        assert matrix != null : "matrix cannot be null";
        this.matrix = matrix;
    }

    /**
     * a method to return the matrix size
     * @return
     */
    public Indexes size() {

        List<Indexes> keys = new ArrayList<>(matrix.keySet());
        return Collections.max(keys);
    }

    /**
     * a method to override the toString method
     * INCOMPLETE
     */
    @Override
    public String toString() {

        Indexes size = size();
        int numRows = size.row() + 1; // Add 1 to include row 0
        int numCols = size.column() + 1; // Add 1 to include column 0
    
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Indexes index = new Indexes(row, col);
                T value = matrix.get(index);
                System.out.println("[" + index.toString() + "]: " + value); //print the entry
                System.out.println("\t"); //print a tab
            }
            System.out.println("\n");
        }
        return "";
    }

    /**
     * a method to return the element at the given indexes
     * FOUNDATION METHOD FOR OTHER TWO VALUE METHODS
     * @return the corresponding value
     */
    public T value(Indexes indexes) {

        Objects.requireNonNull(indexes, "indexes cannot be null");
        return matrix.get(indexes);
    }

    /**
     * overloading the value method to account for unique indexes
     * @return the corresponding value
     */
    public T value(int row, int column) {

        Indexes indexes = new Indexes(row, column);
        return value(indexes);
    }
    
    /**
     * a nested class to aid error handling by defining protocols for controlling the matrix lengths
     */
    static class InvalidLengthException extends Exception {

        /**
         * an enum to define the possible values of the exception's cause
         */
        enum Cause {
            ROW, 
            COLUMN
        }
    
        private Cause cause; //a private variable declaring the cause
        private Integer length; //a private variable declaring the length

        /**
         * a constructor for the InvalidLengthException
         * @param cause
         * @param length
         */
        public InvalidLengthException(Cause cause, Integer length) {
            this.cause = cause;
            this.length = length;
        }

        /**
         * getter for the cause
         * @return the cause
         */
        public Cause cause() {
            return cause;
        }

        /**
         * a getter for the length
         * @return the length
         */
        public Integer length() {
            return length;
        }

        /**
         * a method to check if the length is positive and throw an exception otherwise
         * @param cause
         * @param length
         * @return the length
         */
        public static int requireNonEmpty(Cause cause, int length) {

            //null check
            Objects.requireNonNull(cause, "cause cannot be null");

            //checks if the length of the cause dimention fails the valid criteria
            if (length <= 0) {
                throw new IllegalArgumentException(new InvalidLengthException(cause, length)); 
            }
            return length;
        }
    }

    /**
     * a method to create an instance of a MatrixMap using a defined valueMapper function
     * @param <S>
     * @param rows
     * @param columns
     * @param valueMapper
     * @return a new MatrixMap
     */
    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {

        //error handling
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");

        InvalidLengthException.requireNonEmpty(Cause.ROW, rows);
        InvalidLengthException.requireNonEmpty(Cause.COLUMN, columns);

        Map<Indexes, S> matrix = new HashMap<>();
        List<Indexes> indexes = Indexes.stream(rows, columns).collect(Collectors.toList());

        for (Indexes index : indexes) {
            S value = valueMapper.apply(index);
            matrix.put(index, value);
        }
        return new MatrixMap<>(matrix);
    }

    /**
     * overloading the instance method to accept an Indexes object as a parameter
     * @param <S>
     * @param size
     * @param valueMapper
     * @return a MatrixMap
     */
    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {

        //null checks
        Objects.requireNonNull(size, "size cannot be null");
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");
    
        //calling the original instance method
        return MatrixMap.instance(size.row(), size.column(), valueMapper);
    }

    /**
     * a method to populate a MatrixMap using constant values
     * @param <S>
     * @param size
     * @param value
     * @return a new MatrixMap
     */
    public static <S> MatrixMap<S> constant(int size, S value) {

        //null check
        Objects.requireNonNull(value, "value cannot be null");

        return instance(size, size, (index) -> value);
    }

    /**
     * a method to return a new square matrix populated with the identity along the diagonal and zero otherwise
     * @param <S>
     * @param size
     * @param zero
     * @param identity
     * @return a new MatrixMap
     */
    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {

        //null checks
        Objects.requireNonNull(zero, "zero cannot be null");
        Objects.requireNonNull(identity, "identity cannot be null");

        return instance(size, size, (index) -> (index.areDiagonal()) ? identity : zero);
    }

    /**
     * a method to populate a MatrixMap instance from the values in the two-dimentional matrix
     * @param <S>
     * @param matrix
     * @return a new MatrixMap
     */
    public static <S> MatrixMap<S> from(S[][] matrix) {

        return instance(matrix.length, matrix[0].length, (index) -> index.value(matrix));
    }

    public static void main(String[] args) {

        //example: storing values as the sum of the index row and column
        System.out.println(MatrixMap.instance(3, 4, (index) -> index.row() + index.column()));
    }
}

