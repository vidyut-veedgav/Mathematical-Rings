package RingPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        //add null check for list + elements
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
     * @param other
     * @param ring
     * @return the sum
     */
    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {

        List<T> a = this.getCoefficients(); //the first polynomial
        List<T> b = other.getCoefficients(); //the second polynomial

        int maxLength = Math.max(a.size(), b.size()); //computing the length of the longer list
        List<T> sum_list = new ArrayList<>(maxLength); //a new list storing the sum values

        //iterating until the longer list's index is reached
        for (int i = 0; i < maxLength; i++) {
            T a_coefficient = (i < a.size()) ? a.get(i) : ring.zero();
            T b_coefficient = (i < b.size()) ? b.get(i) : ring.zero();
            T sum = ring.sum(a_coefficient, b_coefficient);
            sum_list.add(sum);
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
     * @param other
     * @param ring
     * @return the product
     */
    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        
        List<T> a = this.getCoefficients();
        List<T> b = other.getCoefficients(); 

        int productLength = a.size() + b.size() - 1;
        List<T> p_coefficients = new ArrayList<>(productLength);
        for (int i = 0; i < productLength; i++) {

            T product = ring.zero();
            for (int j = 0; j <= i; j++) {

                if (j < a.size() && (i - j) < b.size()) {
                    T a_coefficient = a.get(j);
                    T b_coefficient = b.get(i - j);
                    T result = ring.product(a_coefficient, b_coefficient);
                    product = ring.sum(product, result);
                }
            }
            p_coefficients.add(product);
        }        

       return new Polynomial<>(p_coefficients);
    }

    public static void main(String[] args) {
        
        List<Integer> a = List.of(1, 2, 3);
        List<Integer> b = List.of(4, 5, 6);

        Polynomial<Integer> polyA = new Polynomial<>(a);
        Polynomial<Integer> polyB = new Polynomial<>(b);

        Ring<Integer> intRing = new IntegerRing();

        System.out.println((polyA.times(polyB, intRing)));
    }
}

