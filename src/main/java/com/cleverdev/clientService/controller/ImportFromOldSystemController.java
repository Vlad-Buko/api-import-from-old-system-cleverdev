package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.service.ImportFromOldSystemService;
import com.cleverdev.clientService.service.getDataFromOldSystemMethods.DataFromOldSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

/**
 * Created by Vladislav Domaniewski
 */

@RestController
@RequestMapping("/api")
@Log4j2
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
        try {
            JSONArray arrayPatientsFromOldSystem = importService.getJsonObjFromOldSystem(urlForClients);
            log.info(importService.importFromOldSystem(arrayPatientsFromOldSystem));
            dataFromOldSystem.setCountNote(0);
        } catch (ResourceAccessException e) {     // логи сбоев, одни из возможных
            log.error("Ошибка при подключении к старой системе :(");
            log.error(e.getMessage());
        } catch (HttpServerErrorException e) {
            log.error("Ошибка при парсинге, какой-то ключ введен не верно :(");
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
