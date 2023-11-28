package MatrixPackage;

import org.junit.*;

import MatrixPackage.MatrixMap.InconsistentSizeException;
import MatrixPackage.MatrixMap.InvalidLengthException;
import MatrixPackage.MatrixMap.NonSquareException;
import MatrixPackage.MatrixMap.InvalidLengthException.Cause;
import RingPackage.BigIntegerRing;
import RingPackage.IntegerRing;
import RingPackage.Polynomial;
import RingPackage.PolynomialRing;
import RingPackage.Ring;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vidyut Veedgav
 * a tester class for the Matrix classes
 */
public class MatrixTest {

    /**
     * TESTING THE METHODS OF THE INDEXES CLASS
     */


    Indexes index = new Indexes(0, 0);
    /**
     * testing the compareTo method
     * implicitly tests the comparisonResult and compareColumns method
     */

    @Test
    public void testCompareTo() {
        
        Indexes i = new Indexes(2, 2);
        assertTrue(i.compareTo(index) > 0); //testing greater than
        assertTrue(index.compareTo(i) < 0); //testing less than
        i = new Indexes(0, 0);
        assertTrue(i.compareTo(index) == 0); //testing equal to
    }

    /**
     * testing the toString method
     */
    @Test
    public void testToString() {
        assertEquals("0, 0", index.toString());
    }

    /**
     * testing the value method
     */
    @Test
    public void testValue() {
        Integer[][] arr = new Integer[1][1];
        arr[0][0] = 10;
        assertEquals(Integer.valueOf(10), index.value(arr));
    }

    /**
     * testing the areDiagonal method
     */
    @Test
    public void testAreDiagonal() {
        Indexes test1 = new Indexes(0, 0);
        Indexes test2 = new Indexes(3, 4);
        Indexes test3 = new Indexes(5, 5);
        
        assertEquals(true, test1.areDiagonal());        
        assertEquals(false, test2.areDiagonal());
        assertEquals(true, test3.areDiagonal());
    }

    /**
     * testing the stream(Indexes from, Indexes to)
     * implicitly tests the getMin and getMax methods
     */
    @Test
    public void testStream() {
        Indexes from = index;
        Indexes to = new Indexes(3, 3);
        List<Indexes> index_list = Indexes.stream(from, to).collect(Collectors.toList());
        assertEquals("0, 0", index_list.get(0).toString());
        assertEquals("0, 1", index_list.get(1).toString());
        assertEquals("0, 2", index_list.get(2).toString());
        assertEquals("0, 3", index_list.get(3).toString());
        assertEquals("1, 1", index_list.get(5).toString());
        assertEquals("2, 0", index_list.get(8).toString());
        assertEquals("2, 2", index_list.get(10).toString());
    }

    /**
     * testing the stream(Indexes size) method
     */
    @Test
    public void testStreamTwo() {
        Indexes size = new Indexes(3, 3);
        List<Indexes> index_list = Indexes.stream(size).collect(Collectors.toList());
        assertEquals("0, 0", index_list.get(0).toString());
        assertEquals("0, 1", index_list.get(1).toString());
        assertEquals("0, 2", index_list.get(2).toString());
        assertEquals("0, 3", index_list.get(3).toString());
        assertEquals("1, 1", index_list.get(5).toString());
        assertEquals("2, 0", index_list.get(8).toString());
        assertEquals("2, 2", index_list.get(10).toString());
    }

    /**
     * testing the stream(int rows, int columns) method
     */
    @Test
    public void testStreamThree() {
        List<Indexes> index_list = Indexes.stream(3, 3).collect(Collectors.toList());
        assertEquals("0, 0", index_list.get(0).toString());
        assertEquals("0, 1", index_list.get(1).toString());
        assertEquals("0, 2", index_list.get(2).toString());
        assertEquals("0, 3", index_list.get(3).toString());
        assertEquals("1, 1", index_list.get(5).toString());
        assertEquals("2, 0", index_list.get(8).toString());
        assertEquals("2, 2", index_list.get(10).toString());
    }

