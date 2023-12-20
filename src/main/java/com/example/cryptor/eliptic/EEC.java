package com.example.cryptor.eliptic;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class EEC {
    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        System.out.println("Java generate an EC keypair");
        String ecdhCurvenameString = "secp256r1";
        // standard curvennames
        // secp256r1 [NIST P-256, X9.62 prime256v1]
        // secp384r1 [NIST P-384]
        // secp521r1 [NIST P-521]
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "SunEC");
        ECGenParameterSpec ecParameterSpec = new ECGenParameterSpec(ecdhCurvenameString);
        keyPairGenerator.initialize(ecParameterSpec);
        KeyPair ecdhKeyPair = keyPairGenerator.genKeyPair();
        PrivateKey privateKey = ecdhKeyPair.getPrivate();
        PublicKey publicKey = ecdhKeyPair.getPublic();
        System.out.println("privateKey: " + privateKey);
        System.out.println("publicKey: " + publicKey);
    }
}