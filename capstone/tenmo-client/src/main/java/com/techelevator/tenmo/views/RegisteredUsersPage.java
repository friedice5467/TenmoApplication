package com.techelevator.tenmo.views;

import com.techelevator.tenmo.services.AccountService;

import java.util.List;
import java.util.Scanner;

public class RegisteredUsersPage {

    public String display(Scanner scanner, AccountService accountService){
        //displays stuff here
        List<String> userList = accountService.getUserList();
        int counter = 1;
        String username = "";

        System.out.println();
        System.out.println("------------------------");
        System.out.println("User ID \tUsername ");
        System.out.println("------------------------");
        for (String user : userList) {
            System.out.println(counter + ": \t\t\t" + user);
            counter++;
        }
        System.out.println("------------------------");
        System.out.println();
        System.out.print("Choose a username from the list to send money to:  ");
        username = scanner.nextLine().trim();
        System.out.println("----------------------------------------------------------");
        return username;
    }
    //what is their username?: enter here
}
