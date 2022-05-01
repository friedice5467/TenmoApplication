package com.techelevator.tenmo.views;

import com.techelevator.tenmo.model.Transfer;

import java.util.Scanner;

public class ViewTransferPage {
    public int display(Scanner scanner){

        System.out.println();
        System.out.print("Enter the transfer ID you want to see: ");
        String transactionIdString = scanner.nextLine();
        System.out.println("----------------------------------------------------------");
        System.out.println("Transfer details");
        System.out.println("----------------------------------------------------------");
        int transactionId = 0;
        try{
            transactionId = Integer.parseInt(transactionIdString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format");
        }

        return transactionId;
    }

}
