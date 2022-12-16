package com.cleverdev.clientService.patient.controller;

import com.cleverdev.clientService.patient.service.PatientService;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Vladislav Domaniewski
 */

@Log
@RestController
@RequestMapping("/api")
@NoArgsConstructor
public class PatientControllerNewSystem {

    /**
    Здесь мы записываем URL-адрес для запроса нашего старого проекта, для получения JSON
     */

    private static final String WEATHER_URL = "http://localhost:8081/api/old/clients";
    @Autowired
    private PatientService patientService;


    @GetMapping("/main")
    public String getJsonFromOldSystem() throws Exception{
        patientService.populatingDatabase(WEATHER_URL);
        log.info("VVV --- Get JSON from old system for clients data! --- VVV");
        return "JSON добавлен в БД!";
    }
}

