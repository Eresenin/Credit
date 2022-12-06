package com.lpnu.vasyliev.credit.dao.mysql;

import com.lpnu.vasyliev.credit.dao.BankDAO;
import com.lpnu.vasyliev.credit.entity.Bank;
import com.lpnu.vasyliev.credit.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static com.lpnu.vasyliev.credit.dao.mysql.constants.SQLQueries.bankInfoByIdQuery;

public class MysqlBankDAO implements BankDAO {
    private static final Logger logger = LoggerFactory.getLogger(BankDAO.class);
    @Override
    public Bank getBankById(int bankId) {
        logger.info("entered", this);
        Connection connection=null;
        PreparedStatement statement=null;
        Bank bank = new Bank();
        try{
            connection= ConnectionManager.getConnection();
            statement = connection.prepareStatement(bankInfoByIdQuery);
            statement.setInt(1, bankId);

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                bank.setName(rs.getString("name"));
                bank.setId(bankId);
            }
        }catch (SQLException e){
            logger.error(e.getMessage());
            EmailSender.send(e.getMessage());
        }finally {
            ConnectionManager.closeConnection(connection);
            ConnectionManager.closeStatement(statement);
        }
        return bank;
    }
}
