package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.service.ImportFromOldSystemService;
import com.cleverdev.clientService.service.getDataFromOldSystemMethods.DataFromOldSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
    private final DataFromOldSystem dataFromOldSystem;


    // Запуск приложения каждые 2 часа в 15 минут первого часа
    @Scheduled(cron = "* 15 0/2 * * *")
    @PostMapping("/import-from-old-system")
    public ResponseEntity<Void> importFromOldSystem()
    {
        JSONArray arrayPatientsFromOldSystem = importService.getJsonObjFromOldSystem(urlForClients);
        String info = importService.importFromOldSystem(arrayPatientsFromOldSystem);
        log.info(info);
        dataFromOldSystem.setCountNote(0);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/test")
//    public ResponseEntity<Void> saveCollection() {
//        List<Note> setNotes = noteRepository.findAll();
//        noteRepository.deleteAll();
//        System.out.println(noteRepository.findAll());
//        noteRepository.saveAll(setNotes);
//        return ResponseEntity.ok().build();
//    }
}
