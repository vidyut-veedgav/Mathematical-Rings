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

Your code should look something like this:

Ring<Integer> intRing = new IntegerRing();
List<Integer> intList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
Rings.sum(intList, intRing);

In this example, I am summing Integers in the dataset {1, 2, 3, 4, 5}.


FOR TESTING THE SPARSE MATRIX METHODS, UNCOMMENT THE PREWRITTEN TOSTRING METHOD