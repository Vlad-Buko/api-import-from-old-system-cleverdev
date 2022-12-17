package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.dto.ClientNoteDto;
import com.cleverdev.clientService.dto.PatientDto;
import com.cleverdev.clientService.service.GetJsonFromOldSystem;
import com.cleverdev.clientService.service.NoteService;
import com.cleverdev.clientService.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@RequestMapping
@Log
@RequiredArgsConstructor
public class ImportFromOldSystemController {
    private ParameterizedTypeReference<JSONArray> typeRef = new ParameterizedTypeReference<JSONArray>() {
    };

    @Value("${app.urlClients}")
    private String urlForClients;
    @Value("${app.urlNotes}")
    private String urlForNotes;
    private final PatientService patientService;
    private final NoteService noteService;

    @PostMapping("/note")
    public void getNotesFromOldSystem()
    {
        noteService.populatingDataBaseNotes(urlForNotes);
    }

    @PostMapping("/patient")
    public List<PatientDto> getPatientsFromOldSystem() {
        JSONArray arrayPatientsFromOldSystem = patientService.getJsonObjFromOldSystem(urlForClients);
        patientService.saveClientsFromOldSystemInNewSystem(arrayPatientsFromOldSystem);
        log.info("Patients added!");
        return null;
    }
}
