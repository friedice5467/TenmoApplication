package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int recieverUserId;
    private int senderUserId;
    private int transferId;
    private int transferType;
    private int transferStatus;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;

    public Transfer(){}

    public Transfer(int recieverUserId, int senderUserId, int transferId, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal amount) {
        this.recieverUserId = recieverUserId;
        this.senderUserId = senderUserId;
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public int getRecieverUserId() {
        return recieverUserId;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public int getTransferId() {
        return transferId;
    }

    public int getTransferType() {
        return transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}