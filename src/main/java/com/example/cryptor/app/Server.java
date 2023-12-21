//package com.example.cryptor.app;
//
//import com.example.cryptor.rsa.RSA;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.*;
//import java.math.BigInteger;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server {
//    private static final Logger logger = LogManager.getLogger(Server.class);
//    public static final int DESIRED_AES_KEY_LEN = 256;
//
//    public static void main(String[] args) throws IOException {
//        // 1. Create a server socket
//        ServerSocket server = new ServerSocket(95);
//
//        // 2. Wait for the connection
//        System.out.println("Server started. Waiting for the client....");
//
//        // 3. Get the socket I/O streams and perform the processing
//        try (Socket conn = server.accept()) {
//            System.out.println("Client connected!!");
//            // 3.1 --> InputStream; to receive information from client
//            PrintWriter out = null;
//            BufferedReader in = null;
//
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(conn.getOutputStream());
//            ObjectInputStream objectInputStream = new ObjectInputStream(conn.getInputStream());
//
//            // Read and print the message from the client
//            String messageToSend = "Hello from the server!";
//            objectOutputStream.writeObject(messageToSend);
//            objectOutputStream.flush();
//
//            Object receivedObject = objectInputStream.readObject();
//            String receivedString = (String) receivedObject;
//            System.out.println("Received String from server: " + receivedString);
//
//            ServerExecute serverExecute = new ServerExecute();
//            BigInteger notTrimmedKeyFromECC = serverExecute.executeECDH(out, objectOutputStream, in, objectInputStream);
//            String msgToEncrypt = "Hello this is sample message that is going to be encrypted and decrypted...";
//            String encryptedMsg = serverExecute.executeAESEncrypt(notTrimmedKeyFromECC, msgToEncrypt);
//            objectOutputStream.writeObject(encryptedMsg);
//            logger.info("Sending encrypted string with key that user has");
//
//            RSA rsa = new RSA(256, 128);
//            objectOutputStream.writeObject(rsa.getE());
//            objectOutputStream.writeObject(rsa.getN());
//            objectOutputStream.writeObject(rsa.getN());
//
//            String encryptedStringWithPublicKey = (String) objectInputStream.readObject();
//            String decrypted = rsa.decrypt(encryptedStringWithPublicKey);
//            System.out.println("Decrypted string: " + decrypted);
//
//
////            serverExecute.executeAES(objectOutputStream, objectInputStream, bigInteger, stringToEncrypt);
//
//            int exitingCode = objectInputStream.read();
//
//            // 4. Close the connection
////            in.close();   // Close the input stream
////            out.close();  // Close the output stream
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
