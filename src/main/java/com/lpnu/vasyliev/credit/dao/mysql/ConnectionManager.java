package com.lpnu.vasyliev.credit.dao.mysql;

import com.lpnu.vasyliev.credit.dao.mysql.constants.Config;
import com.lpnu.vasyliev.credit.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    public static Connection getConnection()  {
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Config.dbUrl, Config.dbUser, Config.dbPassword);
        } catch (Exception e) {
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
            System.exit(0);
        }
        return connection;
    }

    public static void closeConnection(Connection connection){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                EmailSender.send(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement statement){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                EmailSender.send(e.getMessage());
            }
        }
    }
}