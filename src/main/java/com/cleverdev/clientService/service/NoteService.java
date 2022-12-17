package com.cleverdev.clientService.service;

import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.enums.PatientStatusEnum;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@Service(value = "vv")
@RequiredArgsConstructor
public class NoteService implements GetJsonFromOldSystem {
    private final PatientRepository patientRepo;
    private final NoteRepository noteRepo;

    @Override
    public JSONArray getJsonObjFromOldSystem(String urlClient) {
        return null;
    }

    @Override
    public void populatingDataBaseNotes(String urlNote) {
        List<Patient> patientWithActive = patientRepo.findByStatusId(PatientStatusEnum.ACTIVE);
        for (Patient ob : patientWithActive) {


        }
//        try{
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            //reqobj
//            JSONObject request = new JSONObject();
//            request.put("agency", "v02");
//            request.put("dateFrom", "2020-12-15");
//            request.put("dateTo", "2022-12-15");
//            request.put("clientGuid", "046EF858-FAF6-A193-308A-72FC05B39993");
//            //Or Hashmap
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<JSONObject> entity = new HttpEntity<>( request, headers);
//            ResponseEntity<JSONArray> response = restTemplate.exchange(urlNote, HttpMethod.POST, entity, typeRef);//example of post req with json as request payload
////            if(Integer.parseInt(response.getStatusCode().toString()) == HttpURLConnection.HTTP_OK)
////            {
//            JSONArray  responsedetails = response.getBody();
//            System.out.println(responsedetails);//whole json response as map object
//
//            for (Object it : responsedetails) {
//                JSONObject jsonKey = (JSONObject) it;
//            }
////            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            System.err.println(e);
//        }
        Patient patient = new Patient();
        for (int i = 1; i < 5; i++) {
            patient = patientRepo.findById(i);
            System.out.println("Hello!" + " " + patient.getFirstName());

        }
        List<Patient> pati = patientRepo.findAll();
        System.out.println(pati.size());
        System.out.println(patientWithActive);
    }
}
