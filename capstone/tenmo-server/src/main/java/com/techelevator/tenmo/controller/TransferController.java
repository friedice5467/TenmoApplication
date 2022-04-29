package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transfer")
@PreAuthorize("isAuthenticated()")
public class TransferController {
    TransferDao transferDao;
    UserDao userDao;
    private final int pending = 1;
    private final int approved = 2;
    private final int rejected = 3;
    private final int send = 2;
    private final int request = 1;

    @Autowired
    public TransferController(TransferDao transferDao, UserDao userDao){
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @GetMapping()
    public List<Transfer> getTransferList(Principal principal){
       int accountFromId = userDao.findAccountIdByUsername(principal.getName());

        List<Transfer> transferList = transferDao.findTransferByAccountID(accountFromId);
        List<Transfer> transferList1 = transferDao.findReceivedTransferByAccountId(principal,accountFromId);
        transferList.addAll(transferList1);

        return transferList;
    }

    @PostMapping("/send")
    public void createSendTransfer(Principal principal, @RequestBody Transfer transfer) {
        int accountFromId = userDao.findAccountIdByUsername(transfer.getSenderUsername());
        int accountToId = userDao.findAccountIdByUsername(transfer.getReceiverUsername());
        int userIdSender = userDao.findIdByUsername(transfer.getSenderUsername());
        int userIdReceiver = userDao.findIdByUsername(transfer.getReceiverUsername());
        String senderUsername = "";
        if(principal.getName().equalsIgnoreCase(transfer.getSenderUsername())){
            senderUsername = transfer.getSenderUsername();
        }
        transfer.setTransferType(send);
        transfer.setTransferStatus(approved);
        transfer.setAccountFrom(accountFromId);
        transfer.setAccountTo(accountToId);
        transfer.setSenderUsername(senderUsername);

        transferDao.createSendTransfer(transfer);

        if(transfer.getAmount().compareTo(BigDecimal.ZERO) > 0 && userDao.getBalance(transfer.getSenderUsername()).compareTo(transfer.getAmount()) >= 0 ){
            transferDao.updateTransfer(userIdSender, userIdReceiver, transfer);
        }
        else {
            transfer.setTransferStatus(rejected);
            transferDao.updateRejectedTransfer(transfer);
        }
    }





}
