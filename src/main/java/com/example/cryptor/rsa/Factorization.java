package com.example.cryptor.rsa;

import com.example.cryptor.helpful.PrimeGenerator;
import com.example.cryptor.helpful.Timer;

import java.math.BigInteger;
import java.util.Scanner;

public class Factorization {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();


        for (int i = 10; i < 100; i++) {
            BigInteger p = PrimeGenerator.generatePrimeNumber(i);
            BigInteger q = PrimeGenerator.generatePrimeNumber(i);
            System.out.println(p.toString() + " -- " + q.toString());
            BigInteger number = p.multiply(q);
            timer.startTimer();
            factorize(number);
            timer.stopTimer();
        }

        scanner.close();
    }

    // Function to factorize the product of two prime numbers
    private static void factorize(BigInteger product) {
        // Find and print the two prime factors
        BigInteger[] factors = findPrimeFactors(product);

        System.out.println(factors[0] + " and " + factors[1]);
    }

    // Function to find the two prime factors of a product
    private static BigInteger[] findPrimeFactors(BigInteger product) {
        BigInteger[] factors = new BigInteger[2];

        // Find the smallest factor of the product
        factors[0] = findSmallestFactor(product);

        // Divide the product by the first factor to find the second factor
        factors[1] = product.divide(factors[0]);

        return factors;
    }

    // Function to find the smallest factor of a number
    private static BigInteger findSmallestFactor(BigInteger n) {
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n.sqrt()) <= 0; i = i.add(BigInteger.ONE)) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                return i;
            }
        }
        // If no factor is found (should not happen for the product of two primes)
        return n;
    }
}
