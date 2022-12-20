package com.cleverdev.clientService.service;

import com.cleverdev.clientService.service.converter.PatientConvert;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.exceptions.GuidAlreadyExistException;
import com.cleverdev.clientService.model.PatientModel;
import com.cleverdev.clientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;



/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class PatientService implements GetJsonFromOldSystem {
    private final PatientRepository patientRepository;
    private final PatientConvert patientConvert;

    public void createPatient(PatientModel patientModel) throws GuidAlreadyExistException {
        if (patientRepository.findByOldClientGuid(patientModel.getOldClientGuid()) == null){
            patientRepository.save(patientConvert.fromPatientModelToPatient(patientModel));
        } else {
            Patient patient = patientRepository.findByOldClientGuid(patientModel.getOldClientGuid());
            if (patient.getOldClientGuid().equals(patientModel.getOldClientGuid())) {
                throw new GuidAlreadyExistException("EXCEPTION : Patient with this guid exists, unique values for type guid are required");
            }
        }
    }

    public Patient pathMappingPatient(PatientModel patientModel, String guid) {
        Patient patient = patientRepository.findByOldClientGuid(guid);
//        patient.setId(patient.getId());
        patient.setFirstName(patientModel.getFirstName());
        patient.setLastName(patientModel.getLastName());
        patient.setOldClientGuid(patientModel.getOldClientGuid());
        patient.setStatusId(patientModel.getStatusId());
        patientRepository.save(patient);
        return patient;
    }

    public Patient deletePatientFromDb(Long id) {
        Patient patient = patientRepository.getById(id);
        patientRepository.deleteById(id);
        return patient;
    }

    @Override
    public JSONArray getJsonObjFromOldSystem(String urlClient) {
        return null;
    }
}
