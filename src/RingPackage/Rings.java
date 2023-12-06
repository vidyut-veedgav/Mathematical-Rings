package RingPackage;

import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;

/**
 * This is a class defining behavior for ring operations
 * @author Vidyut Veedgav
 */
public final class Rings<T> {
    
    /**
     * Reduces elements of a list to a sinle element by sequentially conducting an externally defined operation on the accumulation of a List
     * @param <T> the static type T
     * @param args the list of arguments being reduced
     * @param zero the zero of data type T
     * @param accumulator the BinaryOperator interface which specifies the behavior of the reduce function (add the elements, multiply the elements, etc.)
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
     * Reduces a list by summing its elements
     * @param <T> the static type T
     * @param args the list of arguments being summed
     * @param ring the Ring object used for the addition operation on elemnts in args
     * @return a new T element representing the sum of all elements in args
     */
    public static <T> T sum(List<T> args, Ring<T> ring) {

        //null checks
        Objects.requireNonNull(args);
        Objects.requireNonNull(ring);

        return reduce(args, ring.zero(), (x, y) -> ring.sum(x, y));
    }

    /**
     * Reduces a list by multiplying its elements
     * @param <T> the static type T
     * @param args the list of arguments being multiplied
     * @param ring the Ring object used for the multiplication operation on elemnts in args
     * @return a new T element representing the product of all elements in args
     */
    public static <T> T product(List<T> args, Ring<T> ring) {

        //null checks
        Objects.requireNonNull(args);
        Objects.requireNonNull(ring);

        return reduce(args, ring.identity(), (x, y) -> ring.product(x, y));
    }
}
