package MatrixPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import MatrixPackage.MatrixMap.InvalidLengthException.Cause;
import RingPackage.Ring;
import RingPackage.Rings;

/**
 * This is a class representing a standard matrix with each entry consisting of an Indexes object as keys and generic T values
 * @author Vidyut Veedgav
 */
public final class MatrixMap<T> implements Matrix<T> { 
    
    private final Map<Indexes, T>  matrix; //a field representing the matrix itself
    private final Indexes size; //a field representing the size of the matrix in Indexes form
    
    /**
     * Constructor for the matrix, called by the static factory methods (instance, constant, identity).
     * Sets the size field by iterating through the Indexes objects in the key set and finding the greatest index. This occurs upon instantiation.
     * @param matrix the Map object representing the underlying data structure.
     */
    private MatrixMap(Map<Indexes, T> matrix) {
        this.matrix = matrix;
        this.size = Collections.max(matrix.keySet());
    }

    /**
     * Gets the internal Map object of the matrix.
     * @return An immutable copy of the matrix, which can be used for containment testing.
     */
    protected Map<Indexes, T> getMap() {
        return Map.copyOf(matrix);
    }

    /**
     * Gets the matrix size.
     * @return the greatest index of the matrix, computed by the constructor upon instantiation.
     */
    public Indexes size() {
        return size;
    }

    /**
     * Returns a string representation of the object that "textually represents" a MatrixMap.
     * @return a string consisting of each entry (Indexes: value)in the matrix, laid out in order top to bottom and left to right such that it accurately represents a matrix.
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
     * Returns the value of this MatrixMap at the specified Indexes object in the argument.
     * @param indexes the Indexes object containing the row and column of the desired return value.
     * @return the corresponding value mapped by indexes
     */
    public T value(Indexes indexes) {

        //null check
        Objects.requireNonNull(indexes, "indexes cannot be null");
        return matrix.get(indexes);
    }

    /**
     * Returns the value of this MatrixMap at the specified row and column in the argument.
     * @param indexes the row and column of the desired return value in this MatrixMap.
     * @return the corresponding value mapped by the index at (row, column).
     */
    public T value(int row, int column) {
        return value(new Indexes(row, column));
    }
    
    /**
     * This is a nested Exception class to aid error handling by defining protocols for controlling Matrix lengths.
     */
    static class InvalidLengthException extends Exception {

        /**
         * Defines the possible values of a Matrix, which could be subject to an invalid length.
         */
        enum Cause {
            ROW, 
            COLUMN
        }
    
        private Cause cause; //a private variable declaring the cause of the error
        private Integer length; //a private variable declaring the length value being examined

        /**
         * Constructor for the InvalidLengthException.
         * @param cause either ROW or COLUMN, as specified in the enumerated type, Cause.
         * @param length the length value being examined.
         */
        public InvalidLengthException(Cause cause, Integer length) {

            //null checks
            Objects.requireNonNull(cause);
            Objects.requireNonNull(length);

            this.cause = cause;
            this.length = length;
        }

        /**
         * Gets the cause of the InvalidLengthException.
         * @return either ROW or COLUMN, if one of them violates the specified length constraints.
         */
        public Cause cause() {
            return cause;
        }

        /**
         * Gets the length of the value being examined by the InvalidLengthException.
         * @return the integer value of the either the row or column of a Matrix object.
         */
        public Integer length() {
            return length;
        }

