package com.cleverdev.clientService.service;

import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.enums.PatientStatusEnum;
import com.cleverdev.clientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class PatientService implements GetJsonFromOldSystem {
    private final PatientRepository patientRepository;
    private Patient patient;
    @Override
    public JSONArray getJsonObjFromOldSystem(String urlClient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONArray> response = restTemplate.exchange(urlClient, HttpMethod.POST, entity, typeRef);
        JSONArray responseDetails = response.getBody();
        return responseDetails;
    }

    public void saveClientsFromOldSystemInNewSystem(JSONArray arrayJson) {
        for (Object obj : arrayJson) {
            LinkedHashMap<Object, Object> jsonPatientKey = (LinkedHashMap) obj;
            patient = Patient.builder()
                    .firstName((String) jsonPatientKey.get("firstName"))
                    .lastName((String) jsonPatientKey.get("lastName"))
                    .oldClientGuid((String) jsonPatientKey.get("guid"))
                    .statusId((PatientStatusEnum.valueOf(jsonPatientKey.get("status").toString())))
                    .build();
            patientRepository.save(patient);
        }
    }

    @Override
    public void populatingDataBaseNotes(String urlNote) {

    }
}
