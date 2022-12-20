package com.cleverdev.clientService.service;

import com.cleverdev.clientService.service.converter.PatientConvert;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

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
}