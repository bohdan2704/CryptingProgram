package com.example.cryptor.app;

import com.example.cryptor.eliptic.ECCKeyExchange;
import com.example.cryptor.sync.AES;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

//import static com.example.cryptor.app.Server.DESIRED_AES_KEY_LEN;

public class ServerExecute {
    private static final int DESIRED_AES_KEY_LEN = 256;
//    public BigInteger executeECDH(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
//        System.out.println("ServerExecuteECDH");
//        ECCKeyExchange eccKeyExchange = new ECCKeyExchange();
//        try {
//            return eccKeyExchange.serverStart(objectOutputStream, objectInputStream, null);
//        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static String executeAESDecrypt(BigInteger key, String encryptedMsg) {
        key = key.shiftRight(key.bitLength() - DESIRED_AES_KEY_LEN + 1 );
        System.out.println(Arrays.toString(key.toByteArray()));
        System.out.println(key.toByteArray().length);
        AES aes = new AES(key.toByteArray());

        System.out.println("Encrypted msg: " + encryptedMsg);
        byte[] decrypted = aes.ECB_decrypt(AES.hexStringToByteArray(encryptedMsg));
        String decryptedMsg = new String(AES.trimZeroesFromEnd(decrypted));
        System.out.println("Decrypted msg: " + decryptedMsg);
        return decryptedMsg;
    }

    public static String executeAESEncrypt(BigInteger key, String msgToEncrypt) {
        key = key.shiftRight(key.bitLength() - DESIRED_AES_KEY_LEN + 1);
        System.out.println(Arrays.toString(key.toByteArray()));
        System.out.println(key.toByteArray().length);

        AES aes = new AES(key.toByteArray());
        byte[] ecbEncrypt = aes.ECB_encrypt(msgToEncrypt.getBytes());
        return AES.byteArrayToHexString(ecbEncrypt);
    }
}
