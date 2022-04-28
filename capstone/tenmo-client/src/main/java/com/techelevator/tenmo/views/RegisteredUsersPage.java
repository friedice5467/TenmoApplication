package com.techelevator.tenmo.views;

import com.techelevator.tenmo.services.AccountService;

import java.util.List;
import java.util.Scanner;

public class RegisteredUsersPage {

    public String display(Scanner scanner, AccountService accountService){
        //displays stuff here
        List<String> userList = accountService.getUserList();
        int counter = 1;
        for (String user : userList) {
            System.out.println(counter + ": " + user);
            counter++;
        }
        System.out.print("Choose a user from the list that you want to send money to:  ");

        return scanner.nextLine().trim();
    }
    //what is their username?: enter here
}
