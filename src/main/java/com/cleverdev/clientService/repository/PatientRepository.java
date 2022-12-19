package com.cleverdev.clientService.repository;


import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.enums.PatientStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findById(long id);
    List<Patient> findByStatusId(PatientStatusEnum status);
    Patient findByOldClientGuid(String guid);
}
