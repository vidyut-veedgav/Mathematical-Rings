
/**
 * @author Vidyut Veedgav
 * a class to demonstrate the concept of Rings on Doubles
 */
public class DoubleRing implements Ring<Double>{

    @Override
    public Double zero() {
        return Double.valueOf(0.0);
    }

    @Override
    public Double identity() {
        return Double.valueOf(1.0);
    }

    @Override
    public Double sum(Double x, Double y) {
        return Double.valueOf(x + y);
    }

    @Override
    public Double product(Double x, Double y) {
        return Double.valueOf(x * y);
    }
}