    /**
     * testing the value(MatrixMap<S> matrix) method
     */
    @Test
    public void testValueaTwo() {
        MatrixMap<Integer> matrix = MatrixMap.instance(3, 4, (index) -> index.row() + index.column());
        Indexes test = new Indexes(2, 1);
        assertEquals(Integer.valueOf(3), test.value(matrix));
        test = new Indexes(1, 1);
        assertEquals(Integer.valueOf(2), test.value(matrix));
        test = new Indexes(3, 2);
        assertEquals(Integer.valueOf(5), test.value(matrix));
    }

    /**
     * TESTING THE MMETHODS OF THE MATRIXMAP CLASS
     */

    Indexes ind = new Indexes(2, 2);

     /**
      * testing the size method
      */
    @Test
    public void testSize() {

        MatrixMap<Integer> matrix = MatrixMap.constant(5, 100);
        assertEquals("5, 5", matrix.size().toString());
        MatrixMap<Integer> matrix2 = MatrixMap.constant(12, 100);
        assertEquals("12, 12", matrix2.size().toString());
    }

    /**
      * testing the toString method
      * implicitly tests the toGrid method
      */
    @Test
    public void testMatrixToString() {
        MatrixMap<Integer> matrix = MatrixMap.constant(1, 10);
        assertEquals("[0, 0]: 10\t[0, 1]: 10\t\n" + //
                "[1, 0]: 10\t[1, 1]: 10\t\n" + //
                "", matrix.toString());
    }

    /**
      * testing the value(Indexes index) method
      */
    @Test
    public void testValueTwo() {
        MatrixMap<Integer> matrix = MatrixMap.instance(2, 2, (index) -> index.row() + index.column());
        Indexes index = new Indexes(1, 2);
        assertEquals(Integer.valueOf(3), matrix.value(index));
    }

    /**
      * testing the value(int row, int column) method
      */
    @Test
    public void testValueThree() {
        MatrixMap<Integer> matrix = MatrixMap.instance(2, 2, (index) -> index.row() + index.column());
        Indexes index = new Indexes(1, 2);
        assertEquals(Integer.valueOf(3), matrix.value(index.row(), index.column()));
    }
    
    /**
     * TESTING THE METHODS OF THE STATIC CLASS INVALIDLENGTHEXCEPTION
     */

    /**
     * testing the cause method
     */
    @Test
    public void testCause() {
        Cause cause = Cause.COLUMN;
        InvalidLengthException e = new InvalidLengthException(cause, 5);
        assertEquals(Cause.COLUMN, e.cause());
    }

    /**
     * testing the length method
     */
    @Test
    public void testLength() {
        Cause cause = Cause.COLUMN;
        InvalidLengthException e = new InvalidLengthException(cause, 5);
        assertEquals(Integer.valueOf(5), e.length());
    }

    /**
     * testing the requireNonEmpty method
     * implicitly tests the checkValidLength method
     */
    @Test
    public void testRequireNonEmpty() {

        Cause cause = Cause.COLUMN;
        assertEquals(5, InvalidLengthException.requireNonEmpty(cause, 5));
        assertThrows(IllegalArgumentException.class, () -> InvalidLengthException.requireNonEmpty(cause, -1));
    }

    /**
     * testing the requireMatchingSize method
     */
    @Test
    public void testRequireMatchingSize() {

        Matrix<Integer> m1 = MatrixMap.instance(2, 2, (index) -> 1);
        Matrix<Integer> m2 = MatrixMap.instance(2, 2, (index) -> 1);
        Matrix<Integer> m3 = MatrixMap.instance(4, 4, (index) -> 1);
        assertEquals(new Indexes(2, 2), InconsistentSizeException.requireMatchingSize(m1, m2));
        assertThrows(IllegalArgumentException.class, () -> InconsistentSizeException.requireMatchingSize(m1, m3));
        InconsistentSizeException e = new InconsistentSizeException(new Indexes(1, 1), new Indexes(2, 2));
        assertEquals(new Indexes(1, 1), e.getThisIndex());
        assertEquals(new Indexes(2, 2), e.getOtherIndex());
    }

