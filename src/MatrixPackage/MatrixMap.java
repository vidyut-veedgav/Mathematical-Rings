package MatrixPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import MatrixPackage.MatrixMap.InvalidLengthException.Cause;
import RingPackage.IntegerRing;
import RingPackage.Ring;
import RingPackage.Rings;

/**
 * @author Vidyut Veedgav
 * a class representing a matrix map class
 */
public final class MatrixMap<T> implements Matrix<T> { 
    
    private final Map<Indexes, T>  matrix; //a field representing the matrix
    private final Indexes size;
    
    /**
     * a constructor for the matrix
     * @param matrix
     */
    private MatrixMap(Map<Indexes, T> matrix) {
        this.matrix = matrix;
        this.size = Collections.max(matrix.keySet());
    }

    /**
     * a getter method for the map used for testing
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
        return size;
    }

    /**
     * a method to override the toString method
     */
    @Override
    public String toString() {

        Indexes size = size();
        int numRows = size.row() + 1; // Add 1 to include row 0
        int numCols = size.column() + 1; // Add 1 to include column 0
    
        StringBuilder sb = new StringBuilder();
        //indexing through the rows
        for (int row = 0; row < numRows; row++) {

            //indexing through the corresponding column
            for (int col = 0; col < numCols; col++) {
                Indexes index = new Indexes(row, col); //creating an index
                T value = value(index); //finding the value at this index
                sb.append("[").append(index.toString()).append("]: ").append(value).append("\t"); //add the entry
            }
            sb.append("\n"); //add a carriage return
        }
        return sb.toString();
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

        for (Indexes index : indexes) { //populating the matrix with the specified values
            S value = valueMapper.apply(index);
            matrix.put(index, value);
        } 
        return new MatrixMap<>(Map.copyOf(matrix));
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

            //null checks
            Objects.requireNonNull(thisIndex, "thisIndex cannot be null");
            Objects.requireNonNull(otherIndex, "otherIndex cannot be null");

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
         * @throws IllegalArgumentException
         */
        public static <T> Indexes requireMatchingSize(Matrix<T> thisMatrix, Matrix<T> otherMatrix) {

            //null checks
            Objects.requireNonNull(thisMatrix, "thisMatrix cannot be null");
            Objects.requireNonNull(otherMatrix, "otherMatrix cannot be null");
            
            //checks if the sizes are not equal
            if (!thisMatrix.size().equals(otherMatrix.size())) {
                throw new IllegalArgumentException(new InconsistentSizeException(thisMatrix.size(), otherMatrix.size()));
            }
            return thisMatrix.size();
        }
    }

    /**s
     * a nested class used to check if a matrix is a square
     */

    static class NonSquareException extends Exception {

        private final Indexes indexes; //instance field storing the index to be checked

        /**
         * constructor
         * @param indexes
         */
        public NonSquareException(Indexes indexes) {

            //null check
            Objects.requireNonNull(indexes, "indexes cannot be null");
            this.indexes = indexes;
        }

        /**
         * a getter for the indexes method
         * @return
         */
        public Indexes getIndexes() {
            return indexes;
        }

        /**
         * a method to throw an exception if the index that is passed in is not on the diagonal of the matrix
         * @param indexes should be the size of the matrix
         * @return
         */
        public static Indexes requireDiagonal(Indexes indexes) {

            //null check
            Objects.requireNonNull(indexes, "indexes cannot be null");

            //checks if the index is on the diagonal
            if (!indexes.areDiagonal()) {
                throw new IllegalStateException(new NonSquareException(indexes));
            }
            return indexes;
        }
    }

    /**
     * a method to support matrix addition
     * @param other
     * @param plus
     * @return
     */
    public MatrixMap<T> plus(Matrix<T> other, BinaryOperator<T> plus) {

        //null checks
        Objects.requireNonNull(other, "other cannot be null");
        Objects.requireNonNull(plus, "plus cannot be null");

        //checking if the matrixes are the same size
        InconsistentSizeException.requireMatchingSize(this, other); 

        return instance(this.size(), (index) -> plus.apply(this.value(index), other.value(index))); //creating a new instance of the matrix containing the sum
    }

    /**
     * a method to support matrix multiplication
     * @return
     */
    public MatrixMap<T> times(Matrix<T> other, Ring<T> ring) {

        //null checks
        Objects.requireNonNull(other, "other cannot be null");
        Objects.requireNonNull(ring, "ring cannot be null");

        NonSquareException.requireDiagonal(this.size()); //checks if this matrix is a square
        NonSquareException.requireDiagonal(other.size()); //checks if the other matrix is a square
        InconsistentSizeException.requireMatchingSize(this, other); //checks if the matrixes are of equal size

        int length = this.size().row(); //sets the length of the matrixes by accessing the row of the size of this index (can be row or column from either matrix)
        return instance(this.size(), (index) -> { //creates an instance of a matrix containing the product

            return getProductAtIndex(other, ring, length, index);
        });
    }

    /**
     * a subroutine which conducts multiplication and computes the resulting value at each index of the matrix
     * @param other
     * @param ring
     * @param length
     * @param index
     * @return
     */
    private T getProductAtIndex(Matrix<T> other, Ring<T> ring, int length, Indexes index) {
        List<T> products = new ArrayList<>();
        //indexes until length is reached and adds to the product list the element at the row of this and column of other
        for (int i = 0; i <= length; i++) {
            products.add(ring.product(this.value(new Indexes(index.row(), i)), other.value(new Indexes(i, index.column()))));
        }
        return Rings.sum(products, ring); //sums the elements of the product list
    }

    /**
     * a method to convert this matrix to a sparse matrix
     * @return
     */
    public SparseMatrixMap<T> convertToSparse(Ring<T> ring) {
        return SparseMatrixMap.instance(this.size(), ring, (index) -> this.value(index));
    }

    /**
     * main method
     * @param args
     *
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Matrix Size");
        Integer size = Integer.valueOf(input.nextLine());
        System.out.println("SIZE = " + size);
     
        System.out.println("Matrix One Values:");
        Map<Indexes, Integer> indexMap = new HashMap<>();
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                Indexes index = new Indexes(i, j);
                System.out.println("Value at: " + index.toString());
                indexMap.put(index, Integer.valueOf(input.nextLine()));
            }
        }
        MatrixMap<Integer> m1 = MatrixMap.instance(new Indexes(size, size), (index) -> indexMap.get(index));
        System.out.println("MATRIX 1: ");
        System.out.println(m1.toString());

        System.out.println("Matrix Two Values:");
        Map<Indexes, Integer> indexMap2 = new HashMap<>();
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                Indexes index = new Indexes(i, j);
                System.out.println("Value at: " + index.toString());
                indexMap2.put(index, Integer.valueOf(input.nextLine()));
            }
        }
        MatrixMap<Integer> m2 = MatrixMap.instance(new Indexes(size, size), (index) -> indexMap2.get(index));
        System.out.println("MATRIX 1: ");
        System.out.println(m1.toString());

        System.out.println("ADD or MULTIPLY ([A/M])");
        String addOrMultiply = input.nextLine();

        input.close();

        Ring<Integer> intRing = new IntegerRing();

        if (addOrMultiply.equals("A")) {
            MatrixMap<Integer> sum = m1.plus(m2, (x, y) -> intRing.sum(x, y));
            System.out.println(sum);
        }
        else if (addOrMultiply.equals("M")) {
            MatrixMap<Integer> product = m1.times(m2, intRing);
            System.out.println(product);
        }
        else {
            System.out.println("Invalid selection");
        }
    }  
    */
}

