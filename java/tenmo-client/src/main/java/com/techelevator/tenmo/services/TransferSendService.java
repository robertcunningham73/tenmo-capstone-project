package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.NamedUserId;
import com.techelevator.tenmo.model.TransferSend;
import com.techelevator.view.ConsoleService;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;

import java.math.BigDecimal;



public class TransferSendService
{

    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService(System.in, System.out);

    public TransferSendService(String url) {
        BASE_URL = url;
    }

    public TransferSend addTransfer(int fromId, int toId, BigDecimal amountOfTransfer) {
        TransferSend transferSend = new TransferSend(fromId,toId,amountOfTransfer);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TransferSend> entity = new HttpEntity<>(transferSend,headers);

        try {
            transferSend = restTemplate.postForObject(BASE_URL + "transfer",
                    entity, TransferSend.class);
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getRawStatusCode() + " : " + ex.getStatusText());
        } catch (ResourceAccessException ex) {
            System.out.println(ex.getMessage());
        }
        return transferSend;
    }
}
