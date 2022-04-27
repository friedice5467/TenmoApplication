package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    UserDao userDao;

    @Autowired
    public AccountController(UserDao userDao){
        this.userDao = userDao;
    }

    @GetMapping()
    public BigDecimal getBalance(Principal principal){
        String userName = principal.getName();

        return userDao.getBalance(userName);
    }

    @GetMapping("users")
    public List<String> getUserList(Principal principal) {
        return userDao.listAll(principal);
    }
}
