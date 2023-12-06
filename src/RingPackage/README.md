# Ring
 Computational support for the mathematical concept of rings

 How to compile and run the code:

 1. Download the zip file with the project contents
 2. Open the files in you IDE (VS Code, Eclipse, IntelliJ)
 3. Use the tester class to examine functionality of each class
 4. For customized usage, create a main method in the 'Rings' class and do the following:
    a. Create an instance of a Ring
    b. Create an instance of your dataset (using the List interface)
    c. Call the sum or product methods using these objects as inputs

Global Precondition: All non-primitive data types being passed in as parameters to methods must be non-null. 

Your code should look something like this:

Ring<Integer> intRing = new IntegerRing();
List<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
Rings.sum(intList, intRing);

In this example, I am summing Integers in the dataset {1, 2, 3, 4, 5}.

For testing the MatrixMap method, you can add the following main method to the code, and it will instruct you to follow a sequence of steps to create MatrixMap:

   /**
    * main method
    * @param args
    *
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Matrix Size");
        Integer size = Integer.valueOf(input.nextLine());
        System.out.println("SIZE = " + size);

        System.out.println("Matrix One Values:");
        Map<Indexes, Integer> indexMap = new HashMap<>();
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                Indexes index = new Indexes(i, j);
                System.out.println("Value at: " + index.toString());
                indexMap.put(index, Integer.valueOf(input.nextLine()));
            }
        }
        MatrixMap<Integer> m1 = MatrixMap.instance(new Indexes(size, size), (index) -> indexMap.get(index));
        System.out.println("MATRIX 1: ");
        System.out.println(m1.toString());

        System.out.println("Matrix Two Values:");
        Map<Indexes, Integer> indexMap2 = new HashMap<>();
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                Indexes index = new Indexes(i, j);
                System.out.println("Value at: " + index.toString());
                indexMap2.put(index, Integer.valueOf(input.nextLine()));
            }
        }
        MatrixMap<Integer> m2 = MatrixMap.instance(new Indexes(size, size), (index) -> indexMap2.get(index));
        System.out.println("MATRIX 1: ");
        System.out.println(m1.toString());

        System.out.println("ADD or MULTIPLY ([A/M])");
        String addOrMultiply = input.nextLine();

        input.close();

        Ring<Integer> intRing = new IntegerRing();

        if (addOrMultiply.equals("A")) {
            MatrixMap<Integer> sum = m1.plus(m2, (x, y) -> intRing.sum(x, y));
            System.out.println(sum);
        }
        else if (addOrMultiply.equals("M")) {
            MatrixMap<Integer> product = m1.times(m2, intRing);
            System.out.println(product);
        }
        else {
            System.out.println("Invalid selection");
        }
    }

For testing the SparseMatrixMap method, you can do the same with the following main method:
    /**
     * main method
     * @param args
     *
    public static void main(String[] args) {
        
        Ring<Integer> ring = new IntegerRing();

        //sparse = SparseMatrixMap.constant(2, ring, 1);
        //System.out.println(sparse);

        //sparse = SparseMatrixMap.identity(2, ring);

        //System.out.println(SparseMatrixMap.instance(2, 2, ring, (index) -> (index.column())));
        //System.out.println(SparseMatrixMap.instance(2, 2, ring, (index) -> (index.column())).convertToStandard(ring));
        //System.out.println(MatrixMap.instance(2, 2, (index) -> (index.column())).convertToSparse(ring));


        //OPERATION TESTING

        System.out.println("***************************************************");
        System.out.println("STANDARD MATRIX MAP:");
        Matrix<Integer> m1 = MatrixMap.instance(new Indexes(2, 2), (index) -> index.row());
        Matrix<Integer> m2 = MatrixMap.instance(new Indexes(2, 2), (index) -> index.column());
        System.out.println("Matrix 1: ");
        System.out.println(m1);
        System.out.println("Matrix 2: ");
        System.out.println(m2);
        System.out.println("***************************************************");
        System.out.println("*ADDITION*");
        System.out.println("Sum: ");
        System.out.println(MatrixRing.instance(ring).sum(m1, m2));
        System.out.println("*MULTIPLICATION*");
        System.out.println("Product: ");
        System.out.println(MatrixRing.instance(ring).product(m1, m2));

        System.out.println("***************************************************");
        System.out.println("SPARSE MATRIX MAP:");
        Matrix<Integer> s1 = SparseMatrixMap.instance(new Indexes(2, 2), ring, (index) -> index.row());
        Matrix<Integer> s2 = SparseMatrixMap.instance(new Indexes(2, 2), ring, (index) -> index.column());
        System.out.println("Matrix 1: ");
        System.out.println(s1);
        System.out.println("Matrix 2: ");
        System.out.println(s2);
        System.out.println("***************************************************");
        System.out.println("*ADDITION*");
        System.out.println("Sum: ");
        System.out.println(MatrixRing.instance(ring).sum(s1, s2));
        System.out.println("*MULTIPLICATION*");
        System.out.println("Product: ");
        System.out.println(MatrixRing.instance(ring).product(s1, s2));
        System.out.println("***************************************************");
    }
    */

The following can be used in SparseMatrixMap to aid a change in implementation, specifically in the addition and multilplication methods:

/* Methods to use in case of a change in implementation */
/*
    /**
     * finds the intersection of nonzero indexes between this matrix and another
     * @param other
     * @return
     *
    private List<Indexes> intersectionNonZeroIndexes(SparseMatrixMap<T> other) {
        List<Indexes> intersection = new ArrayList<>(this.nonZeroIndexes);
        intersection.retainAll(other.nonZeroIndexes);
        return intersection;
    }

    /**
     * finds the union of nonzero indexes between this matrix and another
     * @param other
     * @return
     *
    private List<Indexes> unionNonZeroIndexes(SparseMatrixMap<T> other) {
        Set<Indexes> resultSet = new HashSet<>(nonZeroIndexes);
        resultSet.addAll(other.nonZeroIndexes);
        return new ArrayList<>(resultSet);
    }
*/


FOR TESTING THE SPARSE MATRIX METHODS, UNCOMMENT THE PREWRITTEN TOSTRING METHOD