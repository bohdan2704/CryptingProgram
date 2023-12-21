package com.example.cryptor.rsa;

import com.example.cryptor.helpful.PrimeGenerator;
import lombok.Getter;

import java.math.BigInteger;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    @Getter
    private BigInteger n;
    private BigInteger phi;
    @Getter
    private BigInteger e;
    private BigInteger d;

    public RSA(int nLen, int eLen) {
        this.p = PrimeGenerator.generatePrimeNumber(nLen);
        this.q = PrimeGenerator.generatePrimeNumber(nLen);
        this.n = p.multiply(q);
        this.phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        this.e = choosePublicExponent(phi, eLen);
        this.d = this.calculatePrivateKey();
    }

    private BigInteger calculatePrivateKey() {
        BigInteger dBigInteger = e.modInverse(phi);
        return dBigInteger;
    }

    public String encrypt(String message) {
        return encrypt(message, e,n);
    }

    public static String encrypt(String message, BigInteger e, BigInteger n) {
        StringBuilder ciphertextBuilder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            BigInteger m = BigInteger.valueOf((int) c);
            BigInteger encrypted = m.modPow(e, n);
            ciphertextBuilder.append(encrypted).append(" ");
        }
        // Get normal string, not ASCII
        return ciphertextBuilder.toString();
    }

    public String decrypt(String ciphertext) {
        // Get ascii representation of the text
//        ciphertext = toAsciiString(ciphertext);
        StringBuilder plaintextBuilder = new StringBuilder();
        String[] encryptedChars = ciphertext.split(" ");
        for (String encryptedChar : encryptedChars) {
            BigInteger encrypted = new BigInteger(encryptedChar);
            BigInteger decrypted = encrypted.modPow(d, n);
            char plaintextChar = (char) decrypted.intValue();
            plaintextBuilder.append(plaintextChar);
        }
        return plaintextBuilder.toString();
    }

    private static BigInteger choosePublicExponent(BigInteger phiN, int eLen) {
        // Start with a candidate value for e (commonly used value: 65537)
        BigInteger e = PrimeGenerator.generatePrimeNumber(eLen);

        // Check if e and phi(n) are coprime
        while (!e.gcd(phiN).equals(BigInteger.ONE)) {
            // Choose a different value for e if not coprime
            System.out.println("Searching for coprime. Default value is bad");
            e = BigInteger.valueOf(phiN.intValue()).add(BigInteger.ONE);
        }

        return e;
    }

    private static String toAsciiString(String message) {
        StringBuilder sb = new StringBuilder();
        for (char c : message.toCharArray()) {
            int asciiCode = (int) c;
            sb.append(asciiCode);
        }
        return sb.toString();
    }

    public static String unconcatenate(String input) {
        StringBuilder result = new StringBuilder();

        // Iterate over pairs of digits
        for (int i = 0; i < input.length(); i += 2) {
            // Get the current two-digit substring
            String digits = input.substring(i, Math.min(i + 2, input.length()));

            // Convert the two-digit substring to a character
            char letter = (char) Integer.parseInt(digits);

            // Append the character to the result
            result.append(letter);
        }

        // Convert the result to uppercase
        return result.toString().toUpperCase();
    }

    public String showKeys() {
        return "Public encryptor: " + e.toString() + " (" + e.toString(2).length() + ") bits" + System.lineSeparator() +
                "Public N multiply: " + n.toString() + " (" + n.toString(2).length() + ") bits" + System.lineSeparator()+
                "Private decryptor: " + d.toString() + " (" + d.toString(2).length() + ") bits" + System.lineSeparator();
    }
}