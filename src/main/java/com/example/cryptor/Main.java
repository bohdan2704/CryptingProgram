package com.example.cryptor;

import com.example.cryptor.helpful.PrimeGenerator;
import com.example.cryptor.rsa.RSA;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        BigInteger bigInteger1 = PrimeGenerator.generatePrimeNumber(128);
        BigInteger bigInteger2 = PrimeGenerator.generatePrimeNumber(128);
        // MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJaMRJ2zQ0poqtmm7PjNFpLxlJrSYZMW7NUDPMMAwD3QzKz4txFjrfKznSq9TtnQFf6LNX4J5YlISVavf0cy0qUCAwEAAQ==
        // MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAloxEnbNDSmiq2abs+M0WkvGUmtJhkxbs1QM8wwDAPdDMrPi3EWOt8rOdKr1O2dAV/os1fgnliUhJVq9/RzLSpQIDAQABAkBw0Ty6URQkNruRz53V9Rrtk0whmXa1m4BvQLXQDEgECcW2xwIMHA7il9u98ZBhfS3cl9l0+BvjTCCSA3V9VW0hAiEA1Drx++edjPj3RgS7oFHIKOhlNgdmcdTpmfhNG8SGl3MCIQC1mLMopxgYudCy8WT6yS4V5vSNGGMP0FnT3czlB0P3hwIgKu1hVNqce49ANOFwMhVYry2Si0Mj7ZJrTcFzl1JBBxECIQCoNz8elUo4/UdZu0qzFNDf8QTv/nslFvdezsMSFNzc2QIgb1LMgHa6DpDzGaC+p/w7mQXvvbfoa4DDD/mazsOVoR0=
//        RSA rsa = new RSA( BigInteger.valueOf(61), BigInteger.valueOf(53));
        RSA rsa = new RSA( 128, 32);

        System.out.println("-- SECRET --");
        System.out.println(rsa.showKeys());
        System.out.println("-- SECRET --");

        String message = "HELLO MARYNKA IS HERE";
        String ciphertext = rsa.encrypt(message);

        System.out.println("Ciphertext: " + ciphertext);
        String decryptedAsciiString = rsa.decrypt(ciphertext);

        System.out.println("Decrypted message: " + decryptedAsciiString);
    }

}