package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    public List<Transfer> findAll();

    public void createSendTransfer(Transfer transfer);

    public void updateTransfer(int userIdSender, int userIdReceiver, Transfer transfer);


}
