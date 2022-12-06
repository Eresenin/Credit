package com.lpnu.vasyliev.credit.dao;

import com.lpnu.vasyliev.credit.dao.mysql.MysqlLoanOfferDAO;
import com.lpnu.vasyliev.credit.entity.LoanOffer;
import javafx.collections.ObservableList;

public interface LoanOfferDAO {
    LoanOfferDAO actualInstance = new MysqlLoanOfferDAO();
    LoanOffer getLoanOfferById(int loanOfferId);
    public boolean loanOffersExists(int id);
    ObservableList<LoanOffer> getLoanOffersWithMinOfferSum(int minDebtAmount);
    ObservableList<LoanOffer> getLoanOffersWithMaxPercentage(int maxPercentage);
    ObservableList<LoanOffer> getLoanOffersWithBothConditions(int minDebtSum, int maxPercentage);
    ObservableList<LoanOffer> getAllLoanOffers();
}
