package com.example.cryptor;

import com.example.cryptor.app.ServerExecute;
import com.example.cryptor.rsa.RSA;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {
    @FXML
    private TextField textFieldRsa;

    @FXML
    private TextField textFieldEcdh;


    @FXML
    protected void onRsaBtnClick() {
        try (ServerSocket server = new ServerSocket(95)){
            // 2. Wait for the connection
            System.out.println("Server started. Waiting for the client....");

            // 3. Get the socket I/O streams and perform the processing
            try (Socket conn = server.accept()) {
                System.out.println("Client connected!!");
                // 3.1 --> InputStream; to receive information from client
                PrintWriter out = null;
                BufferedReader in = null;

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(conn.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(conn.getInputStream());

                // Read and print the message from the client
                String messageToSend = "Hello from the server!";
                objectOutputStream.writeObject(messageToSend);
                objectOutputStream.flush();

                Object receivedObject = objectInputStream.readObject();
                String receivedString = (String) receivedObject;
                System.out.println("Received String from server: " + receivedString);

                RSA rsa = new RSA(256, 128);
                objectOutputStream.writeObject(rsa.getE());
                objectOutputStream.writeObject(rsa.getN());
                objectOutputStream.writeObject(rsa.getN());

                String encryptedStringWithPublicKey = (String) objectInputStream.readObject();
                String decrypted = rsa.decrypt(encryptedStringWithPublicKey);
                System.out.println("Decrypted string: " + decrypted);


//            serverExecute.executeAES(objectOutputStream, objectInputStream, bigInteger, stringToEncrypt);

                int exitingCode = objectInputStream.read();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onEcdhBtnClick() {
        String textToEncrypt = textFieldEcdh.getText();
        System.out.println("Clicked");
    }
}