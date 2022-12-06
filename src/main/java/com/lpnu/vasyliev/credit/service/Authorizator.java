package com.lpnu.vasyliev.credit.service;

import com.lpnu.vasyliev.credit.dao.UserDAO;
import com.lpnu.vasyliev.credit.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lpnu.vasyliev.credit.service.Validator.*;

public class Authorizator {
    private static Logger logger = LoggerFactory.getLogger(Authorizator.class);
    private static String currentUserLogin;
    public static String getCurrentUserLogin(){return currentUserLogin;}
//    public static void setCurrentUserLogin(String login){currentUserLogin=login;}


    public static void SignIn(String login, String password) throws IllegalArgumentException{
        if     (validateLoginOrPassword(login) &&
                validateLoginOrPassword(password) &&
                UserDAO.actualInstance.loginExists(login) &&
                UserDAO.actualInstance.acceptableStatus(login) &&
                UserDAO.actualInstance.passwordMatchesLogin(login, password)) {
            currentUserLogin=login;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    public static void SignUp(String login, String password, String name, String money)
            throws IllegalArgumentException{
        if     (validateLoginOrPassword(login) &&
                validateLoginOrPassword(password) &&
                validateName(name) &&
                validateInt(money) &&
                !UserDAO.actualInstance.loginExists(login)){
            UserDAO.actualInstance.addUser(new User(login, password, name, "default", Integer.valueOf(money)));
            currentUserLogin=login;
        }else {
            throw new IllegalArgumentException();
        }
    }
}
