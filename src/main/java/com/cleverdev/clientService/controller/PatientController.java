package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.exceptions.GuidAlreadyExistException;
import com.cleverdev.clientService.exceptions.PatientNotFoundException;
import com.cleverdev.clientService.model.PatientModel;
import com.cleverdev.clientService.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vladislav Domaniewski
 */

@Log4j2
@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPatient(@RequestBody PatientModel patientModel) {
        log.info("Patient added successful in DB!!!");
        try {
            patientService.createPatient(patientModel);
        } catch (GuidAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateStudent(@RequestBody PatientModel patientModel,
                              @RequestParam String guidFromOldSystem) {

        log.info("Patient " + patientService.pathMappingPatient(patientModel,
                guidFromOldSystem) + "was updated!");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-patient")
    public Patient getPatient(@RequestParam Long id) {
        try {
            return patientService.getPatientFromDb(id);
        } catch (PatientNotFoundException e) {
            e.printStackTrace();
        }
        return Patient.builder().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        try {
            log.info("Patient " + patientService.deletePatientFromDb(id) + " successful successful from DB!!!");
        } catch (PatientNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

}

