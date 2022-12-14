package com.lpnu.vasyliev.credit.controller.main_menu;

import com.lpnu.vasyliev.credit.controller.utils.MenuCommands;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;
import com.lpnu.vasyliev.credit.entity.LoanOffer;
import com.lpnu.vasyliev.credit.service.Validator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.service.ScenesManager.changeScene;

public class LoansOverviewController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(LoansOverviewController.class);

    @FXML
    private Button button_back_to_main;
    @FXML
    private Button button_manage_loans;
    @FXML
    private Button button_show_offers;
    @FXML
    private TextField tf_min_debt_sum;
    @FXML
    private TextField tf_max_month_percentage;
    @FXML
    private TableView<LoanOffer> offersTable = new TableView<>();
    @FXML
    private TableColumn<LoanOffer, Integer> id_col;
    @FXML
    private TableColumn<LoanOffer, Integer> debt_sum_col;
    @FXML
    private TableColumn<LoanOffer, Integer> debt_period_col;
    @FXML
    private TableColumn<LoanOffer, Integer> percentage_col;
    @FXML
    private TableColumn<LoanOffer, Integer> pen_percentage_col;
    @FXML
    private TableColumn<LoanOffer, Integer> extension_period_col;
    @FXML
    private TableColumn<LoanOffer, Integer> bank_id_col;

    private ObservableList<LoanOffer> loanOffers;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("entered", this);

        initTable();

        button_show_offers.setOnAction(actionEvent-> {
            String monthPercentage=tf_max_month_percentage.getText();

            if(!monthPercentage.isEmpty() && !Validator.validatePercent(monthPercentage)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid input value!");
                alert.show();
            }

            loanOffers = MenuCommands.getOffersByConditions(tf_min_debt_sum.getText(),
                    tf_max_month_percentage.getText());
            offersTable.setItems(loanOffers);
        });


        //navigation buttons
        button_back_to_main.setOnAction(actionEvent-> {
                changeScene(actionEvent, "main-page.fxml", "Main page");
        });

        button_manage_loans.setOnAction(actionEvent-> {
                changeScene(actionEvent, "loans-manager.fxml", "Loans manager");
        });
    }
    private boolean validateInput(){
        return Validator.validateInt(tf_min_debt_sum.getText()) ;
//                &&
//                Validator.validateInt(tf_max_month_percentage.getText())
//                &&
//                Integer.valueOf(tf_max_month_percentage.getText()) < 100;
    }

    private void initTable(){
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        debt_sum_col.setCellValueFactory(new PropertyValueFactory<>("debtAmount"));
        debt_period_col.setCellValueFactory(new PropertyValueFactory<>("debtPeriod"));
        percentage_col.setCellValueFactory(new PropertyValueFactory<>("percentagePerMonth"));
        pen_percentage_col.setCellValueFactory(new PropertyValueFactory<>("penaltyPercentagePerMonth"));
        extension_period_col.setCellValueFactory(new PropertyValueFactory<>("possibleExtensionPeriod"));
        bank_id_col.setCellValueFactory(new PropertyValueFactory<>("bankId"));
        loanOffers = LoanOfferDAO.actualInstance.getAllLoanOffers();
        offersTable.setItems(loanOffers);
    }

}
