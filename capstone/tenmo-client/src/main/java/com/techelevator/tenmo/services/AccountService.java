package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountService {

    private static final String BASE_URL = "http://localhost:8080/";
    private static final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public BigDecimal getBalance(){
        BigDecimal balance = BigDecimal.ZERO;
        try{
            String url = BASE_URL + "accounts";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);


            ResponseEntity<BigDecimal> response = restTemplate.exchange(url, HttpMethod.GET, entity, BigDecimal.class);
            balance = response.getBody();
        } catch (RestClientException e) {

        }
        return balance;
    }

    public List<String> getUserList() {
        List<String> userList = new ArrayList<>();

        try {
            String url = BASE_URL + "accounts/" + "users";
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<String[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, String[].class);
            userList = Arrays.asList(response.getBody());

        } catch (RestClientException e) {

        }
        return userList;
    }


}
