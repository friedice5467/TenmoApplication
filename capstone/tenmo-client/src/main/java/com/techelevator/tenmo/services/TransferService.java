package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {

    private static final String BASE_URL = "http://localhost:8080/";
    private static final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public boolean createSendTransfer(Transfer transfer) {
        try {
            String url = BASE_URL + "transfer/send";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

            restTemplate.postForObject(url, entity, Void.class);
            return true;
        } catch (RestClientException e) {
            System.out.println("this failed");
            return false;
        }

    }

    public boolean createRequestTransfer(Transfer transfer) {
        try {
            String url = BASE_URL + "transfer/request";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);

            restTemplate.postForObject(url, entity, Void.class);
            return true;
        } catch (RestClientException e) {
            System.out.println("this failed");
            return false;
        }

    }

    public List<Transfer> getPastTransfer(){
        List<Transfer> transferList =new ArrayList<>();

        try{
            String url = BASE_URL + "transfer";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Transfer[]>response =restTemplate.exchange(url, HttpMethod.GET, entity, Transfer[].class);
            transferList = Arrays.asList(response.getBody());
        } catch (RestClientException e) {

        }
        for(Transfer transfer : transferList){
            System.out.println(transfer.toString());
        }
        return transferList;
    }

    public List<Transfer> getPendingTransfer(){
        List<Transfer> transferList =new ArrayList<>();

        try{
            String url = BASE_URL + "transfer/pending";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Transfer[]>response =restTemplate.exchange(url, HttpMethod.GET, entity, Transfer[].class);
            transferList = Arrays.asList(response.getBody());
        } catch (RestClientException e) {

        }
        for(Transfer transfer : transferList){
            System.out.println(transfer.toString());
        }
        return transferList;
    }

//    public void getCurrentTransfer(int transferId){
//        Transfer transfer = new Transfer();
//        try{
//            String url = BASE_URL + "transfer/" + transferId;
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setBearerAuth(authToken);
//            HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<Transfer> response = restTemplate.exchange(url, HttpMethod.GET, entity, Transfer.class);
//            System.out.println(response.getBody().toString());
//        } catch (RestClientException e) {
//            e.printStackTrace();
//        }
//
//    }
}
