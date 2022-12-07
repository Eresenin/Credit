package com.lpnu.vasyliev.credit.controller.main_menu;

import com.lpnu.vasyliev.credit.dao.UserDAO;
import com.lpnu.vasyliev.credit.entity.ActiveLoan;
import com.lpnu.vasyliev.credit.entity.User;
import com.lpnu.vasyliev.credit.service.Authorizator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.lpnu.vasyliev.credit.controller.utils.FXMLConstants.FXML_FILES_MAP;
import static com.lpnu.vasyliev.credit.service.Authorizator.getCurrentUserLogin;
import static com.lpnu.vasyliev.credit.service.ScenesManager.changeScene;

public class LoansManagerController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(LoansManagerController.class);
    @FXML
    private Label label_wallet_balance;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logger.info("entered", this);

        User user = UserDAO.actualInstance.getUserByLogin(getCurrentUserLogin());
        label_wallet_balance.setText("Current wallet balance: " + user.getMoney());

        ToggleGroup toggleGroup = new ToggleGroup();
        rb_take_a_loan.setToggleGroup(toggleGroup);
        rb_pay_a_loan.setToggleGroup(toggleGroup);
        rb_extend_credit_line.setToggleGroup(toggleGroup);
        rb_update_loan.setToggleGroup(toggleGroup);
        rb_take_a_loan.setSelected(true);

        initTable();

        button_execute.setOnAction(actionEvent-> {

            String command = ((RadioButton)toggleGroup.getSelectedToggle()).getText();
            String fxmlFile = FXML_FILES_MAP.get(command);
            changeScene(actionEvent, fxmlFile, command);

        });

        //navigation buttons
        button_back_to_main.setOnAction(actionEvent-> {
                changeScene(actionEvent, "main-page.fxml", "Main page");
        });

        button_overview_offers.setOnAction(actionEvent-> {
                changeScene(actionEvent, "loans-overview.fxml", "Loans overview");
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

//    private void refreshTable(){
//        activeLoans = UserDAO.actualInstance.getAllActiveLoans(Authorizator.getCurrentUserLogin());
//        active_loans_table.setItems(activeLoans);
//    }
}
