package com.lpnu.vasyliev.credit.dao;

import com.lpnu.vasyliev.credit.dao.mysql.MysqlBankDAO;
import com.lpnu.vasyliev.credit.entity.Bank;

public interface BankDAO {
    BankDAO actualInstance = new MysqlBankDAO();

    Bank getBankById(int bankId);
}
