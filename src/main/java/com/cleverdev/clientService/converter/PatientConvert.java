package com.cleverdev.clientService.converter;

import com.cleverdev.clientService.dto.PatientDto;
import com.cleverdev.clientService.entity.Patient;
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
}
