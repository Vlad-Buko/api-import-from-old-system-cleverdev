package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.exceptions.NoteNotFoundException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
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
    org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NoteController.class);
    private final NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<Void> createNewNote(@RequestBody NoteModel noteModel) {
        try {
            noteService.createNewNote(noteModel);
            logger.info("Note was be added!");
        } catch (UserNotFoundException e) {
            logger.error("Note isn't be added, because user not found");
        }
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
    public ResponseEntity<Void> deleteNoteFromDB(@RequestParam Long idNote)  {
        try {
            noteService.deleteNoteFromSystem(2l);
            logger.info("Note be deleted");
        } catch (NoteNotFoundException e) {
            logger.error("Note was be not found! Please, write another id!");
        }
        return ResponseEntity.ok().build();
    }
}
