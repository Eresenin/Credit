package com.lpnu.vasyliev.credit.service;

import com.lpnu.vasyliev.credit.ApplicationStarter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ScenesManager {
    private static Logger logger = LoggerFactory.getLogger(ScenesManager.class);
    public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title){
        Parent root = null;

        try {
            root = FXMLLoader.load(ApplicationStarter.class.getResource(fxmlFile));
        }catch (IOException e){
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

}
