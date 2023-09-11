package RingPackage;

/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on Integers
 */
public class IntegerRing implements Ring<Integer>{

    @Override
    public Integer zero() {
        return 0;
    }

    @Override
    public Integer identity() {
        return 1;
    }

    @Override
    public Integer sum(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer product(Integer x, Integer y) {
        return x * y;
    }

    
}