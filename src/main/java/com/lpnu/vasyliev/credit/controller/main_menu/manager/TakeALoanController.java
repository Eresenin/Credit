package com.lpnu.vasyliev.credit.controller.main_menu.manager;

import com.lpnu.vasyliev.credit.controller.utils.MenuCommands;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;
import com.lpnu.vasyliev.credit.service.Validator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.service.ScenesManager.changeScene;

public class TakeALoanController implements Initializable {

    @FXML
    private TextField tf_loan_id;
    @FXML
    private Button button_ok;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_ok.setOnAction(actionEvent -> {
            String targetLoanId=tf_loan_id.getText();

            if(Validator.validateInt(targetLoanId) &&
                    LoanOfferDAO.actualInstance.loanOffersExists(Integer.valueOf(targetLoanId)))
            {
                MenuCommands.takeTheLoan(Integer.valueOf(targetLoanId));
                changeScene(actionEvent, "loans-manager.fxml", "Loans manager");
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect data. Try again!");
                alert.show();
            }

        });
    }
}
