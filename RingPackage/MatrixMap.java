package RingPackage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import RingPackage.MatrixMap.InvalidLengthException.Cause;

/**
 * @author Vidyut Veedgav
 * a class representing a matrix map class
 */
public final class MatrixMap<T> {
    
    private final Map<Indexes, T>  matrix; //an field representing the matrix
    
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
     * a getter method for the map
     * @return an immutable copy of the matrix, which can be used for containment testing
     */
    protected Map<Indexes, T> getMap() {
        return Map.copyOf(matrix);
    }

    /**
     * a method to return the matrix size by finding the greatest key in the keySet
     * @return
     */
    public Indexes size() {
        return Collections.max(matrix.keySet());
    }

    /**
     * a method to override the toString method
     */
    @Override
    public String toString() {

        Indexes size = size();
        int numRows = size.row() + 1; // Add 1 to include row 0
        int numCols = size.column() + 1; // Add 1 to include column 0
    
        return toGrid(numRows, numCols);
    }

    /**
     * a subroutine for the toString() method which displays the matrix as a grid 
     * @param numRows
     * @param numCols
     * @return
     */
    private String toGrid(int numRows, int numCols) {

        StringBuilder sb = new StringBuilder();
        //indexing through the rows
        for (int row = 0; row < numRows; row++) {

            //indexing through the corresponding column
            for (int col = 0; col < numCols; col++) {
                addEntry(sb, row, col);
            }
            sb.append("\n"); //add a carriage return
        }
        return sb.toString();
    }

    /**
     * a subroutine of the toGrid method which creates appends a String entry to the matrix
     * @param sb
     * @param row
     * @param col
     */
    private void addEntry(StringBuilder sb, int row, int col) {

        //null check
        assert sb != null : "sb cannot be null";

        Indexes index = new Indexes(row, col); //creating an index
        T value = value(index); //finding the value at this index

        sb.append("[" + index.toString() + "]: " + value); //add the entry
        sb.append("\t"); //add a tab
    }

    /**
     * a method to return the element at the given indexes
     * FOUNDATION METHOD FOR OTHER TWO VALUE METHODS
     * @return the corresponding value
     */
    public T value(Indexes indexes) {

        //null check
        Objects.requireNonNull(indexes, "indexes cannot be null");
        return matrix.get(indexes);
    }

    /**
     * overloading the value method to account for unique indexes
     * @return the corresponding value
     */
    public T value(int row, int column) {
        return value(new Indexes(row, column));
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

            //null checks
            Objects.requireNonNull(cause);
            Objects.requireNonNull(length);

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
     * THE FOUNDATIONAL METHOD FOR CREATING MATRIX INSTANCES
     * @param <S>
     * @param rows
     * @param columns
     * @param valueMapper
     * @return a new MatrixMap
     */
    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {

        //error handling
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");
        InvalidLengthException.requireNonEmpty(Cause.ROW, rows); //checks if the rows are valid
        InvalidLengthException.requireNonEmpty(Cause.COLUMN, columns); //checks if the columns are valid

        Map<Indexes, S> matrix = new HashMap<>(); //creating the map
        List<Indexes> indexes = Indexes.stream(rows, columns).collect(Collectors.toList()); //creating a list of indexes

        populate(valueMapper, matrix, indexes); //populating the matrix with the specified values
        return new MatrixMap<>(Map.copyOf(matrix));
    }

    /**
     * a subroutine of the instance method which fills the matrix with the specified elements
     * @param <S>
     * @param valueMapper
     * @param matrix
     * @param indexes
     */
    private static <S> void populate(Function<Indexes, S> valueMapper, Map<Indexes, S> matrix, List<Indexes> indexes) {

        //null checks
        assert valueMapper != null : "valueMapper cannot be null";
        assert matrix != null : "matrix cannot be null";
        assert indexes != null : "indexes cannot be null";

        for (Indexes index : indexes) {
            S value = valueMapper.apply(index);
            matrix.put(index, value);
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
    
        //calling the foundational instance method
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

        //calling the foundational instance method
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

        //calling the foundational instance method
        return instance(size, size, (index) -> (index.areDiagonal()) ? identity : zero);
    }

    /**
     * a method to populate a MatrixMap instance from the values in the two-dimentional matrix
     * @param <S>
     * @param matrix
     * @return a new MatrixMap
     */
    public static <S> MatrixMap<S> from(S[][] matrix) {

        //null check
        Objects.requireNonNull(matrix, "matrix cannot be null");

        //calling the foundational instance method
        return instance(matrix.length - 1, matrix[0].length - 1, (index) -> index.value(matrix));
    }

    /**
     * a nested class to aid error handling
     */
    static class InconsistentSizeException extends Exception {

        private Indexes thisIndex; //stores the current Index
        private Indexes otherIndex; //stores the other Index

        /**
         * constructor
         * @param thisIndex
         * @param otherIndex
         */
        public InconsistentSizeException(Indexes thisIndex, Indexes otherIndex) {
            this.thisIndex = thisIndex;
            this.otherIndex = otherIndex;
        }

        /**
         * getter for the thisIndex field
         * @return
         */
        public Indexes getThisIndex() {
            return thisIndex;
        }

        /**
         * a getter for the otherIndex field
         * @return
         */
        public Indexes getOtherIndex() {
            return otherIndex;
        }

        /**
         * a method which checks if two matrixes are the same size
         * @param <T>
         * @param thisMatrix
         * @param otherMatrix
         * @return
         * @throws InconsistentSizeException
         */
        public static <T> Indexes requireMatchingSize(MatrixMap<T> thisMatrix, MatrixMap<T> otherMatrix) throws InconsistentSizeException {
            
            if (!thisMatrix.size().equals(otherMatrix.size())) {
                throw new InconsistentSizeException(thisMatrix.size(), otherMatrix.size());
            }
            return thisMatrix.size();
        }
    }

    /**
     * a method to add two matrixes together
     * @param other
     * @param plus
     * @return
     */
    public MatrixMap<T> plus(MatrixMap<T> other, BinaryOperator<T> plus) {

        try {
            InconsistentSizeException.requireMatchingSize(this, other);
        } catch (InconsistentSizeException e) {
            e.printStackTrace();
        }
        
        return instance(this.size(), (index) -> plus.apply(this.value(index), other.value(index)));
    }

    /**
     * a method to multiply two matrixes together
     * @return
     */
    public MatixMap<T> times(MatrixMap<T> other, Ring<T> ring) {

        try {
            InconsistentSizeException.requireMatchingSize(this, other);
        } catch (InconsistentSizeException e) {
            e.printStackTrace();
        }

        return instance(this.size(), (index) -> {

            
        });

    }

    public static void main(String[] args) {

        //example: storing values as the sum of the index row and column
        //System.out.println(MatrixMap.instance(4, 5, (index) -> index.row() + index.column()));

        Integer[][] arr = new Integer[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                arr[i][j] = 100;
            }
        }
        //System.out.println(MatrixMap.from(arr));
        MatrixMap<Integer> m = constant(5, 100);
        MatrixMap<Integer> n = constant(5, 100);

        System.out.println(m.plus(n, (a, b) -> a + b));
    }  
}