    /**
     * testing the requireDiagonal method
     */
    @Test
    public void testRequireDiagonal() {
        NonSquareException e = new NonSquareException(new Indexes(2, 2));
        assertEquals(new Indexes(2, 2), e.getIndexes());
        assertEquals(new Indexes(2, 2), NonSquareException.requireDiagonal(new Indexes(2, 2)));
        assertThrows(IllegalStateException.class, () -> NonSquareException.requireDiagonal(new Indexes(0, 1)));
    }

    /**
     * testing the convertToSparse method
     */
    @Test
    public void testConvertToSparse() {
        SparseMatrixMap<Integer> sparse = MatrixMap.instance(new Indexes(2, 2), (index) -> index.row()).convertToSparse(ring);
        assertEquals(null, sparse.value(new Indexes(0, 0)));
        assertEquals(null, sparse.value(new Indexes(0, 1)));
        assertEquals(null, sparse.value(new Indexes(0, 2)));
        assertEquals(Integer.valueOf(1), sparse.value(new Indexes(1, 0)));
    }

    /**
     * *********************************************************************
     */

    /**
     * TESTING THE REMAINING METHODS OF THE MATRIXMAP CLASS
     */

    /**
     * testing the instance(int rows, int columns, valueMapper) method
     * implicitly tests the populate method
     */
    @Test
    public void testInstance() {
        MatrixMap<Integer> matrix = MatrixMap.instance(2, 3, (index) -> index.row() + index.column());
        assertEquals(Integer.valueOf(4), matrix.value(2, 2));
        assertEquals(Integer.valueOf(3), matrix.value(1, 2));
    }
    
    /**
     * testing the instance(Indexes size, valueMapper) method
     */
    @Test
    public void testInstanceTwo() {
        Indexes size = new Indexes(2, 3);
        MatrixMap<Integer> matrix = MatrixMap.instance(size, (index) -> index.row() + index.column());
        assertEquals(Integer.valueOf(4), matrix.value(2, 2));
        assertEquals(Integer.valueOf(3), matrix.value(1, 2));
    }

    /**
     * testing the constant method
     */
    @Test
    public void testConstant() {
        MatrixMap<Integer> matrix = MatrixMap.constant(3, 100);
        assertTrue(matrix.getMap().containsValue(100));
        assertFalse(matrix.getMap().containsValue(10));
        assertFalse(matrix.getMap().containsValue(20));
        assertFalse(matrix.getMap().containsValue(8));
        assertFalse(matrix.getMap().containsValue(96));
    }

    /**
     * testing the identity method
     */
    @Test
    public void testIdentity() {
        MatrixMap<Integer> matrix = MatrixMap.identity(3, Integer.valueOf(0), Integer.valueOf(5));
        //checking diagonals
        assertEquals(Integer.valueOf(5), matrix.value(0, 0));
        assertEquals(Integer.valueOf(5), matrix.value(1, 1));
        assertEquals(Integer.valueOf(5), matrix.value(2, 2));
        assertEquals(Integer.valueOf(5), matrix.value(3, 3));

        assertEquals(Integer.valueOf(0), matrix.value(0, 1));
        assertEquals(Integer.valueOf(0), matrix.value(2, 3));
        assertEquals(Integer.valueOf(0), matrix.value(3, 1));
    }

    /**
     * testing the from method
     */
    @Test
    public void testFrom() {
        Integer[][] testArr = new Integer[2][2];
        testArr[0][0] = 100;
        testArr[0][1] = 200;
        testArr[1][0] = 300;
        testArr[1][1] = 400;

        MatrixMap<Integer> matrix = MatrixMap.from(testArr);
        assertEquals(Integer.valueOf(100), matrix.value(0, 0));
        assertEquals(Integer.valueOf(200), matrix.value(0, 1));
        assertEquals(Integer.valueOf(300), matrix.value(1, 0));
        assertEquals(Integer.valueOf(400), matrix.value(1, 1));
    }

