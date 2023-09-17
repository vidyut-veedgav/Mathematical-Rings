package RingPackage;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Vidyut Veedgav
 * a class representing an iterator for the Polynomial class
 */
public class PolynomialIterator<T> implements Iterator<T> {

    private List<T> coefficients;
    private int index;

    public PolynomialIterator(List<T> coefficients) {
        this.coefficients = coefficients;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return coefficients.get(index + 1) != null;
    }

    @Override
    public T next() {

        if (!hasNext()) {
            throw new NoSuchElementException("Reached the end of the coefficient list");
        }
        index++;
        return coefficients.get(index - 1);
    }
}
