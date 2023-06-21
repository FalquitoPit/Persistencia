package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class MultiplicacionMatricesTest {
    private static final String INPUT_FILE_1 = "matriz1.txt";
    private static final String INPUT_FILE_2 = "matriz2.txt";
    private static final String OUTPUT_FILE = "resultado.txt";

    @Test
    public void testValidMatrixMultiplication() {
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrix2 = {
                {10, 11, 12},
                {13, 14, 15},
                {16, 17, 18}
        };

        int[][] expectedResult = {
                {84, 90, 96},
                {201, 216, 231},
                {318, 342, 366}
        };

        writeMatrixToFile(matrix1, INPUT_FILE_1);
        writeMatrixToFile(matrix2, INPUT_FILE_2);

        MultiplicacionMatrices.main(null);

        int[][] resultMatrix = readMatrixFromFile(OUTPUT_FILE);
        Assertions.assertArrayEquals(expectedResult, resultMatrix);
    }

    @Test
    public void testInvalidMatrixMultiplication() {
        int[][] matrix1 = {
                {1, 2},
                {3, 4}
        };

        int[][] matrix2 = {
                {5, 6, 7},
                {8, 9, 10}
        };

        writeMatrixToFile(matrix1, INPUT_FILE_1);
        writeMatrixToFile(matrix2, INPUT_FILE_2);

        MultiplicacionMatrices.main(null);

        File outputFile = new File(OUTPUT_FILE);
        Assertions.assertFalse(outputFile.exists());
    }

    private void writeMatrixToFile(int[][] matrix, String fileName) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[][] readMatrixFromFile(String fileName) {
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
}