        /**
         * Checks if the specified length is positive and throws a new IllegalArgumentException with the cause of InvalidLengthException otherwise.
         * @param cause the specified ROW or COLUMN value.
         * @param length the specified length being checked.
         * @return the length (argument) if it is positive; a new IllegalArgumentException otherwise.
         * @throws IllegalArgumentException
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
     * Creates an instance of a MatrixMap by creating a stream of Indexes from (0, 0) to the specified rows and columns, 
     * and mapping each Indexes object in the stream to a specified value defined by the valueMapper function.
     * @param <S> the static type S.
     * @param rows the number of rows the MatrixMap should have. 
     * @param columns the number of columns the MatrixMap should have. 
     * @param valueMapper a functional interface which uses a lambda expression to set the values of each Indexes object in the MatrixMap.
     * The expression works as follows: ((index) -> index.rows + index.column). In this example, each index is mapped to a value representing
     * the sum of its row and column, but it can be mapped to anything of type S.
     * @return a new MatrixMap with the specified size and populated by the user defined function.
     * @throws InvalidLengthException a precondition is that the rows and columns in the argument must be valid.
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
     * Creates an instance of a MatrixMap by creating a stream of Indexes from (0, 0) to the specified Indexes size, 
     * and mapping each Indexes object in the stream to a specified value defined by the valueMapper function.
     * @param <S> the static type S.
     * @param size the Indexes object containing the number of rows and columns that the MatrixMap should have.
     * @param valueMapper a functional interface which uses a lambda expression to set the values of each Indexes object in the MatrixMap.
     * The expression works as follows: ((index) -> index.rows + index.column). In this example, each index is mapped to a value representing
     * the sum of its row and column, but it can be mapped to anything of type S.
     * @return a new MatrixMap with the specified size and populated by the user defined function.
     * @throws InvalidLengthException a precondition is that the size in the argument must be valid.
     */
    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {

        //null checks
        Objects.requireNonNull(size, "size cannot be null");
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");
    
        //calling the foundational instance method
        return MatrixMap.instance(size.row(), size.column(), valueMapper);
    }

    /**
     * Creates an instance of a MatrixMap by creating a stream of Indexes from (0, 0) to the specified int size which represents both the rows and columns of the MatrixMap, 
     * and mapping each and every Indexes object in the stream to the S value passed into the argument. This represents a constant matrix.
     * @param <S> the static type S.
     * @param size the integer value representing both the number of rows and columns in this MatrixMap.
     * @param value the value that the MatrixMap is being populated with. 
     * @return a new MatrixMap with the specified size and populated with the specifeid value at each index. 
     * @throws InvalidLengthException a precondition is that the size in the argument must be valid.
     */
    public static <S> MatrixMap<S> constant(int size, S value) {

        //null check
        Objects.requireNonNull(value, "value cannot be null");

        //calling the foundational instance method
        return instance(size, size, (index) -> value);
    }

    /**
     * Creates an instance of a MatrixMap by creating a stream of Indexes from (0, 0) to the specified int size which represents both the rows and columns of the MatrixMap, 
     * and mapping each Indexes object to the specified identity if it lies on the diagonal, and zero otherwise. This represents an identity matrix.
     * @param <S> the static type S.
     * @param size the integer value representing both the number of rows and columns in this MatrixMap.
     * @param zero the value representing zero for type S
     * @param identity the value representing the multiplicative identity for type S
     * @return a new MatrixMap representing an identity matrix, populated with the identity along the diagonal; zero otherwise
     */
    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {

        //null checks
        Objects.requireNonNull(zero, "zero cannot be null");
        Objects.requireNonNull(identity, "identity cannot be null");

        //calling the foundational instance method
        return instance(size, size, (index) -> (index.areDiagonal()) ? identity : zero);
    }

    /**
     * Creates an instance of a MatrixMap by creating a stream of Indexes from (0, 0) to the length of the row and column dimentions of the two dimentional array being passed into the argument, 
     * and mapping each Indexes object to the value at the corresponding index in the argument matrix. 
     * @param <S> the static type S.
     * @param matrix the two-dimentional array that this method constructs a MatrixMap from
     * @return a new MatrixMap representing the matrix in array form passed into the argument
     * @throws InvalidLengthException a precondition is that the length of the outer and inner arrays of matrix in the argument must be valid.
     */
    public static <S> MatrixMap<S> from(S[][] matrix) {

        //null check
        Objects.requireNonNull(matrix, "matrix cannot be null");

        //calling the foundational instance method
        return instance(matrix.length - 1, matrix[0].length - 1, (index) -> index.value(matrix));
    }

    /**
     * This is a nested Exception class to aid error handling by defining protocols for controlling Matrix sizes.
     */
    static class InconsistentSizeException extends Exception {

        private Indexes thisIndex; //stores the current Index
        private Indexes otherIndex; //stores the other Index

        /**
         * Constructor for the InconsistentSizeException object. 
         * @param thisIndex the current Index of this Matrix.
         * @param otherIndex the other Index of another Matrix.
         */
        public InconsistentSizeException(Indexes thisIndex, Indexes otherIndex) {

            //null checks
            Objects.requireNonNull(thisIndex, "thisIndex cannot be null");
            Objects.requireNonNull(otherIndex, "otherIndex cannot be null");

            this.thisIndex = thisIndex;
            this.otherIndex = otherIndex;
        }

