package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    public List<Transfer> findAll();

    public void createTransfer(int transferType, int accountFrom, int accountTo, BigDecimal amount);

    public void updateTransfer();


}
