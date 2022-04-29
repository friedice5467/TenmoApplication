package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.controller.AccountController;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Transfer> findTransferByAccountID(int accountId) {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT * \n" +
                "FROM transfer\n" +
                "WHERE account_from = ?\n" +
                "OR account_to = ?";

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
        while(sqlRowSet.next()){
            Transfer transfer = mapRowToTransfer(sqlRowSet);
            transferList.add(transfer);
        }
        return transferList;
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
                "WHERE transfer_id = (SELECT transfer_id\n" +
                "FROM transfer\n" +
                "ORDER BY transfer_id DESC\n" +
                "LIMIT 1);" +
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
        jdbcTemplate.update(sql, transfer.getTransferStatus(), transfer.getAmount(), userIdSender,
                                transfer.getAmount(), userIdReceiver);
    }

    @Override
    public void updateRejectedTransfer(Transfer transfer) {
        String sql = "UPDATE transfer\n" +
                "SET transfer_status_id = ?\n" +
                "WHERE transfer_id = (SELECT transfer_id\n" +
                "FROM transfer\n" +
                "ORDER BY transfer_id DESC\n" +
                "LIMIT 1);";

        jdbcTemplate.update(sql, transfer.getTransferStatus());
    }

    /*
    private Product mapRowToProduct(SqlRowSet row)
    {
        Product product;

        int id = row.getInt("id");
        String name = row.getString("name");
        String category = row.getString("category");
        BigDecimal price = row.getBigDecimal("price");
        String slot = row.getString("slot");
        int quantity = row.getInt("quantity");

        product = new Product(id, name, category, price, slot, quantity);

        return product;
    }
     */
    public Transfer mapRowToTransfer(SqlRowSet row){
        Transfer transfer;

        String senderUsername = row.getString("username");
        int transferId = row.getInt("transfer_id");
        int transferTypeId = row.getInt("transfer_type_id");
        int transferStatusId = row.getInt("transfer_status_id");
        int transferFromId = row.getInt("account_from");
        int transferToId = row.getInt("account_to");
        BigDecimal amount = row.getBigDecimal("amount");

//        transfer.setSenderUsername(senderUsername);
//        transfer.setTransferId(transferId);
//        transfer.setTransferType(transferTypeId);
//        transfer.setTransferStatus(transferStatusId);
//        transfer.setAccountFrom(transferFromId);
//        transfer.setAccountTo(transferToId);
//        transfer.setAmount(amount);

        //String receiverUsername, String senderUsername, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal amount
        transfer = new Transfer()

        return transfer;
    }

}
