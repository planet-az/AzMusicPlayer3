package com.example.az;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * ミュージックプレーヤーの起動クラスです
 * 
 * @author Azumi Hirata
 *
 */
public class AzMusicPlayerApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("AzMusicPlayerView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Az Music Player Ver.3");
        stage.getIcons().add(new Image("/az.jpg"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
