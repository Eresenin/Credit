package com.lpnu.vasyliev.credit.controller.authorization;

import com.lpnu.vasyliev.credit.service.ScenesManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.service.Authorizator.SignUp;
import static com.lpnu.vasyliev.credit.service.ScenesManager.changeScene;

public class SignUpController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(SignUpController.class);
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_balance;
    @FXML
    private Button button_log_in;
    @FXML
    private Button button_sign_up;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("entered", this);
        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ScenesManager.changeScene(actionEvent, "login-page.fxml", "Log in!");
            }
        });

        button_sign_up.setOnAction(actionEvent-> {
                try {
                    SignUp(tf_username.getText(), tf_password.getText(), tf_name.getText(), tf_balance.getText());
                    changeScene(actionEvent, "main-page.fxml", "Main page");
                }catch (IllegalArgumentException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect data. Try again!");
                    alert.show();
                }
        });
    }
}
