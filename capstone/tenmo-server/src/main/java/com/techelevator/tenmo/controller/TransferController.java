package com.techelevator.tenmo.controller;

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
    public void createSendTransfer(@RequestBody Transfer transfer) {
        int accountFromId = userDao.findAccountIdByUsername(transfer.getSenderUsername());
        int accountToId = userDao.findAccountIdByUsername(transfer.getReceiverUsername());
        int userIdSender = userDao.findIdByUsername(transfer.getSenderUsername());
        int userIdReceiver = userDao.findIdByUsername(transfer.getReceiverUsername());

        transfer.setAccountFrom(accountFromId);
        transfer.setAccountTo(accountToId);
        transferDao.createSendTransfer(transfer);

        if(transfer.getAmount().compareTo(BigDecimal.ZERO) > 0 && userDao.getBalance(transfer.getSenderUsername()).compareTo(transfer.getAmount()) >= 0 ){
            // update transfer status
            // update sender balance
            // update receiver balance
            transferDao.updateTransfer(userIdSender, userIdReceiver, transfer);
        }
    }





}
