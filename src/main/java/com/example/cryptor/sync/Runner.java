package com.example.cryptor.sync;

import java.util.Arrays;

public class Runner {
    private static AES cipher;

    public static void main(String[] args) {
//         ECB test
//        cipher = new AES(key);
//
//        double startTime = System.currentTimeMillis();
//        for (int i=0; i < 100000; i++) cipher.ECB_decrypt(cipher.ECB_encrypt(inputText));
//        double endTime = System.currentTimeMillis();
//        System.out.println("ECB | "+(endTime-startTime)/1000.0 + " secs");

        // CBC test
//        byte[] iv = "c8IKDNGsbioSCfxWa6KT8A84SrlMwOUH".getBytes();
        byte[] iv = AES.generateIV();
        byte[] key = AES.generateKey();
        System.out.println(key.length);
        System.out.println(Arrays.toString(key));
        byte[] inputText = "Maryna is encrypting here".getBytes();
        cipher = new AES(key, iv);

        byte[] ivNew = AES.generateIV();
        System.out.println("Crypto Key: " + AES.byteArrayToHexString(key));
        System.out.println("Initialization vector for higher security: " + AES.byteArrayToHexString(ivNew));
        cipher = new AES(key, ivNew);

        byte[] encrypted = cipher.CBC_encrypt(inputText);
        System.out.println("Encrypted bytes: " + Arrays.toString(encrypted));
        String encryptedHex = AES.byteArrayToHexString(encrypted);
        System.out.println("HEX (encrypted) representation to save and send: " + encryptedHex);

        byte [] decrypted = cipher.CBC_decrypt(AES.hexStringToByteArray(encryptedHex));
        byte [] trimmedBytes = AES.trimZeroesFromEnd(decrypted);
        System.out.println("Decrypted bytes (real word): " + Arrays.toString(decrypted));
        System.out.println("Hex representation of our word: " + AES.byteArrayToHexString(decrypted));
        System.out.println("Decrypted zeroes bytes in the end (not trimmed). Zeroes left because AES work with 16 bytes blocks:\n" + new String(decrypted));
        System.out.println(new String(trimmedBytes));
    }

//    private static String fillBlock(String text) {
//        int spaceNum = text.getBytes().length%16==0?0:16-text.getBytes().length%16;
//        for (int i = 0; i<spaceNum; i++) text += " ";
//        return text;
//    }
//
//    private static byte[] getKey() {
//        String key = "";
//        for (int i=0; i < 2; i++) key += Long.toHexString(Double.doubleToLongBits(Math.random()));
//        return key.getBytes();
//    }
//
//    // some tests / demonstration
//
//    private static void keySensitiveTest() {
//        System.out.println("\n~~~ keySensitiveTest ~~~\n");
//        byte[] inputText = "very secret message".getBytes();
//        byte[] iv = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
//        byte[] key;
//
//        // 128
//        System.out.println("128 bit");
//        key = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
//        cipher = new AES(key);
//        System.out.println("ECB result --> " + new String(cipher.ECB_decrypt(cipher.ECB_encrypt(inputText))));
//        cipher = new AES(key, iv);
//        System.out.println("CBC result --> " + new String(cipher.CBC_decrypt(cipher.CBC_encrypt(inputText))));
//
//        // 192
//        System.out.println("192 bit");
//        key = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
//        cipher = new AES(key);
//        System.out.println("ECB result --> " + new String(cipher.ECB_decrypt(cipher.ECB_encrypt(inputText))));
//        cipher = new AES(key, iv);
//        System.out.println("CBC result --> " + new String(cipher.CBC_decrypt(cipher.CBC_encrypt(inputText))));
//
//        // 256
//        System.out.println("256 bit");
//        key = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
//        cipher = new AES(key);
//        System.out.println("ECB result --> " + new String(cipher.ECB_decrypt(cipher.ECB_encrypt(inputText))));
//        cipher = new AES(key, iv);
//        System.out.println("CBC result --> " + new String(cipher.CBC_decrypt(cipher.CBC_encrypt(inputText))));
//    }
//
//    private static void javaCryptoTest() {
//        System.out.println("\n~~~ javaCryptoTest ~~~\n");
//        System.out.println("AES 128 CBC");
//
//        byte[] inputText = "very secret message".getBytes();
//        byte[] key = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
//        byte[] iv = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
//
//        cipher = new AES(key, iv);
//    }
//    private static byte[] javaCryptoEncrypt(byte[] key, byte[] initVector, byte[] value) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector);
//            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
//
//            byte[] encrypted = cipher.doFinal(value);
//
//            return encrypted;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//
//    private static String javaCryptoDecrypt(byte[] key, byte[] initVector, byte[] encrypted) {
//        try {
//            IvParameterSpec iv = new IvParameterSpec(initVector);
//            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//
//            byte[] original = cipher.doFinal(encrypted);
//            return new String(original);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
}