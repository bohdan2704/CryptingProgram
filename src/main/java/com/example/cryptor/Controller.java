package com.example.cryptor;

import com.example.cryptor.app.ServerExecute;
import com.example.cryptor.eliptic.ECCKeyExchange;
import com.example.cryptor.rsa.RSA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField textFieldEcdh;
    @FXML
    private Text serverLbl;
    private ServerSocket server;
    private Socket conn;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    @FXML
    protected void onRsaBtnClick() {
        StringBuilder b = new StringBuilder("We just sent public key to user/client." + System.lineSeparator() + "So it can encrypt message using them and send encrypted message to us, so we can use private key to decrypt it. ").append(System.lineSeparator());
       serverLbl.setText(b.toString());
        try {
            RSA rsa = new RSA(256, 128);
            b.append("This is public key, which consists of E and N (").append(rsa.getE()).append(", ").append(rsa.getN()).append(". ").append(System.lineSeparator());
            out.writeObject(rsa.getE());
            out.writeObject(rsa.getN());
            b.append("Now we are waiting for client to pass encrypted string to us. ").append(System.lineSeparator());

            // Wait for the return
            String encryptedStringWithPublicKey = (String) in.readObject();
            String encryptedStringWithPublicKeyIn36Base = new BigInteger(encryptedStringWithPublicKey.replaceAll("\\s", "")).toString(36);
            // new BigInteger(encryptedStringWithPublicKey).toString(36)
            b.append("Client send to us this encrypted message: ").append(encryptedStringWithPublicKeyIn36Base).append(". ").append(System.lineSeparator());
            String decrypted = rsa.decrypt(encryptedStringWithPublicKey);
            b.append("Actual send message is: ").append(decrypted).append(System.lineSeparator());
            serverLbl.setText(b.toString());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onEcdhBtnClick() {
        try {
            StringBuilder b = new StringBuilder();
            String msgToEncrypt = textFieldEcdh.getText();
            ECCKeyExchange eccKeyExchange = new ECCKeyExchange();
            BigInteger notTrimmedKeyFromECC = eccKeyExchange.serverStart(out, in, b);
            b.append("Thanks to ECDH algorithm we got shared 256 bit secret key, that will be used in AES encryption: ").append(System.lineSeparator()).append(notTrimmedKeyFromECC.toString(36));
            serverLbl.setText(b.toString());
            String encryptedMsg = ServerExecute.executeAESEncrypt(notTrimmedKeyFromECC, msgToEncrypt);
            out.writeObject(encryptedMsg);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = new ServerSocket(95);
            System.out.println("Server started successfully!!");
            conn = server.accept();
            System.out.println("Client connected!!");
            out = new ObjectOutputStream(conn.getOutputStream());
            in = new ObjectInputStream(conn.getInputStream());
            String messageToSend = "Hello from the server to client! Digital handshake";
            out.writeObject(messageToSend);
            out.flush();

            Object receivedObject = in.readObject();
            String receivedString = (String) receivedObject;
            System.out.println("Received String from server: " + receivedString);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}