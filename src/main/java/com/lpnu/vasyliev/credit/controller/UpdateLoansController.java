package com.lpnu.vasyliev.credit.controller;


import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;
import com.lpnu.vasyliev.credit.service.Authorizator;
import com.lpnu.vasyliev.credit.service.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.service.UtilsFXML.changeScene;


public class UpdateLoansController implements Initializable {

    @FXML
    private TextField tf_month_passed;
    @FXML
    private Button button_ok;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_ok.setOnAction(actionEvent -> {
            String monthPassed = tf_month_passed.getText();

            if(Validator.validateInt(monthPassed)){
                ActiveLoanDAO.actualInstance.accrueInterest(Integer.valueOf(monthPassed),
                        Authorizator.getCurrentUserLogin());
                changeScene(actionEvent, "loans-manager.fxml", "Loans manager");
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect data. Try again!");
                alert.show();
            }
        });
    }
}
