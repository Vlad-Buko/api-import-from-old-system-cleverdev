package com.cleverdev.clientService.service.converter;

import com.cleverdev.clientService.dto.PatientDto;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.model.PatientModel;
import org.springframework.stereotype.Component;

/**
 * Created by Vladislav Domaniewski
 */

@Component
public class PatientConvert {
    public Patient fromPatientDtoToPatient(PatientDto patientDto) {
        return Patient.builder()
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .oldClientGuid(patientDto.getGuid())
                .statusId(patientDto.getStatus())
                .build();
    }

    public Patient fromPatientModelToPatient(PatientModel patientModel) {
        return Patient.builder()
                .firstName(patientModel.getFirstName())
                .lastName(patientModel.getLastName())
                .oldClientGuid(patientModel.getOldClientGuid())
                .statusId(patientModel.getStatusId())
                .build();
    }
}
