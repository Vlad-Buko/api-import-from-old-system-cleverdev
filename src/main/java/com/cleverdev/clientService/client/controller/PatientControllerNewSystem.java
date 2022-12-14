package com.cleverdev.clientService.client.controller;

import com.cleverdev.clientService.client.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vladislav Domaniewski
 */

@Log
@RestController
@RequiredArgsConstructor
public class PatientControllerNewSystem {

    /**
    Здесь мы записываем URL-адрес для запроса нашего старого проекта, для получения JSON
     */

    private final static String WEATHER_URL = "http://localhost:8081/api/old/clients";
    private final PatientService patientService;

    @GetMapping(value = "/main")
    public String getJsonFromOldSystem() throws Exception{
        patientService.populatingDatabase(WEATHER_URL);
        log.info("VVV --- Get JSON from old system for clients data! --- VVV");
        return "JSON добавлен в БД!";
    }
}

