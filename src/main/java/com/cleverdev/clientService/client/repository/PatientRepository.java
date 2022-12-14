package com.cleverdev.clientService.client.repository;


import com.cleverdev.clientService.client.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
