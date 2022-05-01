package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public interface TransferDao {

    public List<Transfer> findTransferByAccountID(int accountId);

    public List<Transfer> findReceivedTransferByAccountId(Principal principal, int accountId);

    public void createSendTransfer(Transfer transfer);

    public void createRequestTransfer(Transfer transfer);

    public void updateTransfer(int userIdSender, int userIdReceiver, Transfer transfer);

    public void updateRejectedTransfer(Transfer transfer);

    public List<Transfer> getRequestTransferList(Principal principal, int accountId);

    public String findUsernameByAccountID(int accountId);

}
