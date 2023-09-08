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
                result = accumulator(result, element);
            }
        }
        return null;
    }

    private static <T> T accumulator(T result, T element) {
        
        OperationType o_type;

        switch (o_type) {
            case ZERO:
                //do something
            case IDENTITY:
                //do something
            case SUM:
                //do something
            case PRODUCT:
                //do something
        }
    }

    public static void main(String[] args) {
        
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        IntegerRing intRing = new IntegerRing();
        BinaryOperator<Integer> binOpp = new BinaryOperator<>() {

            @Override
            public Integer apply(Integer t, Integer u) {
                return t + u;
            }
            
        };
        
    }
}
