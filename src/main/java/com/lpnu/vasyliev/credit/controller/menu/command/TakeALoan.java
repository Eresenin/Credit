package com.lpnu.vasyliev.credit.controller.menu.command;

import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TakeALoan implements MenuItem {
    private static final Logger logger = LoggerFactory.getLogger(TakeALoan.class);
    @Override
    public String toString() {
        return "Take a loan";
    }

    @Override
    public void execute(String targetLoanId) {
        ActiveLoanDAO activeLoanDAO = ActiveLoanDAO.actualInstance;
        LoanOfferDAO loanOfferDAO = LoanOfferDAO.actualInstance;

        activeLoanDAO.addActiveLoan(loanOfferDAO.getLoanOfferById(Integer.valueOf(targetLoanId)));
    }
}
