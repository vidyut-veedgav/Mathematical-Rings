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

import MatrixPackage.MatrixMap.InconsistentSizeException;
import MatrixPackage.MatrixMap.InvalidLengthException;
import MatrixPackage.MatrixMap.InvalidLengthException.Cause;
import MatrixPackage.MatrixMap.NonSquareException;
import RingPackage.Ring;
import RingPackage.Rings;

/**
 * This is a class representing a sparse matrix with each entry consisting of an Indexes object as keys and generic T values (barring zeros).
 * @author Vidyut Veedgav
 */
public final class SparseMatrixMap<T> implements Matrix<T> {

    private final Map<Indexes, T>  matrix; //a field representing the matrix
    private final Indexes size; //a field representing the size of the matrix
    private final Ring<T> ring; //a field representing a ring used for intermediate operations

    /**
     * Constructor for the matrix, called by the static factory methods (instance, constant, identity).
     * Sets the size field by iterating through the Indexes objects in the key set and finding the greatest index. This occurs upon instantiation.
     * @param matrix the Map object representing the underlying data structure.
     * @param ring the Ring object used to represent the zero of type T and aid in intermediate operations. 
     */
    private SparseMatrixMap(Map<Indexes, T> matrix, Ring<T> ring) {
        //setting class fields
        this.matrix = matrix;
        this.size = Collections.max(matrix.keySet());
        this.ring = ring;
    }

    /**
     * Gets the matrix size.
     * @return the greatest index of the matrix, computed by the constructor upon instantiation.
     */
    @Override
    public Indexes size() {
        return size;
    }

    /**
     * Returns the value of this SparseMatrixMap at the specified Indexes object in the argument.
     * @param indexes the Indexes object containing the row and column of the desired return value.
     * @return the corresponding value mapped by indexes; zero if the value is null at the specified index.
     */
    @Override
    public T value(Indexes index) {
        //null check
        Objects.requireNonNull(index, "indexes cannot be null");

        //error handling for index greater than size
        T value = matrix.get(index);
        return (value == null) ? ring.zero() : value;
    }

    /**
     * Creates an instance of a SparseMatrixMap by creating a stream of Indexes from (0, 0) to the specified rows and columns, 
     * and mapping each Indexes object in the stream to a specified value defined by the valueMapper function. If that value is zero, it is not added as an entry to the SparseMatrixMap.
     * @param <S> the static type S.
     * @param rows the number of rows the SparseMatrixMap should have. 
     * @param columns the number of columns the SparseMatrixMap should have. 
     * @param ring the Ring object used to represent the zero of type S and aid in intermediate operations. 
     * @param valueMapper a functional interface which uses a lambda expression to set the values of each Indexes object in the MatrixMap.
     * The expression works as follows: ((index) -> index.rows + index.column). In this example, each index is mapped to a value representing
     * the sum of its row and column, but it can be mapped to anything of type S.
     * @return a new SparseMatrixMap with the specified size and populated by the user defined function.
     * @throws InvalidLengthException a precondition is that the rows and columns in the argument must be valid.
     */
    public static <S> SparseMatrixMap<S> instance(int rows, int columns,  Ring<S> ring, Function<Indexes, S> valueMapper) {

        //null check
        Objects.requireNonNull(ring, "ring cannot be null");
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");
        InvalidLengthException.requireNonEmpty(Cause.ROW, rows); //checks if the rows are valid
        InvalidLengthException.requireNonEmpty(Cause.COLUMN, columns); //checks if the columns are valid

        Map<Indexes, S> matrix = new HashMap<>(); //creating the map
        List<Indexes> indexes = Indexes.stream(rows, columns).collect(Collectors.toList()); //creating a list of indexes

        //populating the map with the applied mappings at each index, excluding mappings to zero
        for (Indexes index : indexes) {
            S value = valueMapper.apply(index);
            if (!value.equals(ring.zero())) {
                matrix.put(index, value);
            }
        }
        return new SparseMatrixMap<>(Map.copyOf(matrix), ring);
    }

    /**
     * Creates an instance of a SparseMatrixMap by creating a stream of Indexes from (0, 0) to the specified Indexes size,
     * and mapping each Indexes object in the stream to a specified value defined by the valueMapper function. If that value is zero, it is not added as an entry to the SparseMatrixMap.
     * @param <S> the static type S.
     * @param size the Indexes object containing the number of rows and columns that the SparseMatrixMap should have.
     * @param ring the Ring object used to represent the zero of type S and aid in intermediate operations. 
     * @param valueMapper a functional interface which uses a lambda expression to set the values of each Indexes object in the MatrixMap.
     * The expression works as follows: ((index) -> index.rows + index.column). In this example, each index is mapped to a value representing
     * the sum of its row and column, but it can be mapped to anything of type S.
     * @return a new SparseMatrixMap with the specified size and populated by the user defined function.
     * @throws InvalidLengthException a precondition is that the size in the argument must be valid.
     */
    public static <S> SparseMatrixMap<S> instance(Indexes size, Ring<S> ring, Function<Indexes, S> valueMapper) {

        //null checks
        Objects.requireNonNull(size, "size cannot be null");
        Objects.requireNonNull(ring, "ring cannot be null");
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");
    
        //calling the foundational instance method
        return SparseMatrixMap.instance(size.row(), size.column(), ring, valueMapper);
    }

