package com.lpnu.vasyliev.credit.service;

import com.lpnu.vasyliev.credit.ApplicationStarter;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;
import com.lpnu.vasyliev.credit.entity.LoanOffer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.lpnu.vasyliev.credit.service.Validator.validateInt;

public class UtilsFXML {
    private static Logger logger = LoggerFactory.getLogger(UtilsFXML.class);
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


    public static ObservableList<LoanOffer> getOffersByConditions(String minDebtSum, String maxDebtPercentage){
        if(validateInt(minDebtSum) && validateInt(maxDebtPercentage))
            return LoanOfferDAO.actualInstance.getLoanOffersWithBothConditions(Integer.valueOf(minDebtSum),
                    Integer.valueOf(maxDebtPercentage));
        else if(validateInt(minDebtSum))
            return LoanOfferDAO.actualInstance.getLoanOffersWithMinOfferSum(Integer.valueOf(minDebtSum));
        else if(validateInt(minDebtSum))
            return LoanOfferDAO.actualInstance.getLoanOffersWithMaxPercentage(Integer.valueOf(maxDebtPercentage));
        else
            return LoanOfferDAO.actualInstance.getAllLoanOffers();
    }
}
