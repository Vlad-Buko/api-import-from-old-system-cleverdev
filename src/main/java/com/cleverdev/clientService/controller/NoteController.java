package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.exceptions.NoteNotFoundException;
import com.cleverdev.clientService.model.NoteModel;
import com.cleverdev.clientService.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by Vladislav Domaniewski
 */

@Log
@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
@ControllerAdvice
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<Void> createNewNote(@RequestBody NoteModel noteModel,
                                              @RequestParam String userLogin,
                                              @RequestParam String patientGuidOrLoginId) {
        noteService.createNewNote(noteModel, userLogin, patientGuidOrLoginId);
        log.info("Note was be added!");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-note")
    public HashMap<Object, Object> showNoteForUser (@RequestParam String userLogin) {
        return noteService.showNotes(userLogin);
    }

    @PatchMapping("/change-note")
    public ResponseEntity<Void> changeNote(@RequestBody NoteModel noteModel,
                                           @RequestParam Long id) {
        noteService.updateOneNote(noteModel, id);
        log.info("Note was be change");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNoteFromDB(@PathVariable Long idNote)
            throws NoteNotFoundException {
        try {
            noteService.deleteNoteFromSystem(idNote);
        } catch (NoteNotFoundException e) {
            throw new NoteNotFoundException("Note was be not found! Please, write another id!");
        }
        log.info("NOTE was be delete!");
        return ResponseEntity.ok().build();
    }
}
