package com.spe.calculator;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        System.out.println("Welcome to the Scientific Calculator");
        System.out.println("====================================");

        while (running) {
            System.out.println("\nChoose an operation What you want to perform:");
            System.out.println("1. Square Root (√x)");
            System.out.println("2. Factorial (!x)");
            System.out.println("3. Natural Logarithm (ln(x))");
            System.out.println("4. Power Function (x^b)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: // Square Root
                    System.out.print("Enter a number Below: ");
                    double sqrtInput = scanner.nextDouble();
                    System.out.println("Result: " + squareRoot(sqrtInput));
                    break;
                case 2: // Factorial
                    System.out.print("Enter a non-negative integer: ");
                    int factInput = scanner.nextInt();
                    System.out.println("Result: " + factorial(factInput));
                    break;
                case 3: // Natural Log
                    System.out.print("Enter a positive number: ");
                    double logInput = scanner.nextDouble();
                    System.out.println("Result: " + naturalLog(logInput));
                    break;
                case 4: // Power
                    System.out.print("Enter the base (x): ");
                    double base = scanner.nextDouble();
                    System.out.print("Enter the exponent (b): ");
                    double exponent = scanner.nextDouble();
                    System.out.println("Result: " + power(base, exponent));
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting calculator. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    break;
            }
        }
        scanner.close();
    }

    public static double squareRoot(double num) {
        if (num < 0) return Double.NaN;
        return Math.sqrt(num);
    }

    public static long factorial(int num) {
        if (num < 0) return -1;
        if (num == 0 || num == 1) return 1;
        long result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    public static double naturalLog(double num) {
        if (num <= 0) return Double.NaN; // Log is undefined for non-positive numbers
        return Math.log(num);
    }

    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
}