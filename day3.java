/* Advent of Code, Day 3: Gear Ratios
 * Adrien Abbey, Jan. 2024
 * Part 1 Solution: 531561
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class day3 {
    public static void main(String[] args) throws FileNotFoundException {
        // Enable test code:
        boolean test = true;

        // Load the input file:
        File inputFile = new File("input.txt");
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

        // Test code:
        if (test) {
            System.out.println(" Input rows: " + matrixRows);
            System.out.println(" Input columns: " + matrixCols);
            for (int i = 0; i < matrixRows; i++) {
                String line = new String();
                for (int j = 0; j < matrixCols; j++) {
                    line = line + "" + inputMatrix[i][j];
                }
                System.out.println(line);
            }
        }

        // Track the total sum of valid integers:
        int totalSum = 0;

        // Start scanning for integers:
        for (int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixCols; j++) {
                int[] foundInteger = findNumber(inputMatrix, i, j, matrixRows, matrixCols);
                int[] notFoundMatrix = { -1, -1 };
                if (!Arrays.equals(foundInteger, notFoundMatrix)) {
                    // Integer found. Skip to next column for testing:
                    j = foundInteger[1] + 1;

                    // Test code:
                    if (test) {
                        System.out.println(" Found integer: row " + i + ", columns " + Arrays.toString(foundInteger));
                    }

                    // Check if the found integer has any adjacent special
                    // characters:
                    boolean hasAdjacent = checkAdjacent(inputMatrix, i, foundInteger[0], foundInteger[1], matrixRows,
                            matrixCols);
                    if (hasAdjacent) {
                        // Valid integer found. Turn it into a usable integer:
                        String intString = "";
                        for (int k = foundInteger[0]; k <= foundInteger[1]; k++) {
                            intString += inputMatrix[i][k];
                        }
                        int integerFound = Integer.valueOf(intString);

                        // Add the found integer to the running sum:
                        totalSum += integerFound;

                        // Test code:
                        if (test) {
                            System.out.println(" - Adjacent special character found!");
                        }
                    }
                }
            }
        }

        // Print the result:
        System.out.println("Total sum of the given input is: " + totalSum);

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
            for (int i = column + 1; i <= maxCol - 1; i++) {
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

    public static boolean checkAdjacent(char[][] charMatrix, int row, int startingCol, int endingCol, int matrixRows,
            int matrixCols) {
        // Given the row and column of the start and end of a number in the
        // given character matrix, check adjacent locations for any special
        // characters. If one exists, return 'true', otherwise return 'false'.

        // Determine the start and end rows to check:
        int startRow = row - 1;
        int endRow = row + 1;
        if (startRow < 0) {
            startRow = 0;
        }
        if (endRow > matrixRows - 1) {
            endRow = matrixRows - 1;
        }

        // Determine start and end columns:
        int startCol = startingCol - 1;
        int endCol = endingCol + 1;
        if (startCol < 0) {
            startCol = 0;
        }
        if (endCol > matrixCols - 1) {
            endCol = matrixCols - 1;
        }

        // Check each adjacent row and column for special characters:
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                // Exclude the integer locations in range:
                if (j >= startingCol && j <= endingCol && i == row) {
                    j = endingCol + 1;
                    if (j > matrixCols - 1) {
                        j = matrixCols - 1;
                    }
                }

                // Check if the given character is a special character,
                // excluding '.':
                if (charMatrix[i][j] != '.' && !Character.isDigit(charMatrix[i][j])) {
                    // An adjacent special character has been found:
                    return true;
                }
            }
        }

        // No adjacent special characters found, return false:
        return false;
    }
}