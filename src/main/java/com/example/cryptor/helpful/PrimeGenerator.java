package com.example.cryptor.helpful;

import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeGenerator {
    private static final int CERTAINTY = 100;

    public static boolean isPrime(BigInteger n) {
        // Test if n is not even.
        // But care, 2 is prime !
        if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3))) {
            return true;
        }
        if (n.compareTo(BigInteger.ONE) <= 0 || n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            return false;
        }

        // find r and s
        int s = 0;
        BigInteger r = n.subtract(BigInteger.ONE);
        while (r.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
            s++;
            r = r.divide(BigInteger.valueOf(2));
        }

        // do CERTAINTY tests
        for (int i = 0; i < CERTAINTY; i++) {
            BigInteger a = new BigInteger(n.bitLength() - 2, new SecureRandom()).add(BigInteger.valueOf(2));
            BigInteger x = a.modPow(r, n);
            if (!x.equals(BigInteger.ONE) && !x.equals(n.subtract(BigInteger.ONE))) {
                int j = 1;
                while (j < s && !x.equals(n.subtract(BigInteger.ONE))) {
                    x = x.modPow(BigInteger.valueOf(2), n);
                    if (x.equals(BigInteger.ONE)) {
                        return false;
                    }
                    j++;
                }
                if (!x.equals(n.subtract(BigInteger.ONE))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static BigInteger generatePrimeCandidate(int length) {
        // generate random bits
        BigInteger p = new BigInteger(length, new SecureRandom());
        // apply a mask to set MSB and LSB to 1
        p = p.setBit(length - 1).setBit(0);
        return p;
    }

    public static BigInteger generatePrimeNumber(int length) {
        BigInteger p = BigInteger.valueOf(4);
        // keep generating while the primality test fails
        while (!isPrime(p)) {
            p = generatePrimeCandidate(length);
        }
        return p;
    }
}
