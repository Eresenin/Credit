package com.lpnu.vasyliev.credit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ApplicationStarter extends Application {
    private static Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);
    @Override
    public void start(Stage stage) throws IOException {
        logger.info("entered", this);

        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStarter.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}