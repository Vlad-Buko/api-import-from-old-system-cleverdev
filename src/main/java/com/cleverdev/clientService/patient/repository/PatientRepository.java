package com.cleverdev.clientService.patient.repository;


import com.cleverdev.clientService.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
