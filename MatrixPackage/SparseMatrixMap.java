package MatrixPackage;

import java.util.HashMap;
import java.util.Map;

public class SparseMatrixMap<T> extends MatrixMap<T> {

    private SparseMatrixMap(Map<Indexes, T>  sparseMatrix) {
        super(sparseMatrix);
    }



    public static void main(String[] args) {

        SparseMatrixMap.instance(new Indexes(3, 4), (index) -> index.row() + index.column());
        SparseMatrixMap.instance(new Indexes(3, 4), (index) -> index.row() + index.column()); 
    }
}
