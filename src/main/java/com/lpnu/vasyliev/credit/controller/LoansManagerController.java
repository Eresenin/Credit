package com.lpnu.vasyliev.credit.controller;


import com.lpnu.vasyliev.credit.controller.menu.Menu;
import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;
import com.lpnu.vasyliev.credit.dao.UserDAO;
import com.lpnu.vasyliev.credit.entity.ActiveLoan;
import com.lpnu.vasyliev.credit.service.Authorizator;
import com.lpnu.vasyliev.credit.service.Validator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.controller.menu.command.DefaultCommandLists.MAIN_MENU_COMMANDS;
import static com.lpnu.vasyliev.credit.service.UtilsFXML.changeScene;

public class LoansManagerController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(LoansManagerController.class);
    private static LoansManagerController instance;
    @FXML
    private TextField tf_loan_id;
    @FXML
    private Button button_back_to_main;
    @FXML
    private Button button_overview_offers;
    @FXML
    private Button button_execute;
    @FXML
    private RadioButton rb_take_a_loan;
    @FXML
    private RadioButton rb_pay_a_loan;
    @FXML
    private RadioButton rb_extend_credit_line;
    @FXML
    private RadioButton rb_update_loan;
    @FXML
    private Label label;

    @FXML
    private TableView<ActiveLoan> active_loans_table = new TableView<>();

    @FXML
    private TableColumn<ActiveLoan, Integer> id_col;
    @FXML
    private TableColumn<ActiveLoan, Integer> current_debt_sum_col;
    @FXML
    private TableColumn<ActiveLoan, Integer> time_left_col;
    @FXML
    private TableColumn<ActiveLoan, Integer> current_percentage_col;
    @FXML
    private TableColumn<ActiveLoan, String> debtor_login_col;
    @FXML
    private TableColumn<ActiveLoan, Integer> offer_id_col;
    @FXML
    private TableColumn<ActiveLoan, Integer> bank_id_col;

    private ObservableList<ActiveLoan> activeLoans;

    public static LoansManagerController getInstance() {
        return instance;
    }
    public String getTargetLoanId() {
        return tf_loan_id.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("entered", this);
        instance=this;
        ToggleGroup toggleGroup = new ToggleGroup();
        rb_take_a_loan.setToggleGroup(toggleGroup);
        rb_pay_a_loan.setToggleGroup(toggleGroup);
        rb_extend_credit_line.setToggleGroup(toggleGroup);
        rb_update_loan.setToggleGroup(toggleGroup);
        rb_take_a_loan.setSelected(true);

        initTable();

        button_execute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String id=tf_loan_id.getText();
                String command = ((RadioButton)toggleGroup.getSelectedToggle()).getText();

                if(Validator.validateInt(id) &&
                        ActiveLoanDAO.actualInstance.activeLoanExists(Integer.valueOf(id))) {
                    if(command.equals(rb_pay_a_loan.getText())) {
                        changeScene(actionEvent, "pay-a-loan.fxml", "Pay a loan");

                    }else if (command.equals(rb_update_loan.getText())){
                        changeScene(actionEvent, "update-loans.fxml", "Update a loan");
                    }else {
                        Menu mainMenu = new Menu(MAIN_MENU_COMMANDS);
                        mainMenu.run(command, id);
                    }

                    refreshTable();
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect data. Try again!");
                    alert.show();
                }
            }
        });
        //navigation buttons
        button_back_to_main.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                changeScene(actionEvent, "main-page.fxml", "Main page");
            }
        });
        button_overview_offers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                changeScene(actionEvent, "loans-overview.fxml", "Loans overview");
            }
        });
    }
    private void initTable(){
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        current_debt_sum_col.setCellValueFactory(new PropertyValueFactory<>("currentDebtAmount"));
        time_left_col.setCellValueFactory(new PropertyValueFactory<>("currentTimeLeftInMonths"));
        current_percentage_col.setCellValueFactory(new PropertyValueFactory<>("currentPercentagePerMonth"));
        debtor_login_col.setCellValueFactory(new PropertyValueFactory<>("debtorLogin"));
        offer_id_col.setCellValueFactory(new PropertyValueFactory<>("loanOfferId"));
        bank_id_col.setCellValueFactory(new PropertyValueFactory<>("bankId"));
        activeLoans = UserDAO.actualInstance.getAllActiveLoans(Authorizator.getCurrentUserLogin());
        active_loans_table.setItems(activeLoans);
    }
    private void refreshTable(){
        activeLoans = UserDAO.actualInstance.getAllActiveLoans(Authorizator.getCurrentUserLogin());
        active_loans_table.setItems(activeLoans);
    }
}
