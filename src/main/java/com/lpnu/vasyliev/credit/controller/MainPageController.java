package com.lpnu.vasyliev.credit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.service.ScenesManager.changeScene;

public class MainPageController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(MainPageController.class);

    @FXML
    private RadioButton rb_overview;
    @FXML
    private RadioButton rb_manage;

    @FXML
    private Button button_execute;
    @FXML
    private Button button_log_out;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("entered", this);


        ToggleGroup toggleGroup = new ToggleGroup();
        rb_overview.setToggleGroup(toggleGroup);
        rb_manage.setToggleGroup(toggleGroup);
        rb_overview.setSelected(true);


        button_execute.setOnAction(actionEvent-> {
                String choice = ((RadioButton) toggleGroup.getSelectedToggle()).getText();
                chooseNextScene(choice, actionEvent);
        });

        button_log_out.setOnAction(actionEvent-> {
                changeScene(actionEvent, "login-page.fxml", "Log in!");
        });
    }

    private void chooseNextScene(String choice, ActionEvent actionEvent) {
        String fxmlFile;
        if (choice.equals("Overview offers"))
            changeScene(actionEvent, "loans-overview.fxml", "Offers overview");//"offers-overview"
        else
            changeScene(actionEvent, "loans-manager.fxml", "Loans manager");
    }
}
