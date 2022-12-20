package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.exceptions.GuidAlreadyExistException;
import com.cleverdev.clientService.model.PatientModel;
import com.cleverdev.clientService.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vladislav Domaniewski
 */

@Log
@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/create-patient")
    public void createPatient(@RequestBody PatientModel patientModel) {
        log.info("Patient added successful in DB!!!");
        try {
            patientService.createPatient(patientModel);
        } catch (GuidAlreadyExistException e) {
            System.out.println(e.getMessage());
        }

    }

    @PatchMapping("/update")
    public void updateStudent(@RequestBody PatientModel patientModel,
                              @RequestParam String guidFromOldSystem) {

        log.info("Patient " + patientService.pathMappingPatient(patientModel,
                guidFromOldSystem) + "was updated!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.info("Patient " + patientService.deletePatientFromDb(id) + " successful successful from DB!!!");
        return ResponseEntity.ok().build();
    }

}

