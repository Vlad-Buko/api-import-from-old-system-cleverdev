package com.cleverdev.clientService.service;

import com.cleverdev.clientService.exceptions.PatientNotFoundException;
import com.cleverdev.clientService.service.converter.PatientConvert;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.exceptions.GuidAlreadyExistException;
import com.cleverdev.clientService.model.PatientModel;
import com.cleverdev.clientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;


/**
 * Created by Vladislav Domaniewski
 */

@Service
@RequiredArgsConstructor
public class PatientService {
    ParameterizedTypeReference<JSONArray> typeRef = new ParameterizedTypeReference<>() {
    };
    private final PatientRepository patientRepository;
    private final PatientConvert patientConvert;

    public boolean createPatient(PatientModel patientModel) throws GuidAlreadyExistException {
        if ((patientRepository.findByOldClientGuid(patientModel.getOldClientGuid())) == null) {
            patientRepository.save(patientConvert.fromPatientModelToPatient(patientModel));
            return true;
        } else
            throw new GuidAlreadyExistException("EXCEPTION : Patient with this guid exists, unique values for type guid are required");
        }

    public Patient pathMappingPatient(PatientModel patientModel, String guid) {
        Patient patient = patientRepository.findByOldClientGuid(guid);
        patient.setFirstName(patientModel.getFirstName());
        patient.setLastName(patientModel.getLastName());
        patient.setOldClientGuid(patientModel.getOldClientGuid());
        patient.setStatusId(patientModel.getStatusId());
        patientRepository.save(patient);
        return patient;
    }

    public Patient getPatientFromDb(Long id) throws PatientNotFoundException {
        if (patientRepository.getById(id) != null) {
            return patientRepository.getById(id);
        }
        throw new PatientNotFoundException("This patient not found!");
    }
    public Patient deletePatientFromDb(Long id) throws PatientNotFoundException {
        if (patientRepository.getById(id) == null)
            throw new PatientNotFoundException("This patient not found!");

        Patient patient = patientRepository.getById(id);
        patientRepository.deleteById(id);
        return patient;
    }
}
