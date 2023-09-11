package RingPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * @author Vidyut Veedgav
 * a class defining behavior for ring operations
 */
public final class Rings<T> {
    
    public static <T> T reduce(List<T> args, T zero, BinaryOperator<T> accumulator) {

        boolean foundAny = false;
        T result = zero;

        for (T element : args) {

            if (!foundAny) {
                foundAny = true;
                result = element;
            } else {
                result = accumulator.apply(result, element);
            }
        }
        return result;
    }

    public static final <T> T sum(List<T> args, Ring<T> ring) {
        return reduce(args, ring.zero(), (x, y) -> ring.sum(x, y));
    }

    public static final <T> T product(List<T> args, Ring<T> ring) {
        return reduce(args, ring.zero(), (x, y) -> ring.product(x, y));
    }

    public static void main(String[] args) {
        
        List<Integer> list = new ArrayList<>(Arrays.asList());
        IntegerRing intRing = new IntegerRing();
        Integer sum_result = sum(list, intRing);
        System.out.println(sum_result);

        Integer product_result = product(list, intRing);
        System.out.println(product_result);
    }
}
