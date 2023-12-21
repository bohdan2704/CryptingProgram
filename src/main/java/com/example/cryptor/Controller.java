package com.example.cryptor;

import com.example.cryptor.app.ServerExecute;
import com.example.cryptor.eliptic.ECCKeyExchange;
import com.example.cryptor.rsa.RSA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField textFieldRsa;
    @FXML
    private Text clientLbl;
    private Socket conn;
    private ObjectOutputStream out;
    private ObjectInputStream in;


    @FXML
    protected void onRsaBtnClick() {
        try {
            String toEncryptAndSend = textFieldRsa.getText();
            BigInteger publicKeyE = (BigInteger) in.readObject();
            BigInteger publicKeyN = (BigInteger) in.readObject();
            String encrypted = RSA.encrypt(toEncryptAndSend, publicKeyE, publicKeyN);
            out.writeObject(encrypted);
            clientLbl.setText("Message was encrypted with public key (E, N) and successfully sent." + "Key E: " + publicKeyE.toString(36) + System.lineSeparator() + "Key N: " + publicKeyN.toString(36) );
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void onEcdhBtnClick() {

        try {
            ECCKeyExchange eccKeyExchange = new ECCKeyExchange();
            BigInteger notTrimmedKeyFromECC = eccKeyExchange.clientStart(out, in);
//            String msgToEncrypt = "This is sample message that is going to be encrypted and decrypted using ECDH...";
//            String encryptedMsg = ServerExecute.executeAESEncrypt(notTrimmedKeyFromECC, msgToEncrypt);
            String encryptedMsg = (String) in.readObject();
            String decryptedMsg = ServerExecute.executeAESDecrypt(notTrimmedKeyFromECC, encryptedMsg);
            clientLbl.setText("Encrypted msg we just got: " + encryptedMsg + System.lineSeparator() + "Decrypted message from server using ECDH shared key that is changing everytime and AES:"+System.lineSeparator()+decryptedMsg);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            conn = new Socket("localhost", 95);
            out = new ObjectOutputStream(conn.getOutputStream());
            in = new ObjectInputStream(conn.getInputStream());

            String messageToSend = "Hello from the client to server! Digital handshake!";
            out.writeObject(messageToSend);
            out.flush();

            String gotThisString = (String) in.readObject();
            System.out.println(gotThisString);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}