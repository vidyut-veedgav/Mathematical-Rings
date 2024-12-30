package MatrixPackage;


import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This is a record representing the index of a matrix.
 * It has an int row and int column, which represent the dimentions of the index.
 * @author Vidyut Veedgav
 */
public record Indexes(int row, int column) implements Comparable<Indexes> {

    /**
     * Compares this Indexes object with another by standardizing comparisons to integer format.
     * @return an integer that is positive if this object is greater than the param, negative is less, and zero if equal.
     * @param o the indexes object being compared to.
     */
    @Override
    public int compareTo(Indexes o) {
        //null check
        Objects.requireNonNull(o, "o cannot be null");

        //if this row != o row, return this row - o row, otherwise return this column - o column
        return (this.row() != o.row() ? (this.row() - o.row()) : (this.column() - o.column()));
    }
    
    /**
     * Returns a string representation of the object that "textually represents" an Indexes object.
     * @return the string representation of an Indexes object in the form, "row, column".
     */
    @Override
    public String toString() {
        return (row() + ", " + column());
    }

    /**
     * Returns the value in a matrix corresponding to this index.
     * @param matrix a two dimentional array representing a matrix.
     * @return the value mapped to this index in the argument matrix.
     */
    public <S> S value(S[][] matrix) {

        //null check
        Objects.requireNonNull(matrix, "matrix cannot be null");
        return matrix[row][column];
    }

    /**
     * Returns the value in a matrix corresponding to this index.
     * @param matrix a Matrix object such as MatrixMap or SparseMatrixMap.
     * @return the value mapped to this index in the argument matrix.
     */
    public <S> S value(Matrix<S> matrix) {
        //null check
        Objects.requireNonNull(matrix);
        return matrix.value(this);
    }

    /**
     * Determines if the index is along the matrix diagonal by checking if the row and column fields are equal. If they are, it means that the index lies on the diagonal path of the matrix.
     * @return true if the row is equal to the column; false otherwise.
     */
    public boolean areDiagonal() {
        return row() == column();
    }

    /**
     * Generates a stream of indexes ordered sequentially from the first and second Indexes objects in the argument.
     * @param <T> the static generic type T.
     * @param from the starting Indexes.
     * @param to the ending Indexes.
     * @return a Stream object representing all Indexes between from (inclusive) and to (inclusive).
     */
    public static <T> Stream<Indexes> stream(Indexes from, Indexes to) {
        //null checks
        Objects.requireNonNull(from, "from cannot be null");
        Objects.requireNonNull(from, "to cannot be null");

        //calculating the range values (rows)
        int minRow = Indexes.getMin(from.row(), to.row()); //the minimum row
        int maxRow = Indexes.getMax(from.row(), to.row()); //the maximum row

        //calculating the range values (columns)
        int minColumn = Indexes.getMin(from.column(), to.column()); //the minimum column
        int maxColumn = Indexes.getMax(from.column(), to.column()); //the maximum column

        //streaming the indexes
        return IntStream.rangeClosed(minRow, maxRow) //creating a range of integers from the minimum to maximum row, inclusive
                        .boxed() //wraps the contents of the range into Integers
                        .flatMap((row) -> IntStream.rangeClosed(minColumn, maxColumn) //use flat map to flatten the 2D stream into a single stream
                        .mapToObj((column) -> new Indexes(row, column))); //mapping each row column pair to a new Index
    }

    /**
     * Gets the smaller value out of two integer inputs using the built-in min(int a1, int a2) method of the Math cass
     * @param from the first integer
     * @param to the second integer
     * @return the smaller value
     */
    private static int getMin(int from, int to) {
        return Math.min(from, to);
    }

    /**
     * Get the larget value out of two integer inputs using the built-in max(int a1, int a2) method of the Math cass
     * @param from the first integer
     * @param to the second integer
     * @return the smaller value
     */
    private static int getMax(int from, int to) {
        return Math.max(from, to);
    }

    /**
     * Generates a stream of indexes ordered sequentially from the index (0, 0) to the index representing the size specified in the parameter.
     * @param size the ending Indexes.
     * @return a Stream object representing all Indexes between (0, 0) (inclusive) and size (inclusive).
     */
    public static Stream<Indexes> stream(Indexes size) {
        //null checks
        Objects.requireNonNull(size, "size cannot be null");
        
        return stream(new Indexes(0, 0), size);    
    }

    /**
     * Generates a stream of Indexes between (0, 0) and a new Indexes object with rows and columns specified in the arguments.
     * @param <T> the static generic type T.
     * @param rows the rows of the end Indexes.
     * @param columns the columns of the end Indexes.
     * @return a Stream object representing the streamed Indexes from (0, 0) (inclusive), to a new Indexes(rows, columns) (inclusive).
     */
    public static <T> Stream<Indexes> stream(int rows, int columns) {

        return stream(new Indexes(0, 0), new Indexes(rows, columns)); 
    }
}
