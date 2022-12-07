package com.lpnu.vasyliev.credit.dao;

import com.lpnu.vasyliev.credit.dao.mysql.MysqlActiveLoanDAO;
import com.lpnu.vasyliev.credit.entity.ActiveLoan;
import com.lpnu.vasyliev.credit.entity.LoanOffer;

public interface ActiveLoanDAO {
    ActiveLoanDAO actualInstance = new MysqlActiveLoanDAO();
    boolean addActiveLoan(LoanOffer loanOffer);

    boolean accrueInterest(int monthsPassed, String userLogin);
    void payTheLoan(int moneyAmount, ActiveLoan activeLoan);
    public boolean activeLoanExists(int id);
    boolean extendCreditLine(ActiveLoan activeLoan);

    ActiveLoan getActiveLoanById(int activeLoanId);
    boolean deleteActiveLoanById(int activeLoanId);
}