    /**
     * testing the plus method
     */
    @Test
    public void testPlus() {

        //TESTING FOR MATRIXES OF BIGINTEGERS

        //matrixes with the same values
        MatrixMap<BigInteger> m = MatrixMap.constant(2, BigInteger.valueOf(1));
        MatrixMap<BigInteger> n = MatrixMap.constant(2, BigInteger.valueOf(1));

        Ring<BigInteger> bigRing = new BigIntegerRing();
        MatrixMap<BigInteger> sum = m.plus(n, (x, y) -> bigRing.sum(x, y));
        assertTrue(sum.value(0, 0).equals(BigInteger.valueOf(2)));
        assertTrue(sum.value(0, 1).equals(BigInteger.valueOf(2)));
        assertTrue(sum.value(1, 0).equals(BigInteger.valueOf(2)));
        assertTrue(sum.value(1, 1).equals(BigInteger.valueOf(2)));
        
        //matrixes with different values
        MatrixMap<BigInteger> x = MatrixMap.constant(2, BigInteger.valueOf(1));
        MatrixMap<BigInteger> y = MatrixMap.constant(2, BigInteger.valueOf(2));

        MatrixMap<BigInteger> sum2 = x.plus(y, (c, d) -> bigRing.sum(c, d));
        assertTrue(sum2.value(0, 0).equals(BigInteger.valueOf(3)));
        assertTrue(sum2.value(0, 1).equals(BigInteger.valueOf(3)));
        assertTrue(sum2.value(1, 0).equals(BigInteger.valueOf(3)));
        assertTrue(sum2.value(1, 1).equals(BigInteger.valueOf(3)));

        //TESTING FOR MATRIXES OF POLYNOMIALS

        //matrixes with the same values
        List<BigInteger> bigList = new ArrayList<>(List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)));
        Polynomial<BigInteger> poly1 = Polynomial.from(bigList);
        Polynomial<BigInteger> poly2 = Polynomial.from(bigList);

        MatrixMap<Polynomial<BigInteger>> pm1 = MatrixMap.constant(2, poly1);
        MatrixMap<Polynomial<BigInteger>> pm2 = MatrixMap.constant(2, poly2);

        Ring<Polynomial<BigInteger>> polyRing = PolynomialRing.instance(bigRing);
        MatrixMap<Polynomial<BigInteger>> polySum = pm1.plus(pm2, (q, w) -> polyRing.sum(q, w));

        assertEquals(List.of(BigInteger.valueOf(2), BigInteger.valueOf(4), BigInteger.valueOf(6)), polySum.value(0, 0).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(2), BigInteger.valueOf(4), BigInteger.valueOf(6)), polySum.value(0, 1).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(2), BigInteger.valueOf(4), BigInteger.valueOf(6)), polySum.value(1, 0).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(2), BigInteger.valueOf(4), BigInteger.valueOf(6)), polySum.value(1, 1).getCoefficients());

        //matrixes with different values
        List<BigInteger> bigList2 = new ArrayList<>(List.of(BigInteger.valueOf(2), BigInteger.valueOf(4), BigInteger.valueOf(6)));
        poly2 = Polynomial.from(bigList2);
        pm2 = MatrixMap.constant(2, poly2);

        MatrixMap<Polynomial<BigInteger>> polySum2 = pm1.plus(pm2, (q, w) -> polyRing.sum(q, w));

        assertEquals(List.of(BigInteger.valueOf(3), BigInteger.valueOf(6), BigInteger.valueOf(9)), polySum2.value(0, 0).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(3), BigInteger.valueOf(6), BigInteger.valueOf(9)), polySum2.value(0, 1).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(3), BigInteger.valueOf(6), BigInteger.valueOf(9)), polySum2.value(1, 0).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(3), BigInteger.valueOf(6), BigInteger.valueOf(9)), polySum2.value(1, 1).getCoefficients());
    }

    /**
     * testing the times method
     */
    @Test
    public void testTimes() {

        //testing for Matrixes of BigIntegers
        MatrixMap<BigInteger> m = MatrixMap.instance(new Indexes(2, 2), (index) -> BigInteger.valueOf(index.row() + index.column()));
        MatrixMap<BigInteger> n = MatrixMap.instance(new Indexes(2, 2), (index) -> BigInteger.valueOf(index.row() + index.column() + 5));
        Ring<BigInteger> bigRing = new BigIntegerRing();

        MatrixMap<BigInteger> product = m.times(n, bigRing);

        assertEquals(BigInteger.valueOf(20), product.value(0, 0));
        assertEquals(BigInteger.valueOf(23), product.value(0, 1));
        assertEquals(BigInteger.valueOf(38), product.value(1, 0));
        assertEquals(BigInteger.valueOf(44), product.value(1, 1));

        //testing for Matrixes of Polynomials
        List<BigInteger> bigList = new ArrayList<>(List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)));
        List<BigInteger> bigList2 = new ArrayList<>(List.of(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6)));

        Polynomial<BigInteger> poly1 = Polynomial.from(bigList);
        Polynomial<BigInteger> poly2 = Polynomial.from(bigList2);

        Ring<Polynomial<BigInteger>> polyRing = PolynomialRing.instance(bigRing);

        MatrixMap<Polynomial<BigInteger>> m1 = MatrixMap.constant(2, poly1);
        MatrixMap<Polynomial<BigInteger>> m2 = MatrixMap.constant(2, poly2);

        MatrixMap<Polynomial<BigInteger>> product2 = m1.times(m2, polyRing);

        assertEquals(List.of(BigInteger.valueOf(12), BigInteger.valueOf(39), BigInteger.valueOf(84), BigInteger.valueOf(81), BigInteger.valueOf(54)), product2.value(0, 0).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(12), BigInteger.valueOf(39), BigInteger.valueOf(84), BigInteger.valueOf(81), BigInteger.valueOf(54)), product2.value(0, 1).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(12), BigInteger.valueOf(39), BigInteger.valueOf(84), BigInteger.valueOf(81), BigInteger.valueOf(54)), product2.value(1, 0).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(12), BigInteger.valueOf(39), BigInteger.valueOf(84), BigInteger.valueOf(81), BigInteger.valueOf(54)), product2.value(1, 1).getCoefficients());
    }

    /**
     * TESTING THE METHODS OF THE SPARSEMATRIXMAP CLASS
     */

    Ring<Integer> ring = new IntegerRing(); 
    /**
     * testing the size method
     */
    @Test
    public void testSparseSize() {
        assertEquals(new Indexes(2, 2), SparseMatrixMap.instance(new Indexes(2, 2), ring, (index) -> Integer.valueOf(1)).size());
    }

    /**
     * testing the constant method
     */
    @Test
    public void testSparseConstant() {
        SparseMatrixMap<Integer> sparse = SparseMatrixMap.constant(1, ring, Integer.valueOf(5));
        assertEquals(Integer.valueOf(5), sparse.value(new Indexes(0, 0)));
        assertEquals(Integer.valueOf(5), sparse.value(new Indexes(0, 1)));
        assertEquals(Integer.valueOf(5), sparse.value(new Indexes(1, 0)));
        assertEquals(Integer.valueOf(5), sparse.value(new Indexes(1, 1)));
    }

    @Test
    public void testSparseIdentity() {
        SparseMatrixMap<Integer> sparse = SparseMatrixMap.identity(1, ring);
        assertEquals(Integer.valueOf(1), sparse.value(new Indexes(0, 0)));
        assertEquals(Integer.valueOf(1), sparse.value(new Indexes(1, 1)));
    }

    
}


