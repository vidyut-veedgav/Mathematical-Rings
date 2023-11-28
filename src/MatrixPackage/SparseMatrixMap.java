package MatrixPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import MatrixPackage.MatrixMap.InconsistentSizeException;
import MatrixPackage.MatrixMap.InvalidLengthException;
import MatrixPackage.MatrixMap.InvalidLengthException.Cause;
import MatrixPackage.MatrixMap.NonSquareException;
import RingPackage.IntegerRing;
import RingPackage.Ring;
import RingPackage.Rings;

public class SparseMatrixMap<T> implements Matrix<T> {

    private final Map<Indexes, T>  matrix; //a field representing the matrix
    private final Indexes size; //a field representing the size of the matrix
    private final Ring<T> ring; //a field representing a ring used for intermediate operations
    private final List<Indexes> nonZeroIndexes; //a field representing the indexes which are mapped to nonzero values

    /**
     * constructor
     */
    private SparseMatrixMap(Map<Indexes, T> matrix, Ring<T> ring) {
        this.matrix = matrix;
        this.size = Collections.max(matrix.keySet());
        this.ring = ring;
        
        //creates a list of the non zero indexes in the matrix
        nonZeroIndexes = matrix.keySet().stream()
                                .filter(key -> !value(key).equals(ring.zero()))
                                .collect(Collectors.toList());
    }

    /**
     * finds the intersection of nonzero indexes between this matrix and another
     * @param other
     * @return
     */
    private List<Indexes> intersectionNonZeroIndexes(SparseMatrixMap<T> other) {
        List<Indexes> result = new ArrayList<>();
        for (Indexes index : nonZeroIndexes) {
            if (other.nonZeroIndexes.contains(index)) {
                result.add(index);
            }
        }
        return result;
    }

    /**
     * finds the union of nonzero indexes between this matrix and another
     * @param other
     * @return
     */
    private Set<Indexes> unionNonZeroIndexes(SparseMatrixMap<T> other) {
        List<Indexes> resultList = new ArrayList<>(this.nonZeroIndexes);
        resultList.addAll(new ArrayList<>(other.nonZeroIndexes));
        return new HashSet<>(resultList);
    }

    /**
     * a method to get the size of this SparseMatrixMap
     */
    @Override
    public Indexes size() {
        return size;
    }

    /**
     * a method to return the value of this SparseMatrixMap at a particular index
     * OPTION 1: add ring param to the interface method
     * OPTION 2: overload value method
     */
    @Override
    public T value(Indexes index) {
        
        //null check
        Objects.requireNonNull(index, "indexes cannot be null");
        T value = matrix.get(index);
        return (value == null) ? ring.zero() : value;
    }

    /**
     * a foundational instance method for the SparseMatrixMap
     * @param <S>
     * @param rows
     * @param columns
     * @param valueMapper
     * @return
     */
    public static <S> SparseMatrixMap<S> instance(int rows, int columns,  Ring<S> ring, Function<Indexes, S> valueMapper) {

        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");
        InvalidLengthException.requireNonEmpty(Cause.ROW, rows); //checks if the rows are valid
        InvalidLengthException.requireNonEmpty(Cause.COLUMN, columns); //checks if the columns are valid

        Map<Indexes, S> matrix = new HashMap<>(); //creating the map
        List<Indexes> indexes = Indexes.stream(rows, columns).collect(Collectors.toList()); //creating a list of indexes

        for (Indexes index : indexes) {
            S value = valueMapper.apply(index);
            if (!value.equals(ring.zero())) {
                matrix.put(index, value);
            }
        }
        return new SparseMatrixMap<>(Map.copyOf(matrix), ring);
    }

    /**
     * overloading the instance method to accept an Indexes object as a parameter
     * @param <S>
     * @param size
     * @param valueMapper
     * @return a MatrixMap
     */
    public static <S> SparseMatrixMap<S> instance(Indexes size, Ring<S> ring, Function<Indexes, S> valueMapper) {

        //null checks
        Objects.requireNonNull(size, "size cannot be null");
        Objects.requireNonNull(valueMapper, "valueMapper cannot be null");
    
        //calling the foundational instance method
        return SparseMatrixMap.instance(size.row(), size.column(), ring, valueMapper);
    }

