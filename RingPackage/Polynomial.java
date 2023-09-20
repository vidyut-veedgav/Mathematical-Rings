package RingPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * @author Vidyut Veedgav
 * a class representing a polynomial ring, meant to emulate functionality for polynomials
 */
public final class Polynomial<T> implements Iterable<T> {
    
    private final List<T> coefficients; //a private instance field representing the polynomial's coefficients

    /**
     * a constructor for the Polynomial class
     * sets the coefficients p0, p1, ..., pm
     * @param coefficients
     */
    private Polynomial(List<T> coefficients) {
        //null check
        Objects.requireNonNull(coefficients);
        this.coefficients = new ArrayList<>(coefficients);
    }

    /**
     * a method to return a new polynomial created from an immutable copy of the input list
     * @param <S>
     * @param coefficients
     * @return a new polynomial
     */
    public static final <S> Polynomial<S> from(List<S> coefficients) {
        return new Polynomial<>(coefficients); 
    }

    /**
     * a getter method to return a mutable copy of the coefficients
     * @return a mutable list of coefficients
     */
    public List<T> getCoefficients() {
        return new ArrayList<>(coefficients);
    }

    /**
     * overrides the toString method to print the polynomial's coefficients
     */
    @Override
    public String toString() {
        return coefficients.toString();
    }

    /**
     * ovrrides the abstract method of Iterable to return the iterator for the list
     * @return a new iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new PolynomialIterator<>(this.coefficients);
    }

    /**
     * returns a list iterator over the coefficients 
     * initial call to next() return pi, initial call to previous returns pi-1
     * @param i
     * @return
     */
    public ListIterator<T> listIterator(int i) {
        return coefficients.listIterator(i);
    }

    /**
     * a method to add two polynomials together
     * Example:
     * a: (1, 2, 3)
     * b: (4, 5, 6)
     * a + b = (5, 7, 9)
     * McCabe's Complexity: 3
     * @param other
     * @param ring
     * @return the sum
     */
    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {

        List<T> a = this.getCoefficients(); //the first polynomial
        List<T> b = other.getCoefficients(); //the second polynomial

        int maxLength = Math.max(a.size(), b.size()); //computing the length of the longer list
        List<T> sum_list = new ArrayList<>(maxLength); //a new list storing the sum values

        ListIterator<T> aIter = a.listIterator();
        ListIterator<T> bIter = b.listIterator();

        //iterating until the longer list's index is reached
        for (int i = 0; i < maxLength; i++) {

            //  for each of the lists, checks if the end of the list is reached, 
            //  and either assigns the addend to the coefficient at the index or zero
            T a_addend = (i < a.size()) ? aIter.next() : ring.zero(); 
            T b_addend = (i < b.size()) ? bIter.next() : ring.zero();

            T sum = ring.sum(a_addend, b_addend); //computes the sum of the addends
            sum_list.add(sum); //adds the sum to the sum_list, which will be used to create the returned polynomial
        }
        return new Polynomial<>(sum_list);
    }

    /**
     * a method to multiply two polynomials together
     * Example:
     * a: (1, 2, 3)
     * b: (4, 5, 6)
     * a * b = (1 * 4),
     *         ((1 * 5) + (2 * 4)),
     *         ((1 * 6) + (2 * 5) + (3 * 4)),
     *         ((1 * 0) + (2 * 6) + (3 * 5) + (0 * 4)),
     *         ((1 * 0) + (2 * 0), (3 * 6) + (0 * 5) + (0 * 4))
     * 
     *       = (4, 13, 28, 27, 18)
     * McCabe's Complexity: 3
     * @param other
     * @param ring
     * @return the product
     */
    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        
        List<T> a = this.getCoefficients();
        List<T> b = other.getCoefficients(); 

        int productLength = computeProductLength(a, b);
        List<T> p_products = new ArrayList<>(productLength);

        ListIterator<T> aIter = a.listIterator();
        ListIterator<T> bIter = b.listIterator();

        int a_start = 0;
        for (int i = 0; i < productLength; i++) {

            T product = ring.zero();
            a_start = computeStartIndex(i, a_start, b);

            aIter = a.listIterator(a_start);
            bIter = b.listIterator(Math.min(i + 1, b.size()));

            while (aIter.hasNext() && bIter.hasPrevious()) {
                T a_factor = aIter.next();
                T b_factor = bIter.previous();
                T result = ring.product(a_factor, b_factor);
                product = ring.sum(product, result);
            }
            p_products.add(product);
        }        
       return new Polynomial<>(p_products);
    }

    /**
     * a helper method to compute whether an index should be incremented in the times method
     * @param args
     */
    private int computeStartIndex(int currentIndex, int startIndex, List<T> list) {
        if ((currentIndex + 1) > list.size()) {
            startIndex = startIndex + 1;
        }
        return startIndex;
    }

    /**
     * a helper method to handle the edge case where both polynomials have no coefficients in the times method
     * @param args
     */
    private int computeProductLength(List<T> a, List<T> b) {
        if (a.isEmpty() && b.isEmpty()) {
            return 0;
        }
        return a.size() + b.size() - 1;
    }

    public static void main(String[] args) {
        
        List<Double> a = List.of(Double.valueOf(1), Double.valueOf(2), Double.valueOf(3));
        List<Double> b = List.of(Double.valueOf(4), Double.valueOf(5), Double.valueOf(6));

        Polynomial<Double> polyA = new Polynomial<>(a);
        Polynomial<Double> polyB = new Polynomial<>(b);

        Ring<Double> doubRing = new DoubleRing();

        System.out.println((polyA.times(polyB, doubRing)));
    }
}

