package org.example;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MultiplicacionMatrices {
    public static void main(String[] args) {
        String inputFile1 = "matriz1.txt";
        String inputFile2 = "matriz2.txt";
        String outputFile = "resultado.txt";

        int[][] matrix1 = readMatrixFromFile(inputFile1);
        int[][] matrix2 = readMatrixFromFile(inputFile2);

        if (matrix1 == null || matrix2 == null) {
            System.out.println("Error: No se pudo leer alguna de las matrices desde los archivos de entrada.");
            return;
        }

        if (!validateMatrixMultiplication(matrix1, matrix2)) {
            System.out.println("Error: Las matrices no pueden multiplicarse. Asegúrate de que las columnas de la primera matriz sean iguales a las filas de la segunda matriz.");
            return;
        }

        int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);

        if (resultMatrix == null) {
            System.out.println("Error: No se pudo multiplicar las matrices.");
            return;
        }

        if (!writeMatrixToFile(resultMatrix, outputFile)) {
            System.out.println("Error: No se pudo escribir el resultado en el archivo de salida.");
            return;
        }

        System.out.println("La multiplicación de las matrices se ha completado y el resultado se ha guardado en el archivo " + outputFile + ".");
    }

    static int[][] readMatrixFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int rowCount = 0;
            int columnCount = -1;
            int[][] matrix = null;

            while ((line = reader.readLine()) != null) {
                String[] values = line.trim().split("\\s+");

                if (columnCount == -1) {
                    columnCount = values.length;
                    matrix = new int[columnCount][];
                } else if (columnCount != values.length) {
                    System.out.println("Error: El número de columnas en la matriz no es consistente.");
                    return null;
                }

                int[] row = new int[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    try {
                        row[i] = Integer.parseInt(values[i]);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: El valor en la matriz no es un número entero válido.");
                        return null;
                    }
                }

                matrix[rowCount] = row;
                rowCount++;
            }

            return matrix;
        } catch (IOException e) {
            System.out.println("Error: No se pudo leer el archivo de entrada: " + fileName);
            return null;
        }
    }

    private static boolean validateMatrixMultiplication(int[][] matrix1, int[][] matrix2) {
        int columnsMatrix1 = matrix1[0].length;
        int rowsMatrix2 = matrix2.length;
        return columnsMatrix1 == rowsMatrix2;
    }

    static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rowsMatrix1 = matrix1.length;
        int columnsMatrix1 = matrix1[0].length;
        int columnsMatrix2 = matrix2[0].length;

        int[][] resultMatrix = new int[rowsMatrix1][columnsMatrix2];

        for (int i = 0; i < rowsMatrix1; i++) {
            for (int j = 0; j < columnsMatrix2; j++) {
                int sum = 0;
                for (int k = 0; k < columnsMatrix1; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                resultMatrix[i][j] = sum;
            }
        }

        return resultMatrix;
    }

    private static boolean writeMatrixToFile(int[][] matrix, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int[] row : matrix) {
                for (int i = 0; i < row.length; i++) {
                    writer.write(String.valueOf(row[i]));
                    if (i != row.length - 1) {
                        writer.write("\t");
                    }
                }
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error: No se pudo escribir en el archivo de salida: " + fileName);
            return false;
        }
    }

	public static int[][] multiplicarMatrices(int[][] matriz1, int[][] matriz2) {
		// TODO Auto-generated method stub
		return null;
	}
}