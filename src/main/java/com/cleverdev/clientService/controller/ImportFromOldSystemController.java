package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.dto.PatientDto;
import com.cleverdev.clientService.service.ImportFromOldSystemService;
import com.cleverdev.clientService.service.NoteService;
import com.cleverdev.clientService.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@RequestMapping("/api")
@Log
@RequiredArgsConstructor
public class ImportFromOldSystemController {
    @Value("${app.urlClients}")
    private String urlForClients;
    @Value("${app.urlNotes}")
    private String urlForNotes;
    private final ImportFromOldSystemService importfromOld;
    private final PatientService patientService;
    private final NoteService noteService;

    @PostMapping("/import-from-old-system")
    public ResponseEntity.BodyBuilder importFromOldSystem()
    {
        JSONArray arrayPatientsFromOldSystem = patientService.getJsonObjFromOldSystem(urlForClients);
        List<PatientDto> listPatients = patientService.getObjectJsonForCreatePatientDtoAndSaveInDB(arrayPatientsFromOldSystem);
        return ResponseEntity.ok();
    }
}