    /**
     * a method to return a new sparse matrix populated with a constant non-zero value
     * @param <S>
     * @param size
     * @param value
     * @return
     */
    public static <S> SparseMatrixMap<S> constant(int size, Ring<S> ring, S value) {

        //null check
        Objects.requireNonNull(value, "value cannot be null");

        //TODO implement error handling to disallow calling this method with a value of 0

        //calling the foundational instance method
        return instance(size, size, ring, (index) -> value);
    }

    /**
     * a method to return a new square sparse matrix populated with the identity along the diagonal
     * @param <S>
     * @param size
     * @param zero
     * @param identity
     * @return a new MatrixMap
     */
    public static <S> SparseMatrixMap<S> identity(int size, Ring<S> ring) {

        //null checks
        Objects.requireNonNull(ring.zero(), "zero cannot be null");
        Objects.requireNonNull(ring.identity(), "identity cannot be null");

        //calling the foundational instance method
        return instance(size, size, ring, (index) -> (index.areDiagonal()) ? ring.identity() : ring.zero());
    }


    /**
     * a method to add this matrix to another
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
     * a method to multiply this matrix with another
     * UNFINISHED, DON'T KNOW WHAT TO DO HERE LOLZ
     */

    //find intersection, for each value at intersection indexes, compute the product
    @Override
    public SparseMatrixMap<T> times(Matrix<T> other, Ring<T> ring) {
        
        //null checks
        Objects.requireNonNull(other, "other cannot be null");
        Objects.requireNonNull(ring, "ring cannot be null");

        //size checking
        NonSquareException.requireDiagonal(this.size()); //checks if this matrix is a square
        NonSquareException.requireDiagonal(other.size()); //checks if the other matrix is a square
        InconsistentSizeException.requireMatchingSize(this, other); //checks if the matrixes are of equal size

        int length = this.size().row(); //sets the length of the matrixes by accessing the row of the size of this index (can be row or column from either matrix)
        return instance(this.size(), this.ring, (index) -> { //creates an instance of a matrix containing the product

            if (this.value(index).equals(0) || other.value(index).equals(0)) {
                return ring.zero();
            }
            List<T> products = new ArrayList<>();
            //indexes until length is reached and adds to the product list the element at the row of this and column of other
            for (int i = 0; i <= length; i++) {
                products.add(ring.product(this.value(new Indexes(index.row(), i)), other.value(new Indexes(i, index.column()))));
            }
            return Rings.sum(products, ring); //sums the elements of the product list
        });
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
     * a method to convert this matrix to a standard matrix
     * @param ring
     * @return
     */
    public MatrixMap<T> convertToStandard(Ring<T> ring) {
        
        return MatrixMap.instance(size(), (index) -> {
            return (!matrix.containsKey(index) ? ring.zero() : value(index));
        });
    }

    public static void main(String[] args) {
        
        Ring<Integer> ring = new IntegerRing();
        Matrix<Integer> sparse = SparseMatrixMap.instance(2, 2, ring, (index) -> (index.row()));
        System.out.println(sparse);
        Matrix<Integer> sparse2 = SparseMatrixMap.instance(2, 2, ring, (index) -> (index.column()));
        System.out.println(sparse2);

        sparse = SparseMatrixMap.constant(2, ring, 1);
        //System.out.println(sparse);

        sparse = SparseMatrixMap.identity(2, ring);

        //System.out.println(SparseMatrixMap.instance(2, 2, ring, (index) -> (index.column())));
        //System.out.println(SparseMatrixMap.instance(2, 2, ring, (index) -> (index.column())).convertToStandard(ring));
        //System.out.println(MatrixMap.instance(2, 2, (index) -> (index.column())).convertToSparse(ring));

        Matrix<Integer> s1 = SparseMatrixMap.instance(new Indexes(2, 2), ring, (index) -> index.column());
        Matrix<Integer> s2 = SparseMatrixMap.instance(new Indexes(2, 2), ring, (index) -> index.row());

        //TODO make plus and times methods more efficient and implement
        //System.out.println(s1.plus(s2, (x, y) -> ring.sum(x, y)));
        //System.out.println(s1.times(s2, intRing));
        //System.out.println(s1.value(new Indexes(0, 0)));
    }
}
