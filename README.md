# Mathematical Rings in Java

## Overview
This project implements various mathematical ring structures in Java, providing a robust framework for algebraic computations. It includes implementations for different types of rings including integers, polynomials, and matrices.

## Project Structure
```
.
├── src/
│   ├── Ring.java                    # Base ring interface
│   ├── Rings.java                   # Utility class for ring operations
│   ├── BigIntegerRing.java          # Implementation for big integers
│   ├── DoubleRing.java             # Implementation for double precision numbers
│   ├── IntegerRing.java            # Implementation for integers
│   ├── Polynomial.java             # Base polynomial class
│   ├── PolynomialRing.java         # Ring implementation for polynomials
│   ├── InterpolatingPolynomial.java # Polynomial interpolation implementation
│   ├── Matrix.java                 # Matrix operations
│   ├── MatrixMap.java              # Matrix mapping functionality
│   ├── MatrixRing.java             # Ring implementation for matrices
│   ├── SparseMatrixMap.java        # Sparse matrix optimizations
│   └── Indexes.java                # Index handling utilities
├── build/                          # Compiled files
├── doc/                           # Generated documentation
├── lib/                           # Project dependencies
├── report/                        # Project reports
└── build.xml                      # Ant build configuration
```

## Features
- Generic ring interface supporting various mathematical structures
- Implementation of common ring types:
  - Integer rings (including BigInteger support)
  - Double precision number rings
  - Polynomial rings
  - Matrix rings
- Support for sparse matrix operations
- Polynomial interpolation capabilities
- Comprehensive test coverage

## Building the Project
This project uses Apache Ant for building. To build the project:

```bash
ant build
```

## Testing
The project includes comprehensive unit tests:
- RingTest.java - Core ring functionality tests
- PolynomialTest.java - Polynomial implementation tests
- MatrixTest.java - Matrix operations tests

To run the tests:

```bash
ant test
```

## Documentation
JavaDoc documentation is available in the `doc/` directory. To generate fresh documentation:

```bash
ant doc
```

## Development Status
- Core ring implementations are complete and documented
- Matrix operations are fully implemented and tested
- Recent updates include new specifications and index handling improvements
- All test suites are up to date and passing

## Dependencies
The project uses:
- Java Development Kit
- Apache Ant (for building)
- JUnit (for testing)

## Project Specifications
Detailed project specifications can be found in `project_specification.pdf` in the root directory.

## Contributing
When contributing to this project, please:
1. Ensure all new code has corresponding unit tests
2. Update documentation for any new features
3. Follow the existing code style and conventions
4. Run the full test suite before submitting changes
