package com.lpnu.vasyliev.credit.controller.menu.command;

import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendCreditLine implements MenuItem {
    private static final Logger logger = LoggerFactory.getLogger(ExtendCreditLine.class);
    @Override
    public String toString() {
        return " Extend credit line";
    }

    @Override
    public void execute(String targetLoanId) {
        logger.info("entered", this);

        ActiveLoanDAO activeLoanDAO = ActiveLoanDAO.actualInstance;
        activeLoanDAO.extendCreditLine(activeLoanDAO.getActiveLoanById(Integer.valueOf(targetLoanId)));

        logger.info("exited", this);
    }
}
