package RingPackage;

import org.junit.*;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vidyut Veedgav
 * a tester class for the Ring classes
 */
public class RingTesting {

    //lists that store each of the number types
    List<Integer> intList; 
    List<BigInteger> bigIntList;
    List<Double> doubleList = new ArrayList<>(Arrays.asList());

    /**
     * testing the sum method
     */
    @Test
    public void testSum() {

        //testing for the Integer type
        Ring<Integer> intRing = new IntegerRing(); //creating the IntegerRing instance

        //test 0
        intList = new ArrayList<>(Arrays.asList());
        assertEquals(Integer.valueOf(0), Rings.sum(intList, intRing));

        //test 1
        intList = new ArrayList<>(Arrays.asList(1));
        assertEquals(Integer.valueOf(1), Rings.sum(intList, intRing));

        //test 1 operation
        intList = new ArrayList<>(Arrays.asList(100, 200));
        assertEquals(Integer.valueOf(300), Rings.sum(intList, intRing));

        //test many
        intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(Integer.valueOf(15), Rings.sum(intList, intRing));

        //testing for the BigInteger type
        Ring<BigInteger> bigIntRing = new BigIntegerRing(); //creating the IntegerRing instance
        bigIntList = new ArrayList<>();

        //test 0
        assertEquals(BigInteger.valueOf(0), Rings.sum(bigIntList, bigIntRing));

        //test 1
        bigIntList.add(BigInteger.valueOf(1));
        assertEquals(BigInteger.valueOf(1), Rings.sum(bigIntList, bigIntRing));

        //test 1 operation
        bigIntList.clear();
        bigIntList.add(BigInteger.valueOf(100));
        bigIntList.add(BigInteger.valueOf(200));
        assertEquals(BigInteger.valueOf(300), Rings.sum(bigIntList, bigIntRing));

        //test many
        bigIntList.clear();
        for (int i = 1; i <= 5; i++) {
            BigInteger b = BigInteger.valueOf(i);
            bigIntList.add(b);
        }
        assertEquals(BigInteger.valueOf(15), Rings.sum(bigIntList, bigIntRing));

        //testing for the Double type
        Ring<Double> doubleRing = new DoubleRing(); //creating the IntegerRing instandouble
        //test 0
        doubleList = new ArrayList<>(Arrays.asList());
        assertEquals(Double.valueOf(0), Rings.sum(doubleList, doubleRing));

        //test 1
        doubleList = new ArrayList<>(Arrays.asList(1.00));
        assertEquals(Double.valueOf(1), Rings.sum(doubleList, doubleRing));

        //test 1 operation
        doubleList = new ArrayList<>(Arrays.asList(100., 200.));
        assertEquals(Double.valueOf(300), Rings.sum(doubleList, doubleRing));

        //test many
        doubleList = new ArrayList<>(Arrays.asList(1.54, 2.63, 3.91, 4.01, 5.99));
        assertEquals(Double.valueOf(18.08), Rings.sum(doubleList, doubleRing));

        //testing for the Polynomial<Integer> type
        Ring<Integer> ring = new IntegerRing();

        //test 0
        Polynomial<Integer> a = Polynomial.from(List.of());
        Polynomial<Integer> b = Polynomial.from(List.of());
        Polynomial<Integer> result = Polynomial.from(a.plus(b, ring).getCoefficients());
        assertEquals(true, result.getCoefficients().isEmpty());

        //test 1
        a = Polynomial.from(List.of(1));
        b = Polynomial.from(List.of(1));
        result = Polynomial.from(a.plus(b, ring).getCoefficients());
        assertEquals(true, result.getCoefficients().get(0).equals(2));

        //test many
        a = Polynomial.from(List.of(1, 2, 3));
        b = Polynomial.from(List.of(4, 5, 6));
        result = Polynomial.from(a.plus(b, ring).getCoefficients());
        assertEquals(List.of(5, 7, 9), result.getCoefficients());

        //test polynomials of different lengths
        a = Polynomial.from(List.of(1, 2, 3, 4, 5));
        b = Polynomial.from(List.of(4, 5, 6));
        result = Polynomial.from(a.plus(b, ring).getCoefficients());
        assertEquals(List.of(5, 7, 9, 4, 5), result.getCoefficients());
        result = Polynomial.from(b.plus(a, ring).getCoefficients());
        assertEquals(List.of(5, 7, 9, 4, 5), result.getCoefficients());

        //testing for the Polynomial<BigInteger> type
        Ring<BigInteger> ring2 = new BigIntegerRing();

        //test 0
        Polynomial<BigInteger> a2 = Polynomial.from(List.of());
        Polynomial<BigInteger> b2 = Polynomial.from(List.of());
        Polynomial<BigInteger> result2 = Polynomial.from(a2.plus(b2, ring2).getCoefficients());
        assertEquals(List.of(), result2.getCoefficients());

        //test 1
        a2 = Polynomial.from(List.of(BigInteger.ONE));
        b2 = Polynomial.from(List.of(BigInteger.ONE));
        result2 = Polynomial.from(a2.plus(b2, ring2).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(2)), result2.getCoefficients());

