package RingPackage;

import java.util.List;
import java.util.Objects;
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
     * @return a single value which is the result of the reduction
     */
    public static <T> T reduce(List<T> args, T zero, BinaryOperator<T> accumulator) {

        //null checks
        Objects.requireNonNull(args);
        Objects.requireNonNull(zero);
        Objects.requireNonNull(accumulator);

        boolean foundAny = false; //checks if the list contains elements
        T result = zero; //initializes the result as zero

        //loops through elements in the input list
        for (T element : args) {

            //null check
            Objects.requireNonNull(args);

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
     * @return a reduction based on addition
     */
    public static <T> T sum(List<T> args, Ring<T> ring) {

        //null checks
        Objects.requireNonNull(args);
        Objects.requireNonNull(ring);

        return reduce(args, ring.zero(), (x, y) -> ring.sum(x, y));
    }

    /**
     * a method to reduce a list by multiplying its elements
     * @param <T>
     * @param args
     * @param ring
     * @return a reduction based on multiplication
     */
    public static <T> T product(List<T> args, Ring<T> ring) {

        //null checks
        Objects.requireNonNull(args);
        Objects.requireNonNull(ring);

        return reduce(args, ring.identity(), (x, y) -> ring.product(x, y));
    }
}
