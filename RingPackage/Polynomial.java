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

    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {

        //switch case to decide which add operation?

        List<T> list = new ArrayList<>();
        for (T element : this) {
            //add the lists per the spec sheet
        }
        return new Polynomial<>(list);
    }

    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        //implements the multiplication functionality
        return null;
    }

    public static void main(String[] args) {
        
        List<Integer> coefficients = List.of(1, 2, 3, 4, 5, 6,7, 8, 9, 10);
        Polynomial<Integer> poly = new Polynomial<>(coefficients);
        
        poly.listIterator(0);
    }
}