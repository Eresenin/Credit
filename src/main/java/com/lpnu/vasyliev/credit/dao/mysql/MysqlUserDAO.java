package com.lpnu.vasyliev.credit.dao.mysql;

import com.lpnu.vasyliev.credit.dao.ActiveLoanDAO;
import com.lpnu.vasyliev.credit.dao.BankDAO;
import com.lpnu.vasyliev.credit.dao.LoanOfferDAO;
import com.lpnu.vasyliev.credit.dao.UserDAO;

import com.lpnu.vasyliev.credit.entity.ActiveLoan;
import com.lpnu.vasyliev.credit.entity.User;

import java.sql.*;
import java.util.Objects;

import com.lpnu.vasyliev.credit.service.EmailSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.*;


import static com.lpnu.vasyliev.credit.dao.mysql.constants.SQLQueries.*;
import static com.lpnu.vasyliev.credit.dao.mysql.ConnectionManager.*;

public class MysqlUserDAO implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(MysqlUserDAO.class);

    @Override
    public void addUser(User user) {
        logger.info("entered", this);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(addUser);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getMoney());
            statement.setString(5, user.getStatus());
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
    public User getUserByLogin(String login) {
        logger.info("entered", this);
        Connection connection = null;
        PreparedStatement statement = null;
        User user = new User();
        try {
            connection = getConnection();
            statement = connection.prepareStatement(userInfoByIdQuery);
            statement.setString(1, login);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user.setLogin(login);
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getString("status"));
                user.setMoney(rs.getInt("money"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }
        return user;
    }

    @Override
    public boolean acceptableStatus(String userLogin) {
        logger.info("entered", this);
        return !Objects.equals(getUserByLogin(userLogin).getStatus(), "overdue");
    }

    @Override
    public void setStatus(String newStatus, String userLogin) {
        logger.info("entered", this);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();

            statement = connection.prepareStatement(setStatus);
            statement.setString(1, newStatus);
            statement.setString(2, userLogin);
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
    public boolean passwordMatchesLogin(String login, String password) {
        UserDAO userDAO = new MysqlUserDAO();
        User user = userDAO.getUserByLogin(login);
        return Objects.equals(user.getPassword(), password);
    }

    @Override
    public boolean loginExists(String login) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean loginExists;

        try {
            connection = getConnection();

            statement = connection.prepareStatement(checkIfLoginExists);
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            rs.next();
            loginExists = rs.getInt(1)==1 ? true : false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }
        return loginExists;
    }

    public void handleOverdue(ActiveLoan activeLoan) {
        ActiveLoanDAO activeLoanDAO = new MysqlActiveLoanDAO();
        activeLoanDAO.payTheLoan(UserDAO.actualInstance.getUserByLogin(activeLoan.getDebtorLogin()).getMoney(), activeLoan);
        if (!debtIsPaid(activeLoan.getId(), activeLoanDAO)) {
            setStatus("hasOverdue", UserDAO.actualInstance.getUserByLogin(activeLoan.getDebtorLogin()).getLogin());
            throw new RuntimeException("You have not paid off in time! " +
                    "Bank will sue you!");
        }
    }

    private boolean debtIsPaid(int activeLoanId, ActiveLoanDAO activeLoanDAO) {
        return activeLoanDAO.getActiveLoanById(activeLoanId).getCurrentDebtAmount() == 0;
    }

    @Override
    public ObservableList<ActiveLoan> getAllActiveLoans(String login) {
        Connection connection = null;
        PreparedStatement statement = null;
        ObservableList<ActiveLoan> activeLoans = FXCollections.observableArrayList();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(getAllUsersLoans);
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ActiveLoan activeLoan = new ActiveLoan();
                BankDAO bankDAO = new MysqlBankDAO();
                activeLoan.setBankId(rs.getInt("bank_id"));
                UserDAO userDAO = new MysqlUserDAO();
                activeLoan.setDebtorLogin(rs.getString("debtor_login"));
                LoanOfferDAO loanOfferDAO = new MysqlLoanOfferDAO();
                activeLoan.setLoanOfferId(rs.getInt("loan_offer_id"));
                activeLoan.setCurrentDebtAmount(rs.getInt("current_debt"));
                activeLoan.setCurrentPercentagePerMonth(rs.getInt("current_percentage"));
                activeLoan.setCurrentTimeLeftInMonths(rs.getInt("current_time_left"));
                activeLoan.setId(rs.getInt("id"));
                activeLoans.add(activeLoan);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        } finally {
            closeConnection(connection);
            closeStatement(statement);
        }

        return activeLoans;
    }

}
