package com.lpnu.vasyliev.credit.entity;

public class LoanOffer {
    private int id;
    private int bankId;
    private int percentagePerMonth;
    private int penaltyPercentagePerMonth;
    private int possibleExtensionPeriod;
    private int debtPeriod;
    private int debtAmount;

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

    public int getPercentagePerMonth() {
        return percentagePerMonth;
    }

    public void setPercentagePerMonth(int percentagePerMonth) {
        this.percentagePerMonth = percentagePerMonth;
    }

    public int getPenaltyPercentagePerMonth() {
        return penaltyPercentagePerMonth;
    }

    public void setPenaltyPercentagePerMonth(int penaltyPercentagePerMonth) {
        this.penaltyPercentagePerMonth = penaltyPercentagePerMonth;
    }

    public int getPossibleExtensionPeriod() {
        return possibleExtensionPeriod;
    }

    public void setPossibleExtensionPeriod(int possibleExtensionPeriod) {
        this.possibleExtensionPeriod = possibleExtensionPeriod;
    }

    public int getDebtPeriod() {
        return debtPeriod;
    }

    public void setDebtPeriod(int debtPeriod) {
        this.debtPeriod = debtPeriod;
    }

    public int getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(int debtAmount) {
        this.debtAmount = debtAmount;
    }

}