        /**
         * Gets the current Index of this Matrix.
         * @return an Indexes object representing the specified index of this Matrix
         */
        public Indexes getThisIndex() {
            return thisIndex;
        }

        /**
         * Gets the other Index of another Matrix.
         * @return an Indexes object representing the specified index of the other Matrix
         */
        public Indexes getOtherIndex() {
            return otherIndex;
        }

        /**
         * Checks if two matrices are the same size by comparing the Indexes objects representing their sizes. 
         * If they are not the same size, an IllegalArgumentException is thrown with the cause, InconsistentSizeException. 
         * @param <T> the static type T.
         * @param thisMatrix the first Matrix being compared.
         * @param otherMatrix the second Matrix being compared.
         * @return the Indexes object representing the size of this Matrix if it is equal to the size of the other Matrix; a new IllegalArgumentException otherwise.
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

    /**
     * This a nested Exception class to aid error handling, used to check if a Matrix object represents a square matrix. 
     */

    static class NonSquareException extends Exception {

        private final Indexes indexes; //instance field storing the index to be checked

        /**
         * Constructor for the NonSquareException. 
         * @param indexes the Indexes object which is used to determine of the Matrix object is a square matrix or not. This should be the size of a Matrix.
         */
        public NonSquareException(Indexes indexes) {

            //null check
            Objects.requireNonNull(indexes, "indexes cannot be null");
            this.indexes = indexes;
        }

        /**
         * Gets the Indexes used to determine if the Matrix is a square or not, as defined by the constructor.
         * @return the Indexes object set upon instantiation. 
         */
        public Indexes getIndexes() {
            return indexes;
        }

        /**
         * Checks if the index that is passed into the argument is on the diagonal of the matrix. 
         * In other words, this method checks if the row and column of the Indexes object are equal, which would indicate that the Matrix is indeed square.
         * If not, this method throws an IllegalStateException with the cause, NonSquareException.
         * @param indexes the Indexes object which should represent the size of the object.
         * @return a new IllegalStateException if the Indexes object is not on the diagonal; the Indexes object otherwise. 
         * @throws IllegalStateException
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
     * Computes Matrix addition by creating a new instance of a MatrixMap, and using the specified BinaryOperator interface in the argument to apply addition between this MatrixMap and the other. 
     * One unobvious precondition is that the apply method of the BinaryOperator should model addition, perhaps using a Ring object. 
     * @param other the other Matrix being added
     * @param plus the BinaryOperator interface which computes the addition of each element in the addend Matrices
     * @return a new MatrixMap which is the sum of this MatrixMap and the other Matrix. 
     * @throws InconsistentSizeException a precondition is that the two matrices being added must be of equal size. 
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
     * Computes Matrix multiplication by creating a new instance of a MatrixMap, and using the specified Ring object to compute the intermediate operations of matrix multiplication. 
     * @param other the other Matrix being multiplied.
     * @param ring a Ring object used for intermediate operations, further defined in the getProductAtIndex method.
     * @return a new MatrixMap which is the product of this MatrixMap and the other Matrix. 
     * @throws NonSquareException a precondition is that both factor matrices must be square matrices. 
     * @throws InconsistentSizeException a precondition is that they must be of equal size. 
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

            return getProductAtIndex(other, ring, length, index); //a subroutine to compite the product at each specific index of the matrix
        });
    }

    /**
     * Conducts the matrix multiplication operation and computes the resulting value at each index of the matrix.
     * @param other the other Matrix being multiplied.
     * @param ring a Ring object used for intermediate operations.
     * @param length the length of the matrix dimentions. It can be either a row or column since the matrices being multiplied must both be squares.
     * @param index the Indexes object which represents the index of the product being computed.
     * @return the T product of matrix multiplication between this and another matrix at the specified index. 
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
     * Converts this MatrixMap to a SparseMatrixMap by creating an instance of a SparseMatrixMap and mapping each value of this MatrixMap to its counterpart.
     * This ensures that if this MatrixMap contains a zero, it is eliminated upon instantiation of a SparseMatrixMap.
     * @param ring a Ring object used to represent the zero of data type T when creating a SparseMatrixMap
     * @return a new SparseMatricMap containting all entries of this MatrixMap, except for mappings to zero. 
     */
    public SparseMatrixMap<T> convertToSparse(Ring<T> ring) {

        //null check
        Objects.requireNonNull(ring, "ring cannot be null");
        return SparseMatrixMap.instance(this.size(), ring, (index) -> this.value(index));
    }
}

