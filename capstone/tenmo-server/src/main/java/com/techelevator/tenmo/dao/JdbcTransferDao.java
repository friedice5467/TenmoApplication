package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> findAll() {
        return null;
    }

    @Override
    public void createTransfer(int transferType, int accountFrom, int accountTo, BigDecimal amount) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (?, 1, ?, ?, ?);";
        jdbcTemplate.update(sql, transferType,  accountFrom, accountTo, amount);
    }

    @Override
    public void updateTransfer(int transferStatus) {
        String sql = "UPDATE transfer\n" +
                "SET transfer_status_id = ?";

    }

}
