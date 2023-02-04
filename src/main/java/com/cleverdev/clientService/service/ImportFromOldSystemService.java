package com.cleverdev.clientService.service;

import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import com.cleverdev.clientService.repository.PatientRepository;
import com.cleverdev.clientService.service.getDataFromOldSystemMethods.DataFromOldSystem;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by Vladislav Domaniewski
 */


@Service
@RequiredArgsConstructor
public class ImportFromOldSystemService  {
    long timeBegin = System.currentTimeMillis();
    ParameterizedTypeReference<JSONArray> typeRef = new ParameterizedTypeReference<>() {
    };
    private final PatientRepository patientRepo;
    private final DataFromOldSystem dataFromOldSystem;
    @Value("${app.urlNotes}")
    private String urlForNotes;
    @Value("${app.importData.dateFrom}")
    private String dateFrom;
    @Value("${app.importData.dateTo}")
    private String dateTo;

    public JSONArray getJsonObjFromOldSystem(String urlClient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONArray> response = restTemplate.exchange(urlClient, HttpMethod.POST, entity, typeRef);
        return response.getBody();
    }

    public String importFromOldSystem(JSONArray getObjFromOldSystem) {
        /*
            Импорт заметок из старой системы происходит если у пациента Status ACTIVE
         */
        List<Patient> patientList = patientRepo.findAll();
        for (Patient patient : patientList) {
            if (PatientStatusEnum.ACTIVE == (patient.getStatusId())) {
                String guidKit = patient.getOldClientGuid();
                String[] listGuid = guidKit.split(",");
                for (String guid : listGuid) {
                LinkedHashMap jsonPatientKey;
                for (Object ob : getObjFromOldSystem) {
                    jsonPatientKey = (LinkedHashMap) ob;
                    if (guid.equals(jsonPatientKey.get("guid").toString())) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        JSONObject request = new JSONObject();
                        request.put("agency", jsonPatientKey.get("agency"));
                        request.put("dateFrom", dateFrom);     // * Параметры даты задаются из application.properties
                        request.put("dateTo", dateTo);
                        request.put("clientGuid", jsonPatientKey.get("guid"));
                        RestTemplate restTemplate = new RestTemplate();
                        HttpEntity<JSONObject> entity = new HttpEntity<>(request, headers);
                        ResponseEntity<JSONArray> response = restTemplate.exchange(urlForNotes, HttpMethod.POST, entity, typeRef);
                        JSONArray responseDetailsNotes = response.getBody();

                        if (responseDetailsNotes.size() == 0) {
                            continue;
                        } else {
                            dataFromOldSystem.saveNoteInDB(responseDetailsNotes, patient);
                        }
                        break;
                    }
                }
            }
            }
        }
        long timeEnd = System.currentTimeMillis();
        long actualTime = timeEnd - timeBegin;
        return "Добавлено заметок: " + dataFromOldSystem.getCountNote() +
                "\n прошло времени: " + actualTime;
    }
}
