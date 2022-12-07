package com.lpnu.vasyliev.credit.controller.utils;

import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;
import com.lpnu.vasyliev.credit.entity.LoanOffer;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lpnu.vasyliev.credit.service.Authorizator.getCurrentUserLogin;
import static com.lpnu.vasyliev.credit.service.Validator.validateInt;

public class MenuCommands {
    private static final Logger logger = LoggerFactory.getLogger(MenuCommands.class);

    public static void takeTheLoan(int targetLoanId){
        logger.info("entered", MenuCommands.class.getName());

        ActiveLoanDAO activeLoanDAO = ActiveLoanDAO.actualInstance;
        LoanOffer loanOffer = LoanOfferDAO.actualInstance.getLoanOfferById(targetLoanId);
        activeLoanDAO.addActiveLoan(loanOffer);

        logger.info("exited" + MenuCommands.class.getName());
    }

    public static void payTheLoan(int targetLoanId, int transferAmount){
        logger.info("entered", MenuCommands.class.getName());

        ActiveLoanDAO activeLoanDAO = ActiveLoanDAO.actualInstance;
        activeLoanDAO.payTheLoan(transferAmount, activeLoanDAO.getActiveLoanById(targetLoanId));

        logger.info("exited" + MenuCommands.class.getName());
    }

    public static void extendCreditLine(int targetLoanId){
        logger.info("entered", MenuCommands.class.getName());

        ActiveLoanDAO activeLoanDAO = ActiveLoanDAO.actualInstance;
        activeLoanDAO.extendCreditLine(activeLoanDAO.getActiveLoanById(targetLoanId));

        logger.info("exited" + MenuCommands.class.getName());
    }

    public static void updateLoans(int monthSinceLastUpdated){
        logger.info("entered", MenuCommands.class.getName());

        ActiveLoanDAO activeLoanDAO = ActiveLoanDAO.actualInstance;
        activeLoanDAO.accrueInterest(monthSinceLastUpdated, getCurrentUserLogin());

        logger.info("exited" + MenuCommands.class.getName());
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
