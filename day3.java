/* Advent of Code, Day 3: Gear Ratios
 * Adrien Abbey, Jan. 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class day3 {
    public static void main(String[] args) throws FileNotFoundException {
        // Load the input file:
        File inputFile = new File("example-input.txt");
        Scanner inputScanner = new Scanner(inputFile);

        // It's probably easiest to work with the input as a character matrix.
        // Before creating the matrix, I need to know its dimensions.
        // To do this, first load up the input file into a String array list:
        ArrayList<String> inputStrings = new ArrayList<String>();
        while (inputScanner.hasNextLine()) {
            inputStrings.add(inputScanner.nextLine());
        }

        // Find the dimensions for the character array:
        int matrixRows = inputStrings.size();
        int matrixCols = 0;
        for (String line : inputStrings) {
            if (line.length() > matrixCols) {
                matrixCols = line.length();
            }
        }

        // Create and fill the character matrix from the given input:
        char[][] inputMatrix = new char[matrixRows][matrixCols];
        for (int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixCols; j++) {
                inputMatrix[i][j] = inputStrings.get(i).charAt(j);
            }
        }

        // FIXME: Test code:
        // System.out.println(" Input rows: " + matrixRows);
        // System.out.println(" Input columns: " + matrixCols);
        // for (int i = 0; i < matrixRows; i++) {
        // String line = new String();
        // for (int j = 0; j < matrixCols; j++) {
        // line = line + "" + inputMatrix[i][j];
        // }
        // System.out.println(line);
        // }

        // Start scanning for integers:
        for (int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixCols; j++) {
                int[] foundInteger = findNumber(inputMatrix, i, j, matrixRows, matrixCols);
                int[] notFoundMatrix = { -1, -1 };
                if (!Arrays.equals(foundInteger, notFoundMatrix)) {
                    // Integer found. Skip to next column for testing:
                    j = foundInteger[1] + 1;

                    // FIXME: Print it:
                    System.out.println(" Found integer: row " + i + ", columns " + Arrays.toString(foundInteger));
                }
            }
        }

        // Close the scanner before exiting:
        inputScanner.close();
    }

    public static int[] findNumber(char[][] charMatrix, int row, int column, int maxRow, int maxCol) {
        // Searches the given char matrix at the given row and column for an
        // integer. If one is found, it returns the starting and ending column
        // as an integer matrix. If no integer is found, returns [-1][-1].

        // If the given row and column starts with an integer:
        if (Character.isDigit(charMatrix[row][column])) {
            // Then start searching from the current position until no more
            // integers are found:
            int finalColumn = column;
            for (int i = column + 1; i <= maxCol; i++) {
                if (Character.isDigit(charMatrix[row][i])) {
                    finalColumn = i;
                } else {
                    break;
                }
            }

            // Found the start and end column of the integer; return that:
            int returnArray[] = { column, finalColumn };
            return returnArray;
        } else {

            // Nothing found, return negative values:
            int returnArray[] = { -1, -1 };
            return returnArray;
        }
    }
}