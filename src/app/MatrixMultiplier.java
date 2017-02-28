package app;

class MatrixMultiplier {

    private final int[][] A;
    private final int[][] B;

    private int[][] resultMatrix;

    /**
     *
     * @param A лева матрица
     * @param B правая матрица
     */
    MatrixMultiplier(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;
        if (B.length != A[0].length){
            System.out.println("Matrix can not be multiply!");
            return;
        }
        this.resultMatrix = new int[A.length][B[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < B[0].length; j++)
                this.resultMatrix[i][j] = 0;
    }

    /**
     * Вычисление ячейки результруещей матрицы
     *     1   2   3                   3
     *   |a11 a12 a13|    1  |b11 b12 B13|
     * 2 |A21 A22 A23|    2  |b21 b22 B23|
     *   |a31 a32 a33|    3  |b31 b32 B33|
     *
     *                     3
     *           |c11 c12 c13|
     *        2  |c21 c22 C23|
     *           |c31 c32 c33|
     *
     * @param row номер строки
     * @param col номер столбца
     */
    void calculateCell(int row, int col){
        int sum = 0;
        if (resultMatrix != null)
            for (int i = 0; i < A[0].length; i++)
                sum += A[row][i] * B[i][col];
        resultMatrix[row][col] = sum;
    }

    /**
     *
     * @return матрица, полученная после умножения
     */
    int[][] getResultMatrix() {
        return resultMatrix;
    }

    /**
     *
     * @return число стоблцов результирующей матрицы
     */
    int getCountColResultMatrix(){
        return resultMatrix[0].length;
    }

}
