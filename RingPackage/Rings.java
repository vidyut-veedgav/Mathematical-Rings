package RingPackage;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * @author Vidyut Veedgav
 * a class defining behavior for ring operations
 */
public final class Rings<T> {
    
    /**
     * a method to reduce elements of a list by conducting an externally defined operation
     * @param <T>
     * @param args
     * @param zero
     * @param accumulator
     * @return
     */
    public static <T> T reduce(List<T> args, T zero, BinaryOperator<T> accumulator) {

        //error handling: checks if the inputs are null and throws an exception depending on the validity of the preconditions
        if (args == null || accumulator == null || zero == null) {
            throw new NullPointerException("Arguments must not be null");
        }

        boolean foundAny = false; //checks if the list contains elements
        T result = zero; //initializes the result as zero

        //loops through elements in the input list
        for (T element : args) {

            //Null check
            if (element == null) {
                throw new NullPointerException("Elements in the args list must be non-null"); 
            }

            //checks if there are elements are present and converts the element to the result
            if (!foundAny) {
                foundAny = true;
                result = element;
            } else {
                result = accumulator.apply(result, element); //conducts an operation on the accumulation of previous elements and the current element
            }
        }
        return result;
    }

    /**
     * a method to reduce a list by summing its elements
     * @param <T>
     * @param args
     * @param ring
     * @return
     */
    protected static <T> T sum(List<T> args, Ring<T> ring) {

        //error handling: asserts that the arguments must not be null
        assert args != null : "Arguments must not be null.";
        assert ring != null : "Arguments must not be null.";

        return reduce(args, ring.zero(), (x, y) -> ring.sum(x, y));
    }

    /**
     * a method to reduce a list by multiplying its elements
     * @param <T>
     * @param args
     * @param ring
     * @return
     */
    protected static <T> T product(List<T> args, Ring<T> ring) {

        //error handling: asserts that the arguments must not be null
        assert args != null : "Argument 'args' must not be null.";
        assert ring != null : "Argument 'ring' must not be null.";

        return reduce(args, ring.identity(), (x, y) -> ring.product(x, y));
    }
}
