package RingPackage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

            if (length > 0) {
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
        checkPositive(rows, columns); //checking if the 
        
        Map<Indexes, S> matrix = new HashMap<>();
        List<Indexes> indexes = Indexes.stream(rows, columns).collect(Collectors.toList());

        for (Indexes index : indexes) {
            S value = valueMapper.apply(index);
            matrix.put(index, value);
        }
        return new MatrixMap<>(matrix);
    }

    /**
     * a helper method to determine if arguments are negative and throw the appropriate exception
     * @param rows
     * @param columns
     */
    private static void checkPositive(int rows, int columns) {
        checkRows(rows);
        checkColumns(columns);
    }

    /**
     * a subhelper method to throw an exception if the error is caused by the row length of the matrix
     * @param rows
     */
    private static void checkRows(int rows) {
        if (rows <= 0) {
            throw new IllegalArgumentException(new InvalidLengthException(Cause.ROW, rows));
        }
    }

    /**
     * a subhelper method to throw an exception if the error is caused by the column length of the matrix
     * @param columns
     */
    private static void checkColumns(int columns) {
        if (columns <= 0) {
            throw new IllegalArgumentException(new InvalidLengthException(Cause.COLUMN, columns));
        }
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

        //extracting the row and column values from the Indexes parameter
        int row = size.row();
        int column = size.column();

        //calling the original instance method
        return MatrixMap.instance(row, column, valueMapper);
    }

    public static <S> MatrixMap<S> constant(int size, S value) {

        //null check
        Objects.requireNonNull(value, "value cannot be null");

        checkIfSizePositive(size); //checking if size is positive

        Map<Indexes, S> matrix = new HashMap<>();
        return null;
    }

    /**
     * a method to return a new square matrix containing the identity along the diagonal and zero otherwise
     * @param <S>
     * @param size
     * @param zero
     * @param identity
     * @return
     */
    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {

        //null checks
        Objects.requireNonNull(zero, "zero cannot be null");
        Objects.requireNonNull(identity, "identity cannot be null");

        checkIfSizePositive(size); //checking if size is positive

        Map<Indexes, S> map = new HashMap<>();

        for (int i = 0; i < size; i++) {
            populate(size, zero, identity, map, i); //populates the matrix with the correct value
        }
        return new MatrixMap<>(map);
    }

    /**
     * a subroutine for the identity method that populates the matrix with the identity input or zero depending on whether or not the index is diagonal
     * @param <S>
     * @param size
     * @param zero
     * @param identity
     * @param map
     * @param i
     */
    private static <S> void populate(int size, S zero, S identity, Map<Indexes, S> map, int i) {
        for (int j = 0; j < size; j++) {
            Indexes key = new Indexes(i, j);
            checkForDiagonal(zero, identity, map, key);
        }
    }

    /**
     * a subroutine for the identity method that checks if the given index is on the diagonal
     * @param <S>
     * @param zero
     * @param identity
     * @param map
     * @param key
     */
    private static <S> void checkForDiagonal(S zero, S identity, Map<Indexes, S> map, Indexes key) {
        if (key.areDiagonal()) {
            map.put(key, identity);
        }
        else {
            map.put(key, zero);
        }
    }

    /**
     * a helper method for the constant and identity methods to check if the size of the matrix is positive
     * @param size
     */
    private static void checkIfSizePositive(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * a method to create a MatrixMap instance from the values in the two-dimentional matrix
     * @param <S>
     * @param matrix
     * @return
     */
    public static <S> MatrixMap<S> from(S[][] matrix) {

        Map<Indexes, S> map = new HashMap<>();
        populate(matrix, map);
        return new MatrixMap<>(map);
    }

    /**
     * a subroutine of the from method to populate the MatrixMap with elements from the matrix array
     * @param <S>
     * @param matrix
     * @param map
     */
    private static <S> void populate(S[][] matrix, Map<Indexes, S> map) {
        for (int row = 0; row < matrix.length; row++) {
            assignRowValue(matrix, map, row);
        }
    }

    /**
     * a subroutine of the from method to assign the row values of the 2D array
     * @param <S>
     * @param matrix
     * @param map
     * @param row
     */
    private static <S> void assignRowValue(S[][] matrix, Map<Indexes, S> map, int row) {
        for (int col = 0; col < matrix[row].length; col++) {
            assignColumnValue(matrix, map, row, col);
        }
    }
 
    /**
     * a subroutine of the from method to assign the column values of the 2D array
     * @param <S>
     * @param matrix
     * @param map
     * @param row
     * @param col
     */
    private static <S> void assignColumnValue(S[][] matrix, Map<Indexes, S> map, int row, int col) {
        Indexes key = new Indexes(row, col);
        S value = matrix[row][col];
        map.put(key, value);
    }

    public static void main(String[] args) {

        //example: storing values as the sum of the index row and column
        System.out.println(MatrixMap.instance(3, 4, (index) -> index.row() + index.column()));
    }
}

