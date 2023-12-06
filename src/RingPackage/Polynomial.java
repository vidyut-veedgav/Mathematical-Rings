package RingPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * This is a class representing a polynomial ring, meant to emulate functionality for polynomials.
 * @author Vidyut Veedgav
 */
public final class Polynomial<T> {
    
    private final List<T> coefficients; //a private instance field representing the polynomial's coefficients

    /**
     * Constructor for the Polynomial class, called by the static factory method (from). This method sets the coefficients of the Polynomial p0, p1, ..., pm in a List data structure. 
     * @param coefficients a List of coefficients that the Polynomial has
     */
    private Polynomial(List<T> coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * Returns a new Polynomial object created from an immutable copy of the input list.
     * @param <S> the static type S.
     * @param coefficients the list of coefficients used to create a Polynomial.
     * @return a new immutable Polynomial objects with coefficients as inputted in the argument. 
     */
    public static final <S> Polynomial<S> from(List<S> coefficients) {
        //null check
        Objects.requireNonNull(coefficients, "coefficients cannot be null");

        return new Polynomial<>(List.copyOf(coefficients)); 
    }

    /**
     * Gets a mutable copy of this Polynomial's coefficients primarily used for containment testing. 
     * @return a mutable List object of coefficients. 
     */
    public List<T> getCoefficients() {
        return new ArrayList<>(coefficients);
    }

    /**
     * Returns a string representation of the object that "textually represents" a Polynomial object.
     * @return the string representation of an Indexes object in the form of its coefficients: "Polynomial [coefficients="a, b, c, d, e"].
     */
    @Override
    public String toString() {
        return "Polynomial [coefficients=" + coefficients + "]";
    }

    /**
     * Computes Polynomial addition between this Polynomial and another Polynomial
     * Example:
     * a: (1, 2, 3)
     * b: (4, 5, 6)
     * a + b = (5, 7, 9)
     * McCabe's Complexity: 3
     * @param other the other Polynomial object
     * @param ring the Ring object used for intermediate operations
     * @return the sum of this Polynomial and the other Polynomial
     */
    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {

        //null checks
        Objects.requireNonNull(other, "the 'other' parameter cannot be null");
        Objects.requireNonNull(ring, "the 'ring' parameter cannot be null");

        List<T> a = this.getCoefficients(); //coefficients of the first polynomial
        List<T> b = other.getCoefficients(); //coefficients of the second polynomial

        int maxLength = Math.max(a.size(), b.size()); //computing the length of the longer list
        List<T> sum_list = new ArrayList<>(maxLength); //a new list storing the sum values

        //initializing the iterators
        ListIterator<T> aIter = a.listIterator();
        ListIterator<T> bIter = b.listIterator();

        //iterating until the longer list's index is reached
        while (aIter.hasNext() || bIter.hasNext()) { 

            //  for each of the lists, checks if the end of the list is reached, 
            //  and either assigns the addend to the coefficient at the index or zero
            T a_addend = getAddend(ring, a, aIter); 
            T b_addend = getAddend(ring, b, bIter);

            T sum = ring.sum(a_addend, b_addend); //computes the sum of the addends
            sum_list.add(sum); //adds the sum to the sum_list, which will be used to create the returned polynomial
        }
        return new Polynomial<>(sum_list);
    }

    /**
     * Gets the addend of a list if it has a next value. This is used in the plus method
     * @param ring the Ring used for intermediate operations
     * @param list the List of coefficients
     * @param iterator the Iterator object used to parse the List
     * @return a new T value representing the addend of the coefficients.
     */
    private T getAddend(Ring<T> ring, List<T> list, ListIterator<T> iterator) {
        return (iterator.hasNext()) ? iterator.next() : ring.zero();
    }

    /**
     * Computes Polynomial multiplication between this Polynomial and another Polynomial
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
     * @param other the other Polynomial object
     * @param ring the Ring object used for intermediate operations
     * @return the product of this Polynomial and the other Polynomial
     */
    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {

        //null checks
        Objects.requireNonNull(other, "the 'other' parameter cannot be null");
        Objects.requireNonNull(ring, "the 'ring' parameter cannot be null"); 
        
        List<T> a = this.getCoefficients(); //coefficients of the first polynomial 
        List<T> b = other.getCoefficients(); //coefficients of the second polynomial

        int productLength;
        if (a.isEmpty() && b.isEmpty()) {  //  edge case: if both lists are empty, product length will be 0             
            productLength = 0;
         } else {
            productLength = a.size() + b.size() - 1;
         }                                           //        if not, the 
                                                             //  otherwise, it will be (size of a + size of b - 1)
        List<T> product_list = new ArrayList<>(productLength); //creates the list of products that will be used to create the final polynomial

        ListIterator<T> aIter = a.listIterator(); //iterator for list a 
        ListIterator<T> bIter = b.listIterator(); //iterator for list b

        int a_start = 0; //start index of list a

        //iterates until productLength is reached
        for (int i = 0; i < productLength; i++) {

            T product = ring.zero(); //initializing the product to zero
            a_start = computeStartIndex(i, a_start, b); //adjusting the start index of a depending on whether b has more elements

            //initializing the iterators to their respective start indices
            aIter = a.listIterator(a_start);
            bIter = b.listIterator(Math.min(i + 1, b.size()));

            //iterates while aIter has a next element and bIter has a previous element
            while (aIter.hasNext() && bIter.hasPrevious()) {
                T a_factor = aIter.next(); //defining a's next value as the first factor
                T b_factor = bIter.previous(); //defining b's previous value as second factor
                T result = ring.product(a_factor, b_factor); //multiplies the factors
                product = ring.sum(product, result); //sums the result of the multiplication with the previous result
            }
            product_list.add(product); //adds the product to the list of products
        }        
       return new Polynomial<>(product_list); 
    }

    /**
     * Computes the starting index of iterations in the times method
     * @param args
     * @return the integer starting index of an iteration
     */
    private int computeStartIndex(int currentIndex, int startIndex, List<T> list) {
        if ((currentIndex + 1) > list.size()) {
            startIndex = startIndex + 1;
        }
        return startIndex;
    }
}

