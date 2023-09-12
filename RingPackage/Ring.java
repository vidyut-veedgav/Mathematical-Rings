package RingPackage;

/**
 * @author Vidyut Veedgav
 * an interface to support ring operations on a variety of set types T
 */
public interface Ring<T> {

    T zero(); //a method to represent the zero property,  a * 0 = 0
    T identity(); //a method to represent the multiplicative identity property, a * 1 = a
    T sum(T x, T y); //a method to represent the sum operation
    T product(T x, T y); //a method to represent the multiplication operation
}



