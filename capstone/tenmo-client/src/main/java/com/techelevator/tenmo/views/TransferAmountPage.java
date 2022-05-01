package com.techelevator.tenmo.views;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferAmountPage {

    public BigDecimal display(Scanner scanner){
        try{
            System.out.print("How many TE bucks do you want to send: ");
            BigDecimal amount = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
            System.out.println("----------------------------------------------------------");

            return amount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
        }
        return BigDecimal.ZERO;

    }

    public BigDecimal displayForRequest(Scanner scanner){
        try{
            System.out.print("Please enter a TE bucks request amount: ");
            BigDecimal amount = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
            System.out.println("----------------------------------------------------------");

            return amount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number");
        }
        return BigDecimal.ZERO;
    }
}
