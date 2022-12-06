package com.lpnu.vasyliev.credit.dao.mysql;

import com.lpnu.vasyliev.credit.dao.BankDAO;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;
import com.lpnu.vasyliev.credit.dao.mysql.constants.SQLQueries;
import com.lpnu.vasyliev.credit.entity.LoanOffer;
import com.lpnu.vasyliev.credit.service.EmailSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.lpnu.vasyliev.credit.dao.mysql.ConnectionManager.*;
import static com.lpnu.vasyliev.credit.dao.mysql.constants.SQLQueries.checkIfLoanOfferExists;


public class MysqlLoanOfferDAO implements LoanOfferDAO {

    private static final Logger logger = LoggerFactory.getLogger(MysqlActiveLoanDAO.class);
    @Override
    public LoanOffer getLoanOfferById(int loanOfferId) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement=null;
        LoanOffer loanOffer = new LoanOffer();
        try{
            connection= ConnectionManager.getConnection();
            statement = connection.prepareStatement(SQLQueries.loanOfferInfoByIdQuery);
            statement.setInt(1, loanOfferId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                loanOffer.setId(loanOfferId);
                BankDAO bankDAO = new MysqlBankDAO();
                loanOffer.setBankId(rs.getInt("bank_id"));
                loanOffer.setDebtAmount(rs.getInt("debt"));
                loanOffer.setDebtPeriod(rs.getInt("debt_period"));
                loanOffer.setPercentagePerMonth(rs.getInt("percentage"));
                loanOffer.setPenaltyPercentagePerMonth(rs.getInt("penalty_percentage"));
                loanOffer.setPossibleExtensionPeriod(rs.getInt("extension_period"));
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }finally {
            ConnectionManager.closeConnection(connection);
            ConnectionManager.closeStatement(statement);
        }
        return loanOffer;
    }
    @Override
    public boolean loanOffersExists(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();

            statement = connection.prepareStatement(checkIfLoanOfferExists);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1)==1 ? true : false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }
    }

    @Override
    public ObservableList<LoanOffer> getLoanOffersWithMinOfferSum(int minDebtAmount) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement = null;
        ObservableList<LoanOffer> loanOffers = FXCollections.observableArrayList();

        try{
            connection= ConnectionManager.getConnection();
            statement=connection.prepareStatement(SQLQueries.getAllLoanOffersWithMinDebt);
            statement.setInt(1, minDebtAmount);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LoanOffer loanOffer=new LoanOffer();
                loanOffer.setId(rs.getInt("id"));
                BankDAO bankDAO = new MysqlBankDAO();
                loanOffer.setBankId(rs.getInt("bank_id"));
                loanOffer.setDebtAmount(rs.getInt("debt"));
                loanOffer.setDebtPeriod(rs.getInt("debt_period"));
                loanOffer.setPercentagePerMonth(rs.getInt("percentage"));
                loanOffer.setPenaltyPercentagePerMonth(rs.getInt("penalty_percentage"));
                loanOffer.setPossibleExtensionPeriod(rs.getInt("extension_period"));
                loanOffers.add(loanOffer);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }finally {
            ConnectionManager.closeConnection(connection);
            ConnectionManager.closeStatement(statement);
        }

        return loanOffers;
    }

    @Override
    public ObservableList<LoanOffer> getLoanOffersWithMaxPercentage(int maxPercentage) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement = null;
        ObservableList<LoanOffer> loanOffers = FXCollections.observableArrayList();

        try{
            connection= ConnectionManager.getConnection();
            statement=connection.prepareStatement(SQLQueries.getAllLoanOffersWithMaxPercentage);
            statement.setInt(1, maxPercentage);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LoanOffer loanOffer=new LoanOffer();
                loanOffer.setId(rs.getInt("id"));
                BankDAO bankDAO = new MysqlBankDAO();
                loanOffer.setBankId(rs.getInt("bank_id"));
                loanOffer.setDebtAmount(rs.getInt("debt"));
                loanOffer.setDebtPeriod(rs.getInt("debt_period"));
                loanOffer.setPercentagePerMonth(rs.getInt("percentage"));
                loanOffer.setPenaltyPercentagePerMonth(rs.getInt("penalty_percentage"));
                loanOffer.setPossibleExtensionPeriod(rs.getInt("extension_period"));
                loanOffers.add(loanOffer);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }

        return loanOffers;
    }

    @Override
    public ObservableList<LoanOffer> getLoanOffersWithBothConditions(int minDebtSum, int maxPercentage) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement = null;
        ObservableList<LoanOffer> loanOffers = FXCollections.observableArrayList();

        try{
            connection= ConnectionManager.getConnection();
            statement=connection.prepareStatement(SQLQueries.getAllLoanOffersWithBothConditions);
            statement.setInt(1, minDebtSum);
            statement.setInt(2, maxPercentage);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LoanOffer loanOffer=new LoanOffer();
                loanOffer.setId(rs.getInt("id"));
                BankDAO bankDAO = new MysqlBankDAO();
                loanOffer.setBankId(rs.getInt("bank_id"));
                loanOffer.setDebtAmount(rs.getInt("debt"));
                loanOffer.setDebtPeriod(rs.getInt("debt_period"));
                loanOffer.setPercentagePerMonth(rs.getInt("percentage"));
                loanOffer.setPenaltyPercentagePerMonth(rs.getInt("penalty_percentage"));
                loanOffer.setPossibleExtensionPeriod(rs.getInt("extension_period"));
                loanOffers.add(loanOffer);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }

        return loanOffers;
    }

    @Override
    public ObservableList<LoanOffer> getAllLoanOffers() {
        logger.info("entered", this);
        Connection connection=null;
        Statement statement = null;
        ObservableList<LoanOffer> loanOffers = FXCollections.observableArrayList();

        try{
            connection= ConnectionManager.getConnection();
            statement=connection.createStatement();
            ResultSet rs =statement.executeQuery(SQLQueries.getAllLoanOffers);
            while (rs.next()){
                LoanOffer loanOffer=new LoanOffer();
                loanOffer.setId(rs.getInt("id"));
                BankDAO bankDAO = new MysqlBankDAO();
                loanOffer.setBankId(rs.getInt("bank_id"));
                loanOffer.setDebtAmount(rs.getInt("debt"));
                loanOffer.setDebtPeriod(rs.getInt("debt_period"));
                loanOffer.setPercentagePerMonth(rs.getInt("percentage"));
                loanOffer.setPenaltyPercentagePerMonth(rs.getInt("penalty_percentage"));
                loanOffer.setPossibleExtensionPeriod(rs.getInt("extension_period"));
                loanOffers.add(loanOffer);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }finally {
            ConnectionManager.closeConnection(connection);
            ConnectionManager.closeStatement(statement);
        }

        return loanOffers;
    }
}
