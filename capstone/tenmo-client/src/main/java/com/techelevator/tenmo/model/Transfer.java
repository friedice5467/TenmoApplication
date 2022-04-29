package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private String senderUsername;
    private String receiverUsername;
    private int transferType;
    private int transferStatus;
    private int accountFrom;
    private int accountTo;
    private BigDecimal amount;

    public Transfer() {
    }

    public Transfer(String receiverUsername, String senderUsername, int transferType, int transferStatus, int accountFrom, int accountTo, BigDecimal amount) {
        this.receiverUsername = receiverUsername;
        this.senderUsername = senderUsername;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
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

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
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

    @Override
    public String toString() {
        return "Transfer ID: " + getTransferId() + "\tFrom: " + getSenderUsername() + "\tTo: " + getReceiverUsername() + "\tAmount: " + getAmount();
    }
}