        //test many
        a2 = Polynomial.from(List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)));
        b2 = Polynomial.from(List.of(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6)));
        result2 = Polynomial.from(a2.plus(b2, ring2).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(5), BigInteger.valueOf(7), BigInteger.valueOf(9)), result2.getCoefficients());

        //test polynomials of different lengths
        a2 = Polynomial.from(List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5)));
        b2 = Polynomial.from(List.of(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6)));
        result2 = Polynomial.from(a2.plus(b2, ring2).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(5), BigInteger.valueOf(7), BigInteger.valueOf(9), BigInteger.valueOf(4), BigInteger.valueOf(5)), result2.getCoefficients());
        result = Polynomial.from(b.plus(a, ring).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(5), BigInteger.valueOf(7), BigInteger.valueOf(9), BigInteger.valueOf(4), BigInteger.valueOf(5)), result2.getCoefficients());


        //testing the Polynomial<Double> type
        Ring<Double> ring3 = new DoubleRing();

        //test 0
        Polynomial<Double> a3 = Polynomial.from(List.of());
        Polynomial<Double> b3 = Polynomial.from(List.of());
        Polynomial<Double> result3 = Polynomial.from(a3.plus(b3, ring3).getCoefficients());
        assertEquals(List.of(), result3.getCoefficients());

        //test 1
        a3 = Polynomial.from(List.of(Double.valueOf(1)));
        b3 = Polynomial.from(List.of(Double.valueOf(1)));
        result3 = Polynomial.from(a3.plus(b3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(2)), result3.getCoefficients());

        //test many
        a3 = Polynomial.from(List.of(Double.valueOf(1), Double.valueOf(2), Double.valueOf(3)));
        b3 = Polynomial.from(List.of(Double.valueOf(4), Double.valueOf(5), Double.valueOf(6)));
        result3 = Polynomial.from(a3.plus(b3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(5), Double.valueOf(7), Double.valueOf(9)), result3.getCoefficients());

        //test polynomials of different lengths
        a3 = Polynomial.from(List.of(Double.valueOf(1), Double.valueOf(2), Double.valueOf(3), Double.valueOf(4), Double.valueOf(5)));
        b3 = Polynomial.from(List.of(Double.valueOf(4), Double.valueOf(5), Double.valueOf(6)));
        result3 = Polynomial.from(a3.plus(b3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(5), Double.valueOf(7), Double.valueOf(9), Double.valueOf(4), Double.valueOf(5)), result3.getCoefficients());
        result3= Polynomial.from(b3.plus(a3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(5), Double.valueOf(7), Double.valueOf(9), Double.valueOf(4), Double.valueOf(5)), result3.getCoefficients());
    }
    /**
     * testing the product method
     */
    @Test
    public void testProduct() {
        
        //testing for the Integer type
        Ring<Integer> intRing = new IntegerRing(); //creating the IntegerRing instance

        //test 0
        intList = new ArrayList<>(Arrays.asList());
        assertEquals(Integer.valueOf(1), Rings.product(intList, intRing));

        //test 1
        intList = new ArrayList<>(Arrays.asList(1));
        assertEquals(Integer.valueOf(1), Rings.product(intList, intRing));

        //test 1 operation
        intList = new ArrayList<>(Arrays.asList(100, 200));
        assertEquals(Integer.valueOf(20000), Rings.product(intList, intRing));

        //test many
        intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(Integer.valueOf(120), Rings.product(intList, intRing));

        //testing for the BigInteger type
        Ring<BigInteger> bigIntRing = new BigIntegerRing(); //creating the IntegerRing instance
        bigIntList = new ArrayList<>();

        //test 0
        assertEquals(BigInteger.valueOf(1), Rings.product(bigIntList, bigIntRing));

        //test 1
        bigIntList.add(BigInteger.valueOf(1));
        assertEquals(BigInteger.valueOf(1), Rings.product(bigIntList, bigIntRing));
        //test 1 operation
        bigIntList.clear();
        bigIntList.add(BigInteger.valueOf(100));
        bigIntList.add(BigInteger.valueOf(200));
        assertEquals(BigInteger.valueOf(20000), Rings.product(bigIntList, bigIntRing));
        //test many
        bigIntList.clear();
        for (int i = 1; i <= 5; i++) {
            BigInteger b = BigInteger.valueOf(i);
            bigIntList.add(b);
        }
        assertEquals(BigInteger.valueOf(120), Rings.product(bigIntList, bigIntRing));

        //testing for the Double type
        Ring<Double> doubleRing = new DoubleRing(); //creating the IntegerRing instandouble
        //test 0
        doubleList = new ArrayList<>(Arrays.asList());
        assertEquals(Double.valueOf(1.0), Rings.product(doubleList, doubleRing));

        //test 1
        doubleList = new ArrayList<>(Arrays.asList(1.00));
        assertEquals(Double.valueOf(1), Rings.product(doubleList, doubleRing));

        //test 1 operation
        doubleList = new ArrayList<>(Arrays.asList(100., 200.));
        assertEquals(Double.valueOf(20000.0), Rings.product(doubleList, doubleRing));

        //test many
        doubleList = new ArrayList<>(Arrays.asList(1.54, 2.63, 3.91, 4.01, 5.99));
        assertEquals(Double.valueOf(380.38591001180004), Rings.product(doubleList, doubleRing));


        //testing the Polynomial<Integer> type
        Ring<Integer> ring = new IntegerRing();

        //test 0
        Polynomial<Integer> a = Polynomial.from(List.of());
        Polynomial<Integer> b = Polynomial.from(List.of());
        Polynomial<Integer> result = Polynomial.from(a.times(b, ring).getCoefficients());
        assertEquals(true, result.getCoefficients().isEmpty());

        //test 1
        a = Polynomial.from(List.of(1));
        b = Polynomial.from(List.of(1));
        result = Polynomial.from(a.times(b, ring).getCoefficients());
        assertEquals(List.of(1), result.getCoefficients());

        //test many
        a = Polynomial.from(List.of(1, 2, 3));
        b = Polynomial.from(List.of(4, 5, 6));
        result = Polynomial.from(a.times(b, ring).getCoefficients());
        assertEquals(List.of(4, 13, 28, 27, 18), result.getCoefficients());

        //test polynomials of different lengths
        a = Polynomial.from(List.of(1, 2, 3, 4, 5));
        b = Polynomial.from(List.of(4, 5, 6));
        result = Polynomial.from(a.times(b, ring).getCoefficients());
        assertEquals(List.of(4, 13, 28, 43, 58, 49, 30), result.getCoefficients());
        result = Polynomial.from(b.times(a, ring).getCoefficients());
        assertEquals(List.of(4, 13, 28, 43, 58, 49, 30), result.getCoefficients());


        //testing for the Polynomial<BigInteger> type
        Ring<BigInteger> ring2 = new BigIntegerRing();

        //test 0
        Polynomial<BigInteger> a2 = Polynomial.from(List.of());
        Polynomial<BigInteger> b2 = Polynomial.from(List.of());
        Polynomial<BigInteger> result2 = Polynomial.from(a2.times(b2, ring2).getCoefficients());
        assertEquals(List.of(), result2.getCoefficients());

        //test 1
        a2 = Polynomial.from(List.of(BigInteger.ONE));
        b2 = Polynomial.from(List.of(BigInteger.ONE));
        result2 = Polynomial.from(a2.times(b2, ring2).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(1)), result2.getCoefficients());

        //test many
        a2 = Polynomial.from(List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3)));
        b2 = Polynomial.from(List.of(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6)));
        result2 = Polynomial.from(a2.times(b2, ring2).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(4), BigInteger.valueOf(13), BigInteger.valueOf(28), BigInteger.valueOf(27), BigInteger.valueOf(18)), result2.getCoefficients());

        //test polynomials of different lengths
        a2 = Polynomial.from(List.of(BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5)));
        b2 = Polynomial.from(List.of(BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6)));
        result2 = Polynomial.from(a2.times(b2, ring2).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(4), BigInteger.valueOf(13), BigInteger.valueOf(28), BigInteger.valueOf(43), BigInteger.valueOf(58), BigInteger.valueOf(49), BigInteger.valueOf(30)), result2.getCoefficients());
        result = Polynomial.from(b.times(a, ring).getCoefficients());
        assertEquals(List.of(BigInteger.valueOf(4), BigInteger.valueOf(13), BigInteger.valueOf(28), BigInteger.valueOf(43), BigInteger.valueOf(58), BigInteger.valueOf(49), BigInteger.valueOf(30)), result2.getCoefficients());


        //testing the Polynomial<Double> type
        Ring<Double> ring3 = new DoubleRing();

        //test 0
        Polynomial<Double> a3 = Polynomial.from(List.of());
        Polynomial<Double> b3 = Polynomial.from(List.of());
        Polynomial<Double> result3 = Polynomial.from(a3.times(b3, ring3).getCoefficients());
        assertEquals(List.of(), result3.getCoefficients());

        //test 1
        a3 = Polynomial.from(List.of(Double.valueOf(1)));
        b3 = Polynomial.from(List.of(Double.valueOf(1)));
        result3 = Polynomial.from(a3.times(b3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(1)), result3.getCoefficients());

         //test many
        a3 = Polynomial.from(List.of(Double.valueOf(1), Double.valueOf(2), Double.valueOf(3)));
        b3 = Polynomial.from(List.of(Double.valueOf(4), Double.valueOf(5), Double.valueOf(6)));
        result3 = Polynomial.from(a3.times(b3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(4), Double.valueOf(13), Double.valueOf(28), Double.valueOf(27), Double.valueOf(18)), result3.getCoefficients());

        //test polynomials of different lengths
        a3 = Polynomial.from(List.of(Double.valueOf(3), Double.valueOf(2), Double.valueOf(3), Double.valueOf(4), Double.valueOf(5)));
        b3 = Polynomial.from(List.of(Double.valueOf(4), Double.valueOf(5), Double.valueOf(6)));
        result3 = Polynomial.from(a3.times(b3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(12), Double.valueOf(23), Double.valueOf(40), Double.valueOf(43), Double.valueOf(58), Double.valueOf(49), Double.valueOf(30)), result3.getCoefficients());
        result3 = Polynomial.from(b3.times(a3, ring3).getCoefficients());
        assertEquals(List.of(Double.valueOf(12), Double.valueOf(23), Double.valueOf(40), Double.valueOf(43), Double.valueOf(58), Double.valueOf(49), Double.valueOf(30)), result3.getCoefficients());
    }
}
