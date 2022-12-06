package com.lpnu.vasyliev.credit.dao;

import com.lpnu.vasyliev.credit.dao.mysql.MysqlUserDAO;
import com.lpnu.vasyliev.credit.entity.ActiveLoan;
import com.lpnu.vasyliev.credit.entity.User;
import javafx.collections.ObservableList;

public interface UserDAO {
    UserDAO actualInstance = new MysqlUserDAO();
    static UserDAO getInstance(){return actualInstance;}
    void addUser(User user);
    User getUserByLogin(String login);
    void setStatus(String status, String userLogin);
    boolean acceptableStatus(String userLogin);
    void handleOverdue(ActiveLoan activeLoan);
    boolean passwordMatchesLogin(String login, String password);
    boolean loginExists(String login);
    ObservableList<ActiveLoan> getAllActiveLoans(String login);
}
