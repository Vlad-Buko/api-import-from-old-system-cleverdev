package com.cleverdev.clientService.client.controller;

import com.cleverdev.clientService.client.consumer.JsonUtils;
import com.cleverdev.clientService.client.dto.ClientDtoOne;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@Log
@RequiredArgsConstructor
public class ClientControllerNewSystem {

    public static final String WEATHER_URL =
            "http://localhost:8081/clients";

    final RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/main")
    public JSONArray getJson() throws Exception{
        JSONParser jsonPars = new JSONParser();
        URL url = JsonUtils.createUrl(WEATHER_URL);
        String resultJsoon = JsonUtils.parseUrl(url);;
        JSONArray clientJsonArray = (JSONArray) jsonPars.parse(resultJsoon);

//        List<ClientDtoOne> listCLient = new ArrayList<>();
//        for (Object at : clientJsonList) {
//            JSONObject clientJsonObject = (JSONObject) at;
//
//        }
        List<ClientDtoOne> person = new ArrayList<>();
        // { }
//        for (int i = 0; i < clientJsonArray.size(); i++) {
//            ClientDtoOne client = new ClientDtoOne();
//            person.set(i, );
//            System.out.println("-" + clientJsonArray + "-");
//        }

        return clientJsonArray;
    }
}

