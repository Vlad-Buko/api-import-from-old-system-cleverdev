package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.dto.ClientNoteDto;
import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Vladislav Domaniewski
 */

@Log
@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/dd")
    public ResponseEntity<Void> createNewNote(@RequestBody NoteDto noteDto,
                                              @RequestParam String userLogin,
                                              @RequestParam String patientGuid) {
        noteService.createNewNote();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNoteFromDB(@PathVariable String guid){
        return ResponseEntity.ok().build();
    }
}
