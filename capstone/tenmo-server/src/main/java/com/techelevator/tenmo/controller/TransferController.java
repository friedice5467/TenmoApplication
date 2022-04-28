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

@RestController
@RequestMapping("/transfer")
@PreAuthorize("isAuthenticated()")
public class TransferController {
    TransferDao transferDao;
    UserDao userDao;

    @Autowired
    public TransferController(TransferDao transferDao){
        this.transferDao = transferDao;
    }

    @PostMapping("/send")
    public void createSendTransfer(Principal principal, @RequestBody Transfer transfer) {
        int accountFromId = userDao.findAccountIdByUsername(transfer.getSenderUsername());
        int accountToId = userDao.findAccountIdByUsername(transfer.getReceiverUsername());
        int userIdSender = userDao.findIdByUsername(transfer.getSenderUsername());
        int userIdReceiver = userDao.findIdByUsername(transfer.getReceiverUsername());
        String senderUsername = "";
        if(principal.getName().equalsIgnoreCase(transfer.getReceiverUsername())){
            senderUsername = transfer.getReceiverUsername();
        }


        //String receiverUsername, String senderUsername, int transferId, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal amount
        Transfer newTransfer = new Transfer(transfer.getReceiverUsername(), senderUsername, 2
                                                , 1, accountFromId, accountToId, transfer.getAmount());

        transferDao.createSendTransfer(newTransfer);

        if(transfer.getAmount().compareTo(BigDecimal.ZERO) > 0 && userDao.getBalance(transfer.getSenderUsername()).compareTo(transfer.getAmount()) >= 0 ){
            newTransfer.setTransferStatus(2);
            transferDao.updateTransfer(userIdSender, userIdReceiver, newTransfer);
        }
        else {
            newTransfer.setTransferStatus(3);
            transferDao.updateRejectedTransfer(newTransfer);
        }
    }





}
