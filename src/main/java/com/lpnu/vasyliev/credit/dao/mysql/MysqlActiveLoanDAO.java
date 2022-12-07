package com.lpnu.vasyliev.credit.dao.mysql;

import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;
import com.lpnu.vasyliev.credit.dao.BankDAO;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;
import com.lpnu.vasyliev.credit.dao.UserDAO;
import com.lpnu.vasyliev.credit.dao.mysql.constants.SQLQueries;
import com.lpnu.vasyliev.credit.entity.ActiveLoan;
import com.lpnu.vasyliev.credit.entity.LoanOffer;
import com.lpnu.vasyliev.credit.entity.User;
import com.lpnu.vasyliev.credit.service.Authorizator;
import com.lpnu.vasyliev.credit.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

import static com.lpnu.vasyliev.credit.dao.mysql.ConnectionManager.*;
import static com.lpnu.vasyliev.credit.dao.mysql.constants.SQLQueries.*;

public class MysqlActiveLoanDAO implements ActiveLoanDAO {
    private static final Logger logger = LoggerFactory.getLogger(MysqlActiveLoanDAO.class);

    @Override
    public boolean addActiveLoan(LoanOffer loanOffer) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=getConnection();
            statement = connection.prepareStatement(SQLQueries.addActiveLoan);
            statement.setInt(1, loanOffer.getDebtAmount());
            statement.setInt(2, loanOffer.getPercentagePerMonth());
            statement.setInt(3, loanOffer.getDebtPeriod());
            statement.setString(4, Authorizator.getCurrentUserLogin());
            statement.setInt(5, loanOffer.getBankId());
            statement.setInt(6, loanOffer.getId());
            statement.executeUpdate();

            manipulateUserMoney(UserDAO.actualInstance.getUserByLogin(Authorizator.getCurrentUserLogin()),
                    loanOffer.getDebtAmount());
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }finally {
            closeConnection(connection);
            closeStatement(statement);
        }

        logger.info("exited", this);
        return true;
    }

    @Override
    public boolean accrueInterest(int monthsPassed, String userLogin) {
        logger.info("entered", this);
        Connection connection = null;
        PreparedStatement statement = null;

        UserDAO userDAO = new MysqlUserDAO();
        List<ActiveLoan> activeLoans = userDAO.getAllActiveLoans(userLogin);

        try {
            connection = getConnection();
            for (ActiveLoan activeLoan: activeLoans) {
                if(activeLoan.getCurrentTimeLeftInMonths()-monthsPassed<=0){
                    userDAO.handleOverdue(activeLoan);
                }

                statement = connection.prepareStatement(SQLQueries.accrueInterest);
                statement.setInt(1, (int)Math.ceil(activeLoan.getCurrentDebtAmount()+
                        (activeLoan.getCurrentPercentagePerMonth() * 0.01 *
                                activeLoan.getCurrentDebtAmount() * monthsPassed)));
                statement.setInt(2, activeLoan.getCurrentTimeLeftInMonths() - monthsPassed);
                statement.setInt(3, activeLoan.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }

        return true;
    }

    @Override
    public void payTheLoan(int transferAmount, ActiveLoan activeLoan) {
        logger.info("entered", this);

        if(transferAmount>activeLoan.getCurrentDebtAmount())
            transferAmount=activeLoan.getCurrentDebtAmount();

        if(transferAmount>UserDAO.actualInstance.getUserByLogin(activeLoan.getDebtorLogin()).getMoney())
            transferAmount=UserDAO.actualInstance.getUserByLogin(activeLoan.getDebtorLogin()).getMoney();

        if(activeLoan.getCurrentDebtAmount()-transferAmount==0){
            deleteActiveLoanById(activeLoan.getId());
        }
        Connection connection = null;
        PreparedStatement statement = null;
        User user = UserDAO.actualInstance.getUserByLogin(activeLoan.getDebtorLogin());
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLQueries.payTheLoan);
            statement.setInt(1, activeLoan.getCurrentDebtAmount()-transferAmount);
            statement.setInt(2, activeLoan.getId());
            statement.executeUpdate();

            manipulateUserMoney(user, -transferAmount);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }

    }

    public boolean activeLoanExists(int id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();

            statement = connection.prepareStatement(checkIfActiveLoansExists);
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

    private void manipulateUserMoney(User user, int sum){
        logger.info("entered", this);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLQueries.manipulateMoney);
            statement.setInt(1, user.getMoney()+sum);
            statement.setString(2, user.getLogin());
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }
    }


    @Override
    public boolean extendCreditLine(ActiveLoan activeLoan) {
        logger.info("entered", this);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(SQLQueries.extendCreditLine);
            statement.setInt(1, activeLoan.getCurrentPercentagePerMonth()+
                    LoanOfferDAO.actualInstance.getLoanOfferById(activeLoan.getLoanOfferId()).getPenaltyPercentagePerMonth());
            statement.setInt(2, activeLoan.getCurrentTimeLeftInMonths()+
                    LoanOfferDAO.actualInstance.getLoanOfferById(activeLoan.getLoanOfferId()).getPossibleExtensionPeriod());
            statement.setInt(3, activeLoan.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }

        return true;
    }

    @Override
    public ActiveLoan getActiveLoanById(int activeLoanId) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement=null;
        ActiveLoan activeLoan=new ActiveLoan();
        try{
            connection=getConnection();
            statement = connection.prepareStatement(SQLQueries.activeLoanInfoByIdQuery);
            statement.setInt(1, activeLoanId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                BankDAO bankDAO = new MysqlBankDAO();
                activeLoan.setBankId(rs.getInt("bank_id"));
                UserDAO userDAO = new MysqlUserDAO();
                activeLoan.setDebtorLogin(rs.getString("debtor_login"));
                activeLoan.setCurrentDebtAmount(rs.getInt("current_debt"));
                activeLoan.setCurrentPercentagePerMonth(rs.getInt("current_percentage"));
                activeLoan.setCurrentTimeLeftInMonths(rs.getInt("current_time_left"));
                LoanOfferDAO loanOfferDAO = new MysqlLoanOfferDAO();
                activeLoan.setLoanOfferId(rs.getInt("loan_offer_id"));
                activeLoan.setId(activeLoanId);
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }finally {
            closeConnection(connection);
            closeStatement(statement);
        }
        return activeLoan;
    }


    @Override
    public boolean deleteActiveLoanById(int activeLoanId) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement=null;
        try{
            connection=getConnection();
            statement = connection.prepareStatement(SQLQueries.removeActiveLoanById);
            statement.setInt(1, activeLoanId);
            statement.executeUpdate();

        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }finally {
            closeConnection(connection);
            closeStatement(statement);
        }
        return true;
    }
}