    /**
     * Creates an instance of a SparseMatrixMap by creating a stream of Indexes from (0, 0) to the specified int size which represents both the rows and columns of the SparseMatrixMap, 
     * and mapping each and every Indexes object in the stream to the S value passed into the argument, unless that value is zero. This represents a constant matrix.
     * @param <S> the static type S.
     * @param size the integer value representing both the number of rows and columns in this SparseMatrixMap.
     * @param ring the Ring object used to represent the zero of type S and aid in intermediate operations. 
     * @param value the value that the MatrixMap is being populated with. 
     * @return a new MatrixMap with the specified size and populated with the specifeid value at each index. 
     * @throws IllegalArgumentException a precondition is that the SparseMatrixMap cannot be populated with a constant value of zero.
     * @throws InvalidLengthException a precondition is that the size in the argument must be valid.
     */
    public static <S> SparseMatrixMap<S> constant(int size, Ring<S> ring, S value) {

        //null check
        Objects.requireNonNull(value, "value cannot be null");
        Objects.requireNonNull(ring, "ring cannot be null");

        //edge case: user attempts to populate a constant sparse matrix with zero
        if (value.equals(ring.zero())) {
            throw new IllegalArgumentException("cannot populate a sparse matrix with 0");
        }

        //calling the foundational instance method
        return instance(size, size, ring, (index) -> value);
    }

    /**
     * Creates an instance of a SparseMatrixMap by creating a stream of Indexes from (0, 0) to the specified int size which represents both the rows and columns of the SparseMatrixMap, 
     * and only mapping Indexes object to the specified identity if it lies on the diagonal. This represents an identity matrix.
     * @param <S> the static type S.
     * @param size the integer value representing both the number of rows and columns in this SparseMatrixMap.
     * @param ring the Ring object used to represent the zero and multiplicative identity of type S and aid in intermediate operations. 
     * @return a new SparseMatrixMap representing an identity matrix, populated with the identity along the diagonal, and no other entries. 
     */
    public static <S> SparseMatrixMap<S> identity(int size, Ring<S> ring) {

        //null check
        Objects.requireNonNull(ring, "ring cannot be null");

        //calling the foundational instance method
        return instance(size, size, ring, (index) -> (index.areDiagonal()) ? ring.identity() : ring.zero());
    }


    /**
     * Computes Matrix addition by creating a new instance of a SparseMatrixMap, and using the specified BinaryOperator interface in the argument to apply addition between this SparseMatrixMap and the other Matrix. 
     * One unobvious precondition is that the apply method of the BinaryOperator should model addition, perhaps using a Ring object. 
     * @param other the other Matrix being added
     * @param plus the BinaryOperator interface which computes the addition of each element in the addend Matrices
     * @return a new SparseMatrixMap which is the sum of this MatrixMap and the other Matrix. 
     * @throws InconsistentSizeException a precondition is that the two matrices being added must be of equal size. 
     */
    @Override
    public Matrix<T> plus(Matrix<T> other, BinaryOperator<T> plus) {
        
        //null checks
        Objects.requireNonNull(other, "other cannot be null");
        Objects.requireNonNull(plus, "plus cannot be null");
        //consistent size checks
        InconsistentSizeException.requireMatchingSize(this, other); 

        return instance(this.size(), this.ring, (index) -> plus.apply(this.value(index), other.value(index)));
    }
    
    /**
     * Computes Matrix multiplication by creating a new instance of a SparseMatrixMap, and using the specified Ring object to compute the intermediate operations of matrix multiplication. 
     * @param other the other Matrix being multiplied.
     * @param ring a Ring object used for intermediate operations, further defined in the getProductAtIndex method.
     * @return a new MatrixMap which is the product of this MatrixMap and the other Matrix. 
     * @throws NonSquareException a precondition is that both factor matrices must be square matrices. 
     * @throws InconsistentSizeException a precondition is that they must be of equal size. 
     */
    @Override
    public SparseMatrixMap<T> times(Matrix<T> other, Ring<T> ring) {
        
        //null checks
        Objects.requireNonNull(other, "other cannot be null");
        Objects.requireNonNull(ring, "ring cannot be null");

        //size and square checks
        NonSquareException.requireDiagonal(this.size()); //checks if this matrix is a square
        NonSquareException.requireDiagonal(other.size()); //checks if the other matrix is a square
        InconsistentSizeException.requireMatchingSize(this, other); //checks if the matrixes are of equal size

        int length = this.size().row(); //sets the length of the matrixes by accessing the row of the size of this index (can be row or column from either matrix)
        return instance(this.size(), this.ring, (index) -> { //creates an instance of a matrix containing the product

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
     * Returns a string representation of the object that "textually represents" a SparseMatrixMap object.
     * @return a string consisting of each entry (Indexes: value)in the matrix, laid out in order top to bottom and left to right such that it accurately represents a sparse matrix. If the value is zero at a given index, the text representation is a series of spaces.
     * 
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

                if (value != this.ring.zero()) {
                    sb.append("[").append(index.toString()).append("]: ").append(value).append("\t"); //add the entry
                } else {
                    sb.append("          ").append("\t");
                }
            }
            sb.append("\n"); //add a carriage return
        }
        return sb.toString();
    }

    /**
     * Converts this SparseMatrixMap to a MatrixMap by creating an instance of a MatrixMap and mapping each value of this MatrixMap to its counterpart, while mapping null entries to zero.
     * This ensures that if this SparseMatrixMap does not contains an entry, it is created as a zero upon instantiation of a MatrixMap.
     * @param ring a Ring object used to represent the zero of data type T when creating a MatrixMap
     * @return a new MatrixMap containting all entries of this SparseMatrixMap, with the addition of mappings to zero. 
     */
    public MatrixMap<T> convertToStandard(Ring<T> ring) {

        //null check
        Objects.requireNonNull(ring, "ring cannot be null");
        
        return MatrixMap.instance(size(), (index) -> {
            return (!matrix.containsKey(index) ? ring.zero() : value(index));
        });
    }
}
