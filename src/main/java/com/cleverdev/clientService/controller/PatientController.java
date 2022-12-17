package com.cleverdev.clientService.controller;

import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vladislav Domaniewski
 */

@Log
@RestController
@RequestMapping("/api")
@NoArgsConstructor
public class PatientController {

    /**
    Здесь мы записываем URL-адрес для запроса нашего старого проекта, для получения JSON
     */

    private static final String WEATHER_URL = "http://localhost:8081/api/old/clients";



}

