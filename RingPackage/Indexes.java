package RingPackage;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Vidyut Veedgav
 * a record representing the index of a matrix
 */
public record Indexes(int row, int column) implements Comparable<Indexes> {

    /**
     * overriding the compareTo method of Comparable
     */
    @Override
    public int compareTo(Indexes o) {
        //null check
        Objects.requireNonNull(o, "o cannot be null");
        if (this.row > o.row) {
            return 1;
        }
        else if (this.row < o.row) {
            return -1;
        }
        else {
            return compareComlumns(o);
        }
    }

    @Override
    public String toString() {
        return (row() + ", " + column());
    }

    /**
     * a helper method to compare indexes by column
     * @param o
     * @return
     */
    private int compareComlumns(Indexes o) {

        //null check
        assert o != null : "o cannot be null";
        if (column > o.column) {
            return 1;
        }
        else if (column < o.column) {
            return -1;
        }
        else {
            return 0;
        }
    }

    /**
     * a method to return the matrix entry corresponding to this index
     * @param matrix
     * @return
     */
    public <T> T value(T[][] matrix) {
        return matrix[row][column];
    }

    /**
     * a method to determine if the index is along the matrix diagonal
     * @return true if diagonal
     */
    public boolean areDiagonal() {
        return row() == column();
    }

    /**
     * a static method to generate all the indexes in a given range
     */
    public static <T> Stream<Indexes> stream(Indexes from, Indexes to) {
        //null checks
        Objects.requireNonNull(from, "from cannot be null");
        Objects.requireNonNull(from, "to cannot be null");

        //calculating the range values
        int minRow = Math.min(from.row(), to.row());
        int maxRow = Math.max(from.row(), to.row());
        int minColumn = Math.min(from.column(), to.column());
        int maxColumn = Math.max(from.column(), to.column());

        //streaming the indexes
        return IntStream.rangeClosed(minRow, maxRow)
                        .boxed() //wraps the contents of the range into Integers
                        .flatMap((row) -> IntStream.rangeClosed(minColumn, maxColumn) //use flat map to flatten the 2D stream into a single stream
                        .mapToObj(column -> new Indexes(row, column))); //mapping each row column pair to a new Index
    }

    /**
     * overloading the stream mehtod to generate indexes between (0, 0) and the size endpoint
     */
    public static Stream<Indexes> stream(Indexes size) {
        //null checks
        Objects.requireNonNull(size, "size cannot be null");
        
        return stream(new Indexes(0, 0), size);    
    }

    /**
     * overloading the stream mehtod to generate indexes between (0, 0) and the (rows, columns) endpoint
     */
    public static <T> Stream<Indexes> stream(int rows, int columns) {

        return stream(new Indexes(0, 0), new Indexes(rows, columns)); 
    }

    public static void main(String[] args) {

        Indexes index = new Indexes(1, 2);
        Indexes index2 = new Indexes(3, 4);
        System.out.println(Indexes.stream(index, index2).collect(Collectors.toList()));
    }
}
