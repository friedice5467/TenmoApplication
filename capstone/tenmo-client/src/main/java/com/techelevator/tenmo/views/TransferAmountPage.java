package com.techelevator.tenmo.views;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferAmountPage {

    public BigDecimal display(Scanner scanner){
        System.out.print("How many TE bucks do you wanna send: ");

        return BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
    }

}
