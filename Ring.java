
/**
 * @author Vidyut Veedgav
 * an interface to support ring operations on a variety of set types T
 */
public interface Ring<T> {

    T zero();
    T identity();
    T sum(T x, T y);
    T product(T x, T y);
}



