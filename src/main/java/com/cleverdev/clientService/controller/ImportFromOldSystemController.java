package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.dto.PatientDto;
import com.cleverdev.clientService.service.ImportFromOldSystemService;
import com.cleverdev.clientService.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.CronTask;
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
    private final ImportFromOldSystemService importService;
    private final NoteService noteService;
    private CronTask task;

//    @Scheduled(cron = "*/2 * * * * *")
    @PostMapping("/import-from-old-system")
    public ResponseEntity<Void> importFromOldSystem()
    {
        JSONArray arrayPatientsFromOldSystem = importService.getJsonObjFromOldSystem(urlForClients);
        importService.importFromOldSystem(arrayPatientsFromOldSystem);
        return ResponseEntity.ok().build();
    }
}
