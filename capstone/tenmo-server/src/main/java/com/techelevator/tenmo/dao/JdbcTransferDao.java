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
    public void createSendTransfer(Transfer transfer) {
        int pending = 1;
        int approved = 2;
        int rejected = 3;
        int send = 2;
        int request = 1;
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,send, pending, transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
    }

    @Override
    public void updateTransfer(int userIdSender, int userIdReceiver, Transfer transfer) {
        String sql = "UPDATE transfer\n" +
                "SET transfer_status_id = ?\n" +
                "WHERE transfer_id = ?;" +
                "" +
                "UPDATE account\n" +
                "SET balance = balance - ?\n" +
                "WHERE user_id = ?;\n" +
                "\n" +
                "UPDATE account\n" +
                "SET balance = balance + ?\n" +
                "WHERE user_id = ?;";

        // account from = current user
        // account to = target user
        // currentUserBalance = balance - amount
        // targetUserBalance = balance + amount
        jdbcTemplate.update(sql, transfer.getTransferStatus(), transfer.getTransferId(), transfer.getAmount(), userIdSender,
                                transfer.getAmount(), userIdReceiver);
    }

    @Override
    public void updateRejectedTransfer(Transfer transfer) {
        String sql = "UPDATE transfer\n" +
                "SET transfer_status_id = ?\n" +
                "WHERE transfer_id = ?\n" +
                ";";

        jdbcTemplate.update(sql, transfer.getTransferStatus(), transfer.getTransferId());
    }

}
