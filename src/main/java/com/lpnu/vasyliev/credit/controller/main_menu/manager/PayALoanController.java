package com.lpnu.vasyliev.credit.controller.main_menu.manager;


import com.lpnu.vasyliev.credit.controller.utils.MenuCommands;
import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;
import com.lpnu.vasyliev.credit.service.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.service.ScenesManager.changeScene;


public class PayALoanController implements Initializable {

    @FXML
    private TextField tf_loan_id;
    @FXML
    private TextField tf_transaction_sum;
    @FXML
    private Button button_ok;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_ok.setOnAction(actionEvent -> {
            String targetLoanId =tf_loan_id.getText();
            String transactionAmount = tf_transaction_sum.getText();

            if(Validator.validateInt(transactionAmount) && Validator.validateInt(targetLoanId) &&
                    ActiveLoanDAO.actualInstance.activeLoanExists(Integer.valueOf(targetLoanId)))
            {
                MenuCommands.payTheLoan(Integer.valueOf(targetLoanId), Integer.valueOf(transactionAmount));
                changeScene(actionEvent, "loans-manager.fxml", "Loans manager");
            }else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect data. Try again!");
                alert.show();
            }
        });
    }
}
