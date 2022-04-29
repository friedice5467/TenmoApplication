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
        String sql = "SELECT tu.username\n" +
                "\t, t.transfer_id\n" +
                "\t, t.transfer_type_id\n" +
                "\t, t.transfer_status_id\n" +
                "\t, t.account_from\n" +
                "\t, t.account_to\n" +
                "\t, t.amount\n" +
                "FROM transfer AS t\n" +
                "INNER JOIN account AS a\n" +
                "ON t.account_from = a.account_id\n" +
                "INNER JOIN tenmo_user AS tu\n" +
                "ON tu.user_id = a.user_id\n" +
                "WHERE t.account_from = ?\n" +
                ";";

        String sql1 = "SELECT tu.username\t\n" +
                "FROM transfer AS t\n" +
                "INNER JOIN account AS a\n" +
                "ON t.account_to = a.account_id\n" +
                "INNER JOIN tenmo_user AS tu\n" +
                "ON tu.user_id = a.user_id\n" +
                "WHERE t.account_from = ?\n";

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        SqlRowSet sqlRowSet1 = jdbcTemplate.queryForRowSet(sql1, accountId);
        String receiveUsername = "";

        while(sqlRowSet.next()){
            if(sqlRowSet1.next()) {
                receiveUsername = sqlRowSet1.getString("username");
            }
            Transfer transfer = mapRowToTransfer(sqlRowSet, receiveUsername);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public List<Transfer> findReceivedTransferByAccountId(Principal principal, int accountId){
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT tu.username\n" +
                "\t, t.transfer_id\n" +
                "\t, t.transfer_type_id\n" +
                "\t, t.transfer_status_id\n" +
                "\t, t.account_from\n" +
                "\t, t.account_to\n" +
                "\t, t.amount\n" +
                "FROM transfer AS t\n" +
                "INNER JOIN account AS a\n" +
                "ON t.account_from = a.account_id\n" +
                "INNER JOIN tenmo_user AS tu\n" +
                "ON tu.user_id = a.user_id\n" +
                "WHERE t.account_to = ?\n" +
                ";";

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, accountId);
        while(sqlRowSet.next()){
            Transfer transfer = mapRowToTransfer(sqlRowSet, principal.getName());
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public Transfer findTransferByTransferId(Principal principal, int transferId){
        Transfer transfer = new Transfer();

        String sql = "SELECT * \n" +
                "FROM transfer\n" +
                "WHERE transfer_id = ?;\n";

        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, transferId);
        if(sqlRowSet.next()){
            transfer = mapRowToTransfer(sqlRowSet, principal.getName());
        }
        return transfer;
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
    public Transfer mapRowToTransfer(SqlRowSet row, String receiverUsername){
        Transfer transfer;

        String senderUsername = row.getString("username");
        int transferId = row.getInt("transfer_id");
        int transferTypeId = row.getInt("transfer_type_id");
        int transferStatusId = row.getInt("transfer_status_id");
        int transferFromId = row.getInt("account_from");
        int transferToId = row.getInt("account_to");
        BigDecimal amount = row.getBigDecimal("amount");

        //String receiverUsername, String senderUsername, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal amount
        transfer = new Transfer(receiverUsername, senderUsername, transferId, transferTypeId, transferStatusId, transferFromId, transferToId, amount);

        return transfer;
    }


}
