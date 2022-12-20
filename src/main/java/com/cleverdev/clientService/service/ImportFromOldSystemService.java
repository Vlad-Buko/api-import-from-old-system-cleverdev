package com.cleverdev.clientService.service;

import com.cleverdev.clientService.service.converter.NoteConverter;
import com.cleverdev.clientService.service.converter.PatientConvert;
import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.dto.PatientDto;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import com.cleverdev.clientService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class ImportFromOldSystemService implements GetJsonFromOldSystem {
    private final NoteRepository noteRepo;
    private final PatientRepository patientRepo;
    private final UserRepository userRepo;
    private final PatientConvert patientConvert;
    private final NoteConverter noteConverter;
    private final UserService userService;
    @Value("${app.urlNotes}")
    private String urlForNotes;

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

    public List<PatientDto> getObjectJsonForCreatePatientDtoAndSaveInDB(JSONArray getObjFromOldSystem) {
        List<PatientDto> fullListClientFromOldSystem = new ArrayList<>();
        for (Object ob : getObjFromOldSystem) {
            PatientDto patientDto = new PatientDto();
            LinkedHashMap<Object, Object> jsonPatientKey = (LinkedHashMap) ob;
            patientDto = PatientDto.builder()
                    .agency((String) jsonPatientKey.get("agency"))
                    .createdDateTime((LocalDateTime) jsonPatientKey.get("createDateTime"))
                    .firstName((String) jsonPatientKey.get("firstName"))
                    .guid((String) jsonPatientKey.get("guid"))
                    .lastName((String) jsonPatientKey.get("lastName"))
                    .status((PatientStatusEnum.valueOf(jsonPatientKey.get("status").toString())))
                    .build();

            patientRepo.save(patientConvert.fromPatientDtoToPatient(patientDto));

            if (PatientStatusEnum.ACTIVE == (PatientStatusEnum.valueOf(jsonPatientKey.get("status").toString()))) {

                try{
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    JSONObject request = new JSONObject();
                    request.put("agency", jsonPatientKey.get("agency"));
                    request.put("dateFrom", "2020-12-15");          //потом поменяем
                    request.put("dateTo", "2022-12-15");            // потом поменяем
                    request.put("clientGuid", jsonPatientKey.get("guid"));
                    RestTemplate restTemplate = new RestTemplate();
                    HttpEntity<JSONObject> entity = new HttpEntity<>(request, headers);
                    ResponseEntity<JSONArray> response = restTemplate.exchange(urlForNotes, HttpMethod.POST, entity, typeRef);

                    JSONArray  responseDetailsNotes = response.getBody();
                    if (responseDetailsNotes.size() == 0) {
                        continue;
                    } else {
                        for (Object it : responseDetailsNotes) {
                            Patient findIdPatientForWriteForNoteEntity = patientRepo.findByOldClientGuid((String)jsonPatientKey.get("guid"));
                            NoteDto noteDto = new NoteDto();

                            LinkedHashMap<Object, Object> jsonNoteKey = (LinkedHashMap) it;

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            User findIdUserForWriteUserEntity = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));  // 0
                            if (findIdUserForWriteUserEntity == null) {
                                userService.saveUserFromOldVersionInNew((String) jsonNoteKey.get("loggedUser"));
                                User findIfNotFound = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));
                                findIdUserForWriteUserEntity = findIfNotFound;
                            }
                            noteDto = NoteDto.builder()
                                    .createdDateTime(LocalDateTime.parse((String)jsonNoteKey.get("createdDateTime"), formatter))
                                    .lastModifiedDateTime(LocalDateTime.parse((String)jsonNoteKey.get("modifiedDateTime"), formatter))
                                    .createdByUserId(findIdUserForWriteUserEntity)
                                    .lastModifiedByUserId(12l)
                                    .comment((String) jsonNoteKey.get("comments"))
                                    .patient(findIdPatientForWriteForNoteEntity)
                                    .build();

                            noteRepo.save(noteConverter.fromNoteDtoToNote(noteDto));
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            } else {
                continue;
            }
        }
        return fullListClientFromOldSystem;
    }
}
