package app;

public class Main {
    private final static int ROWS_FIRST_MATRIX = 2000;                                        // число строк первой матрицы
    private final static int COLS_FIRST_MATRIX = 2000;                                        // число столбцов первой матрицы
    private final static int ROWS_SECOND_MATRIX = COLS_FIRST_MATRIX;                          // число строк второй матрицы
    private final static int COLS_SECOND_MATRIX = 2000;                                       // число  столбцов второй матрицы
    //private final static int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors(); // колчиство потоков для вычисления
    private final static int NUMBER_OF_THREAD = 2;                                            // колчиство потоков для вычисления
    private static Thread[] threads;                                                          // массив потоков;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Number of thread: " + NUMBER_OF_THREAD);
        System.out.println("Generate left matrix ...");
        int[][] leftMatrix = genRandomMatrix(ROWS_FIRST_MATRIX, COLS_FIRST_MATRIX);
        System.out.println("Generate right matrix ...");
        int[][] rightMatrix = genRandomMatrix(ROWS_SECOND_MATRIX, COLS_SECOND_MATRIX);

        final MatrixMultiplier matrixMultiplier = new MatrixMultiplier(leftMatrix, rightMatrix);

        generateThread(leftMatrix, rightMatrix, matrixMultiplier);

        long startTime = System.currentTimeMillis();
        // запуск потоков
        for(Thread thread: threads)
            thread.start();
        // ожидание заверешения работы всех потоков
        for(Thread thread: threads)
            thread.join();
        long stopTime = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (stopTime - startTime));


        // вывод левой матрицы на экран
        //printMatrix(leftMatrix, "Left matrix:\n");
        // вывод правой матрицы на экран
        //printMatrix(rightMatrix, "Right matrix:\n");
        // вывод результата на экран
        //printMatrix(matrixMultiplier.getResultMatrix(), "\nResult Matrix:\n");

        System.out.println("Checking in one thread ...");
        startTime = System.currentTimeMillis();
        final boolean testResult = checkResult(leftMatrix, rightMatrix, matrixMultiplier.getResultMatrix());
        final String result = testResult ? "Success": "Failed";
        System.out.println("Result test: " + result);
        stopTime = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (stopTime - startTime));
    }

    private static int[][] genRandomMatrix(int row, int col){
        if (row <= 0 || col <= 0)
            throw new IllegalArgumentException("row and col can't be <= 0");
        int[][] result = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                result[i][j] = (int) (Math.random()*100);
        return result;
    }

    private static void printMatrix(final int[][] matrix, final String msg){
        if (matrix != null)
            System.out.println(msg);
            for (int[] line: matrix) {
                for (int element: line)
                    System.out.printf(element + "\t");
                System.out.println("\n");
            }
    }

    /**
     * создает массив потоков и распределяет по ним ячейки матрицы для вычисления
     * @param leftMatrix
     * @param rightMatrix
     * @param matrixMultiplier
     */
    private static void generateThread(int[][] leftMatrix, int[][] rightMatrix, MatrixMultiplier matrixMultiplier){
        // количество ячеек, приходяещихся на один поток
        int cellsForOneThread = (leftMatrix.length * rightMatrix[0].length) / NUMBER_OF_THREAD;
        // массив потоков для вычисления
        threads = new Thread[NUMBER_OF_THREAD];
        // ячейка, с которой начианается вычисление
        int startCell = 0;
        // генерация потоков
        for (int i = 0; i < NUMBER_OF_THREAD; i++){
            int stopCell = startCell + cellsForOneThread;
            if (i == NUMBER_OF_THREAD - 1)
                stopCell = leftMatrix.length * rightMatrix[0].length;
            Thread thread = new Thread(new ThreadMultiplier(startCell, stopCell, matrixMultiplier));
            threads[i] = thread;
            startCell = stopCell;
        }
    }

    /**
     * Проверка правильности вычислений в однопоточном режиме
     * @param leftMatrix    правая исходная матрица
     * @param rightMatrix   левая исходная матрица
     * @param resultMatrix  матрица, полученная при вычислении
     */
    private static boolean checkResult(int[][] leftMatrix, int[][] rightMatrix, int[][] resultMatrix){
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < rightMatrix.length; k++)
                    sum += leftMatrix[i][k] * rightMatrix[k][j];
                if (resultMatrix[i][j] != sum){
                    return false;
                }
            }
        }
        return true;
    }
}
