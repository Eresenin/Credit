package com.lpnu.vasyliev.credit.dao.mysql.constants;

public class SQLQueries {
    public static final String activeLoanInfoByIdQuery = "SELECT * FROM Credit.active_loan WHERE id=?";
    public static final String loanOfferInfoByIdQuery = "SELECT * FROM Credit.loan_offer WHERE id=?";

    public static final String bankInfoByIdQuery = "SELECT * FROM Credit.bank WHERE id=?";
    public static final String userInfoByIdQuery = "SELECT * FROM Credit.user WHERE login=?";
    //----------------------------------------------------------------------------
    public static final String getAllUsersLoans = "SELECT * FROM Credit.active_loan WHERE debtor_login=?";
    public static final String getAllLoanOffersWithMinDebt="SELECT * FROM Credit.loan_offer WHERE debt>=?";
    public static final String getAllLoanOffersWithMaxPercentage="SELECT * FROM Credit.loan_offer WHERE percentage<=?";
    public static final String getAllLoanOffersWithBothConditions="SELECT * FROM Credit.loan_offer WHERE debt>=? AND percentage<=?";
    public static final String getAllLoanOffers="SELECT * FROM Credit.loan_offer";
    //----------------------------------------------------------------------------------
    public static final String addUser = "INSERT INTO Credit.user (login, password, name, money, status) VALUES(?, ?, ?, ?, ?)";
    public static final String addActiveLoan = "INSERT INTO Credit.active_loan (current_debt, current_percentage," +
            " current_time_left, debtor_login, bank_id, loan_offer_id) VALUES(?, ?, ?, ?, ?,?)";
    //---------------------------------------------------------------------------------
    public static final String removeActiveLoanById = "DELETE FROM Credit.active_loan WHERE id =?";
    public static final String payTheLoan = "UPDATE credit.active_loan" +
            " SET current_debt=? WHERE id=?";

    public static final String manipulateMoney = "UPDATE credit.user SET money=? WHERE login=?";
    public static final String extendCreditLine = "UPDATE credit.active_loan SET current_percentage=?, " +
            "current_time_left=? WHERE id=?";
    public static final String accrueInterest = "UPDATE credit.active_loan SET current_debt=?," +
            " current_time_left=? WHERE id=?";
    public static final String setStatus = "UPDATE credit.user SET status=? WHERE login=?";
    //----------------------------------------------------------------------------------
    public static final String checkIfLoginExists = "SELECT EXISTS(SELECT * FROM Credit.user WHERE login=?);";
    public static final String checkIfActiveLoansExists = "SELECT EXISTS(SELECT * FROM Credit.active_loan WHERE id=?);";
    public static final String checkIfLoanOfferExists = "SELECT EXISTS(SELECT * FROM Credit.loan_offer WHERE id=?);";

}
