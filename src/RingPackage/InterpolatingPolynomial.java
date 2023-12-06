package RingPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is a class defining the operation of polynomial interpolation
 * @author Vidyut Veedgav
 */
public class InterpolatingPolynomial {

/*
Pseudocode

Method name: interpolatePolynomial

Input:
- list of Integers which are roots for the resulting polynomial
- values must all be unique and in ascending order

Output: 
- a list of the coefficients of the resulting polynomial
*/

/*interpolatePolynomial(list of roots)*/
/**
 * Interpolates a Polynomial given its list of roots.
 * @param rootList the list of roots.
 * @param ring a Ring object used for intermediate operations.
 * @return a new List of coefficients representing the interpolated polynomial.
 */
public static List<Integer> interpolatePolynomial(List<Integer> rootList, Ring<Integer> ring) {

	/*if list of roots is null, throw an appropriate exception*/
    Objects.requireNonNull(rootList, "roots cannot be null");

	/*require elements in roots are not null*/
    if (rootList.stream().anyMatch(Objects::isNull)) {
        throw new NullPointerException("elements in roots cannot be null");
    }

    /*roots ← param list of zeros*/
    List<Integer> roots = rootList;

	/*x ← Multiplicative identity of a the numerical operator being using*/
    Integer x = ring.identity();

	/*factors ← empty list of polynomial factors*/
    List<Polynomial<Integer>> factors = new ArrayList<>();

	/*for each root in roots
		create new Polynomial ← (x, root * -1)
		insert polynomial → factors*/
    for (Integer root : roots) {
        factors.add(Polynomial.from(List.of(x, ring.product(root, Integer.valueOf(-1)))));
    }

	/*return product(factors, ring)*/
    return Rings.product(factors, PolynomialRing.instance(new IntegerRing())).getCoefficients();
    }
}
