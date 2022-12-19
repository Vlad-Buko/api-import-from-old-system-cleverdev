package com.cleverdev.clientService.service;

import com.cleverdev.clientService.converter.PatientConvert;
import com.cleverdev.clientService.dto.PatientDto;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.enums.PatientStatusEnum;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@Service()
@RequiredArgsConstructor
public class NoteService implements GetJsonFromOldSystem {
    private final PatientRepository patientRepo;
    private final NoteRepository noteRepo;
    private final PatientService patientService;
    private final PatientConvert patientConvert;

    @Override
    public JSONArray getJsonObjFromOldSystem(String urlClient) {
        return null;
    }



    @Override
    public void populatingDataBaseNotes(String urlNote) {

    }
}