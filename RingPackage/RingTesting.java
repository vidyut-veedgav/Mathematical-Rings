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
        assertEquals(Integer.valueOf(0), Rings.product(intList, intRing));

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
        assertEquals(BigInteger.valueOf(0), Rings.product(bigIntList, bigIntRing));

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
        assertEquals(Double.valueOf(0), Rings.product(doubleList, doubleRing));

        //test 1
        doubleList = new ArrayList<>(Arrays.asList(1.00));
        assertEquals(Double.valueOf(1), Rings.product(doubleList, doubleRing));

        //test 1 operation
        doubleList = new ArrayList<>(Arrays.asList(100., 200.));
        assertEquals(Double.valueOf(20000.0), Rings.product(doubleList, doubleRing));

        //test many
        doubleList = new ArrayList<>(Arrays.asList(1.54, 2.63, 3.91, 4.01, 5.99));
        assertEquals(Double.valueOf(380.38591001180004), Rings.product(doubleList, doubleRing));
    }
}
