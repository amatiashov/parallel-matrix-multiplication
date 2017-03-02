package app;

public class ThreadMultiplier implements Runnable{
    private int startCell;
    private int stopCell;
    private int countColResultMatrix;           // число столбцов результирующей матрицы
    private MatrixMultiplier matrixMultiplier;

    /**
     *
     * @param startCell             стартовая ячейка, с которой начинается расчет
     * @param stopCell              последняя ячейка (не включена в расчет)
     * @param matrixMultiplier      объект результирующей матрицы
     *
     */
    ThreadMultiplier(final int startCell, final int stopCell, final MatrixMultiplier matrixMultiplier) {
        this.startCell = startCell;
        this.stopCell = stopCell;
        this.matrixMultiplier = matrixMultiplier;
        this.countColResultMatrix = matrixMultiplier.getCountColResultMatrix();
    }

    @Override
    public void run(){
        System.out.println("Thread " + Thread.currentThread().getName() + " started. --> " +
                "Calculate from " +  startCell + " cell to " + stopCell);

        for (int i = startCell; i < stopCell; i++)
                matrixMultiplier.calculateCell(i / countColResultMatrix, i % countColResultMatrix);

        System.out.println("Thread " + Thread.currentThread().getName() + " finished. ");
    }
}
