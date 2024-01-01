/* Advent of Code, Day 3: Gear Ratios
 * Adrien Abbey, Jan. 2024
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class day3 {
    public static void main(String[] args) throws FileNotFoundException {
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

        // FIXME: Test code:
        System.out.println(" Input rows: " + matrixRows);
        System.out.println(" Input columns: " + matrixCols);
        for (int i = 0; i < matrixRows; i++) {
            String line = new String();
            for (int j = 0; j < matrixCols; j++) {
                line = line + "" + inputMatrix[i][j];
            }
            System.out.println(line);
        }

        // Close the scanner before exiting:
        inputScanner.close();
    }
}