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
    // Global Variables:
    public static String inputFileName = "example-input.txt";
    public static boolean test = true; // Enable test mode.
    public static boolean partTwo = true; // Toggle between Part One and Part Two code.

    public static void main(String[] args) throws FileNotFoundException {

        // Load the input file:
        File inputFile = new File(inputFileName);
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

        if (!partTwo) {
            // Use Part One code.

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
                            System.out
                                    .println(" Found integer: row " + i + ", columns " + Arrays.toString(foundInteger));
                        }

                        // Check if the found integer has any adjacent special
                        // characters:
                        boolean hasAdjacent = checkAdjacent(inputMatrix, i, foundInteger[0], foundInteger[1],
                                matrixRows,
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
        } else {
            // Use Part Two code.

            // Track the total sum of all valid gear ratios:
            int totalSum = 0;

            // Search for gears:
            for (int i = 0; i < matrixRows; i++) {
                for (int j = 0; j < matrixCols; j++) {
                    if (inputMatrix[i][j] == '*') {
                        // Add the gear ratio to the total sum, if any:
                        totalSum += ratioCheck(inputMatrix, i, j, matrixRows, matrixCols);
                    }
                }
            }

            // Print the result:
            System.out.println(" Total sum of all gear ratios is: " + totalSum);
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

    public static int ratioCheck(char[][] inputMatrix, int row, int column, int matrixRows, int matrixCols) {
        // Checks each adjacent location for an integer. If two adjacent
        // integers are located, it returns the product of those integers.
        // Otherwise, it returns zero.

        // Integers can be to the left, right, above, below, or diagonally.
        // Integers above or below exclude the possibility of diagonal integers.

        // Track the number of adjacent integers:
        int adjacentCount = 0;
        ArrayList<Integer> adjacentIntegers = new ArrayList<Integer>();

        // Check for adjacent integers:
        if (column - 1 >= 0 && Character.isDigit(inputMatrix[row][column - 1])) {
            adjacentCount += 1;
            adjacentIntegers.add(getInteger(inputMatrix, row, column - 1, matrixRows, matrixCols));
        }
        if (column + 1 < matrixCols && Character.isDigit(inputMatrix[row][column + 1])) {
            adjacentCount += 1;
            adjacentIntegers.add(getInteger(inputMatrix, row, column + 1, matrixRows, matrixCols));
        }
        if (row - 1 >= 0 && Character.isDigit(inputMatrix[row - 1][column])) {
            adjacentCount += 1;
            adjacentIntegers.add(getInteger(inputMatrix, row - 1, column, matrixRows, matrixCols));
        } else {
            // No digit above, check diagonals above:
            if (row - 1 >= 0 && column - 1 >= 0 && Character.isDigit(inputMatrix[row - 1][column - 1])) {
                adjacentCount += 1;
                adjacentIntegers.add(getInteger(inputMatrix, row - 1, column - 1, matrixRows, matrixCols));
            }
            if (row - 1 >= 0 && column + 1 < matrixCols && Character.isDigit(inputMatrix[row - 1][column + 1])) {
                adjacentCount += 1;
                adjacentIntegers.add(getInteger(inputMatrix, row - 1, column + 1, matrixRows, matrixCols));
            }
        }
        if (row + 1 < matrixRows && Character.isDigit(inputMatrix[row + 1][column])) {
            adjacentIntegers.add(getInteger(inputMatrix, row + 1, column, matrixRows, matrixCols));
            adjacentCount += 1;
        } else {
            // No digits below, check diagonals below:
            if (row + 1 < matrixRows && column - 1 >= 0 && Character.isDigit(inputMatrix[row + 1][column - 1])) {
                adjacentCount += 1;
                adjacentIntegers.add(getInteger(inputMatrix, row + 1, column - 1, matrixRows, matrixCols));
            }
            if (row + 1 < matrixRows && column + 1 < matrixCols
                    && Character.isDigit(inputMatrix[row + 1][column + 1])) {
                adjacentCount += 1;
                adjacentIntegers.add(getInteger(inputMatrix, row + 1, column + 1, matrixRows, matrixCols));
            }
        }

        // If ONLY two adjcent integers were found:
        if (adjacentCount == 2) {
            // Find the product of those integers:
            int gearRatio = adjacentIntegers.get(0) * adjacentIntegers.get(1);

            // Test code:
            if (test) {
                System.out.println(" Gear ratio found: " + gearRatio);
            }

            // Return the gear ratio:
            return gearRatio;
        } else {
            // Test code;
            if (test) {
                System.out.println(" No gear ratio found.");
            }
            // Otherwise return zero:
            return 0;
        }
    }

    public static int getInteger(char[][] inputMatrix, int row, int column, int matrixRows, int matrixCols) {
        // Find and return the full integer included in the given location.
        // Assumes a valid input.

        // Track the start and end values:
        int startingCol = column;
        int endingCol = column;

        // Start tracking to the left:
        for (int i = column - 1; i >= 0; i--) {
            if (Character.isDigit(inputMatrix[row][i])) {
                startingCol -= 1;
            } else {
                break;
            }
        }

        // Start tracking to the right:
        for (int i = column + 1; i < matrixCols; i++) {
            if (Character.isDigit(inputMatrix[row][i])) {
                endingCol += 1;
            } else {
                break;
            }
        }

        // Start collecting integers:
        ArrayList<Integer> integers = new ArrayList<Integer>();
        for (int i = startingCol; i <= endingCol; i++) {
            integers.add(Character.getNumericValue(inputMatrix[row][i]));
        }

        // Concactenate the collected integers into a string:
        String intString = "";
        for (int i : integers) {
            intString = intString + "" + i;
        }

        // Convert the integer string into a proper integer:
        int foundInteger = Integer.valueOf(intString);

        // Test code:
        if (test) {
            System.out.println(" Found integer: " + foundInteger);
        }

        // Return the found integer:
        return foundInteger;
    }
}