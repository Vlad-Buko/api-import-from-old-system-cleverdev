package com.cleverdev.clientService.patient.service;

import com.cleverdev.clientService.patient.consumer.JsonUtils;
import com.cleverdev.clientService.patient.entity.Patient;
import com.cleverdev.clientService.patient.enums.PatientStatusEnum;
import com.cleverdev.clientService.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;

    @Override
    public void populatingDatabase(String urlOldSystem) throws Exception {
        JSONParser jsonParser = new JSONParser();
        URL url = JsonUtils.createUrl(urlOldSystem);
        String resultJson = JsonUtils.parseUrl(url);
        JSONArray clientJsonArray = (JSONArray) jsonParser.parse(resultJson);

        for (Object it : clientJsonArray){
            // Получаем ключ из JSON
            JSONObject jsonKey = (JSONObject) it;

            String firstName = (String) jsonKey.get("firstName");
            String lastName = (String) jsonKey.get("lastName");
            String oldClientGuid = (String) jsonKey.get("guid");
//            String statusId = (String) jsonKey.get("status");

//            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            LocalDateTime dateTime = LocalDateTime.parse(, format);
//            Date date = new Date();
//            PatientDto clientDto = new PatientDto("21", "Fdf", "fdf", "dfdf",
//                    null,null,dateTime);
            Patient patientOne = new Patient(firstName, lastName, oldClientGuid, PatientStatusEnum.ACTIVE);
            patientRepository.save(patientOne);
        }
    }
}
