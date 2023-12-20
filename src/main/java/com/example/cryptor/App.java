package com.example.cryptor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("server.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
//        String styleCss = getClass().getResource("style.css").toExternalForm();
        stage.setTitle("Cryptor");
        stage.setScene(scene);
        stage.setResizable(false);
//        scene.getStylesheets().add(styleCss);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}