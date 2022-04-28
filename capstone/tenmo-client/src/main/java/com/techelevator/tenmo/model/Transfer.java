package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int receiverUserId;
    private int senderUserId;
    private int transferId;
    private int transferType;
    private int transferStatus;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;

    public Transfer(){}

    public Transfer(int receiverUserId, int senderUserId, int transferId, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal amount) {
        this.receiverUserId = receiverUserId;
        this.senderUserId = senderUserId;
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public int getReceiverUserId() {
        return receiverUserId;
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

    public void setReceiverUserId(int receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public void setSenderUserId(int senderUserId) {
        this.senderUserId = senderUserId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
