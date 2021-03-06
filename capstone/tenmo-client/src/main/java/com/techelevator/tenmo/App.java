package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.tenmo.views.RegisteredUsersPage;
import com.techelevator.tenmo.views.TransferAmountPage;
import com.techelevator.tenmo.views.ViewTransferPage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();
    private final Transfer transfer = new Transfer();

    private Scanner scanner = new Scanner(System.in);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            accountService.setAuthToken(currentUser.getToken());
            transferService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                displayAllTransfer();
            } else if (menuSelection == 3) {
                displayRequestTransfer();
//                String message = "Enter 2 to approve request, 3 to reject: ";
//                int approved = 2;
//                int rejected = 3;
//                int prompt = consoleService.promptForInt(message);
//                transfer.setTransferStatus(prompt);
//                if(prompt == approved){
//                    transferService.updateTransferStatusApproved(transfer);
//                }
//                else if(prompt == rejected){
//                    transferService.updateTransferStatusRejected(transfer);
//                }
            } else if (menuSelection == 4) {
                displayUserList();
                sendBucks();
            } else if (menuSelection == 5) {
                displayUserListForPending();
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        BigDecimal balance = accountService.getBalance();

        System.out.println("The balance of this account is: $" + balance);
    }

    private List<Transfer> viewTransferHistory() {
        return transferService.getPastTransfer();


    }

    private void displayAllTransfer() {
        List<Transfer> tempList = viewTransferHistory();
        ViewTransferPage viewTransferPage = new ViewTransferPage();
        int transactionId = viewTransferPage.display(scanner);

        for (Transfer transfer : tempList) {
            if (transfer.getTransferId() == transactionId) {
                String status = "";
                String type = "";
                switch (transfer.getTransferStatus()) {
                    case 1:
                        status = "Pending";
                        break;
                    case 2:
                        status = "Approved";
                        break;
                    case 3:
                        status = "Rejected";
                        break;
                }
                switch (transfer.getTransferType()) {
                    case 1:
                        type = "Request";
                        break;
                    case 2:
                        type = "Send";
                        break;
                }

                System.out.println("Transfer ID: " + transfer.getTransferId() + "\nFrom: " + transfer.getSenderUsername() + "\nTo: " + transfer.getReceiverUsername() + "\nStatus: " + status + "\nType: " + type + "\nAmount: " + transfer.getAmount());
            }
        }
    }

    private void displayRequestTransfer() {
        List<Transfer> tempList = viewPendingRequests();
        ViewTransferPage viewTransferPage = new ViewTransferPage();
        int transactionId = viewTransferPage.display(scanner);

        for (Transfer transfer : tempList) {
            if (transfer.getTransferId() == transactionId) {
                String status = "";
                String type = "";
                switch (transfer.getTransferStatus()) {
                    case 1:
                        status = "Pending";
                        break;
                    case 2:
                        status = "Approved";
                        break;
                    case 3:
                        status = "Rejected";
                        break;
                }
                switch (transfer.getTransferType()) {
                    case 1:
                        type = "Request";
                        break;
                    case 2:
                        type = "Send";
                        break;
                }
                System.out.println("Transfer ID: " + transfer.getTransferId() + "\nFrom: " + transfer.getSenderUsername() + "\nTo: " + transfer.getReceiverUsername() + "\nStatus: " + status + "\nType: " + type + "\nAmount: " + transfer.getAmount());
                String message = "Enter 2 to approve request, 3 to reject: ";
                int approved = 2;
                int rejected = 3;
                int prompt = consoleService.promptForInt(message);
                transfer.setTransferStatus(prompt);
                if(prompt == approved){
                    transferService.updateTransferStatusApproved(transfer);
                }
                else if(prompt == rejected){
                    transferService.updateTransferStatusRejected(transfer);
                }
            }
        }
    }

    private List<Transfer> viewPendingRequests() {
        return transferService.getPendingTransfer();


    }

    private void displayUserList() {
        RegisteredUsersPage registeredUsersPage = new RegisteredUsersPage();
        String receiverUsername = registeredUsersPage.display(scanner, accountService);
        transfer.setReceiverUsername(receiverUsername);
        TransferAmountPage transferAmountPage = new TransferAmountPage();
        transfer.setAmount(transferAmountPage.display(scanner));
        transfer.setSenderUsername(currentUser.getUser().getUsername());
    }

    private void displayUserListForPending() {
        RegisteredUsersPage registeredUsersPage = new RegisteredUsersPage();
        String senderUsername = registeredUsersPage.display(scanner, accountService);
        transfer.setSenderUsername(senderUsername);
        TransferAmountPage transferAmountPage = new TransferAmountPage();
        BigDecimal amount = transferAmountPage.displayForRequest(scanner);
        transfer.setAmount(amount);
        transfer.setReceiverUsername(currentUser.getUser().getUsername());
    }

    private void sendBucks() {
        transferService.createSendTransfer(transfer);
    }

    private void requestBucks() {
        transferService.createRequestTransfer(transfer);

    }

}
