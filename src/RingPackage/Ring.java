package RingPackage;

/**
 * This is an interface to support ring operations on a variety of set types T
 * @author Vidyut Veedgav
 */
public interface Ring<T> {

    /**
     * Returns the zero property of data type T such that a * 0 = 0
     * @return zero of type T
     */
    T zero(); 

    /**
     * Returns the multiplicative identity property of data type T such that a * 1 = a
     * @return the identity of type T
     */
    T identity(); 

    /**
     * Returns the sum of two objects of type T
     * @param x the first addend
     * @param y the second addend
     * @return a new T object representing the sum of x and y
     */
    T sum(T x, T y); 

    /**
     * Returns the product of two objects of type T
     * @param x the first factor
     * @param y the second factor
     * @return a new T object representing the product of x and y
     */
    T product(T x, T y); 
}



