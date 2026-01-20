package tutorial.arraydemo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Array {


    public static void main(String[] args) {
        // 1 - declaration array
        int[] arr = {1, 5, 10};

        // 2 - declaration 2D array
        int[][] matrixA = {{1, 2, 3, 4, 5}, {4, 5, 6, 7, 8}};

        // 3 - insert and read 2D array
        int n = 0, k = 0, i, j;
        Scanner reader = new Scanner(System.in);
        //get user input for a
        try {
            System.out.println("Enter the first number");
            n = reader.nextInt();
            System.out.println("Enter the second number");
            k = reader.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERRORE: IL VALORE INSERITO NON E' UN INTERO ");
            System.exit(0);
        }
        int[][] dueDim = new int[n][k];
        // Write
        for (i = 0; i < n; i++) {
            for (j = 0; j < k; j++) {
                System.out.println("Enter the number of the array in the position[" + i + "][" + j + "]");
                dueDim[i][j] = reader.nextInt();
            }
        }
        // Read
        for (i = 0; i < n; i++) {
            for (j = 0; j < k; j++) {
                System.out.println("Value in the position[" + i + "][" + j + "]=" + dueDim[i][j]);
            }
        }
    }
}
