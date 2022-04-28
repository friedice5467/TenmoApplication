package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/transfer")
@PreAuthorize("isAuthenticated()")
public class TransferController {
    TransferDao transferDao;

    @Autowired
    public TransferController(TransferDao transferDao){
        this.transferDao = transferDao;
    }

    @PostMapping("/send")
    public void createSendTransfer(@RequestBody Transfer transfer) {
        transferDao.createSendTransfer(transfer);
    }


}
