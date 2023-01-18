package com.cleverdev.clientService.service;

import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.service.converter.PatientConvert;
import com.cleverdev.clientService.dto.PatientDto;
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

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class ImportFromOldSystemService  {
    ParameterizedTypeReference<JSONArray> typeRef = new ParameterizedTypeReference<>() {
    };
    private final PatientRepository patientRepo;
    private final PatientConvert patientConvert;
    private final DataFromOldSystem dataFromOldSystem;
    @Value("${app.urlNotes}")
    private String urlForNotes;
    @Value("${app.importData.dateFrom}")
    private String dateFrom;
    @Value("${app.importData.dateTo}")
    private String dateTo;
    private List<Note> listNote = new ArrayList<>();
    private final NoteRepository noteRepository;

    public JSONArray getJsonObjFromOldSystem(String urlClient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONArray> response = restTemplate.exchange(urlClient, HttpMethod.POST, entity, typeRef);
        return response.getBody();
    }

    public String importFromOldSystem(JSONArray getObjFromOldSystem) {
        for (Object ob : getObjFromOldSystem) {
            PatientDto patientDto;
            LinkedHashMap<Object, Object> jsonPatientKey = (LinkedHashMap) ob;

            patientDto = PatientDto.builder()
                    .agency((String) jsonPatientKey.get("agency"))
                    .createdDateTime((LocalDateTime) jsonPatientKey.get("createDateTime"))
                    .firstName((String) jsonPatientKey.get("firstName"))
                    .guid((String) jsonPatientKey.get("guid"))
                    .lastName((String) jsonPatientKey.get("lastName"))
                    .status((PatientStatusEnum.valueOf(jsonPatientKey.get("status").toString())))
                    .build();
            if (patientRepo.findByOldClientGuid(patientDto.getGuid()) == null) {
                patientRepo.save(patientConvert.fromPatientDtoToPatient(patientDto));
            }
            if (PatientStatusEnum.ACTIVE == (PatientStatusEnum.valueOf(jsonPatientKey.get("status").toString()))) {

                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    JSONObject request = new JSONObject();
                    request.put("agency", jsonPatientKey.get("agency"));
                    request.put("dateFrom", dateFrom);
                    request.put("dateTo", dateTo);
                    request.put("clientGuid", jsonPatientKey.get("guid"));
                    RestTemplate restTemplate = new RestTemplate();
                    HttpEntity<JSONObject> entity = new HttpEntity<>(request, headers);
                    ResponseEntity<JSONArray> response = restTemplate.exchange(urlForNotes, HttpMethod.POST, entity, typeRef);
                    JSONArray responseDetailsNotes = response.getBody();

                    if (responseDetailsNotes.size() == 0) {
                        continue;
                    } else {
                        listNote.addAll(dataFromOldSystem.saveNoteInDB(responseDetailsNotes, jsonPatientKey));
                    }
                } catch (Exception e) {
//                    System.err.println(e);
                }
            }
        }
            noteRepository.saveAll(listNote);
// Прописывание в логи

        String piecesNote = "Добавлено заметок: " + listNote.size();
        return piecesNote;
    }
}
