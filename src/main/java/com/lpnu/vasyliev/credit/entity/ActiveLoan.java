package com.lpnu.vasyliev.credit.entity;

public class ActiveLoan {
    private int id;
    private int bankId;
    private String debtorLogin;
    private int currentPercentagePerMonth;
    private int currentDebtAmount;
    private int currentTimeLeftInMonths;
    private int loanOfferId;

    public int getLoanOfferId() {
        return loanOfferId;
    }

    public void setLoanOfferId(int loanOfferId) {
        this.loanOfferId = loanOfferId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getDebtorLogin() {
        return debtorLogin;
    }

    public void setDebtorLogin(String debtorLogin) {
        this.debtorLogin = debtorLogin;
    }

    public int getCurrentPercentagePerMonth() {
        return currentPercentagePerMonth;
    }

    public void setCurrentPercentagePerMonth(int currentPercentagePerMonth) {
        this.currentPercentagePerMonth = currentPercentagePerMonth;
    }

    public int getCurrentDebtAmount() {
        return currentDebtAmount;
    }

    public void setCurrentDebtAmount(int currentDebtAmount) {
        this.currentDebtAmount = currentDebtAmount;
    }

    public int getCurrentTimeLeftInMonths() {
        return currentTimeLeftInMonths;
    }

    public void setCurrentTimeLeftInMonths(int currentTimeLeftInMonths) {
        this.currentTimeLeftInMonths = currentTimeLeftInMonths;
    }


}
