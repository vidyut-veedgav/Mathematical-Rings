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
     * @return
     */
    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {

        List<T> a = this.getCoefficients();
        List<T> b = other.getCoefficients();

        List<T> sum_list = longerList(a, b);

        ListIterator<T> aIter; 
        ListIterator<T> bIter;

        for (int i = 0; i < sum_list.size(); i++) {
            aIter = a.listIterator(i);
            bIter = b.listIterator(i);

            if (!aIter.hasNext() || !bIter.hasNext()) {
                s
            }
        }
        Polynomial<T> sum = new Polynomial<>(sum_list);
        return null;
    }

    /**
     * a method to multiply two polynomials together
     * @param other
     * @param ring
     * @return
     */
    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        
        int productLength = this.getCoefficients().size() + other.getCoefficients().size() + 1;
        List<T> p_coefficients = new ArrayList<>(productLength);
        List<T> temp;
        ListIterator<T> pIter;
        ListIterator<T> qIter;
        for (int i = 0; i < productLength; i++) {

            pIter = this.listIterator(i);
            qIter = other.listIterator(0);
            temp = new ArrayList<>();

            while (true) {

                temp.add(ring.product(pIter.next(), qIter.previous()));
                if (!pIter.hasNext() || !pIter.hasPrevious()) {
                    break;
                }
            }
            p_coefficients.add(Rings.sum(temp, ring));
            i++;
        }        

        Polynomial<T> product = new Polynomial<>(p_coefficients);
        return product;
    }

    public static void main(String[] args) {
        
        List<Integer> a = List.of(1, 2, 3, 4);
        List<Integer> b = List.of(5, 6, 7);

        Polynomial<Integer> polyA = new Polynomial<>(a);
        Polynomial<Integer> polyB = new Polynomial<>(b);

        Ring<Integer> intRing = new IntegerRing();

        System.out.println((polyA.times(polyB, intRing)));
        
    }
}