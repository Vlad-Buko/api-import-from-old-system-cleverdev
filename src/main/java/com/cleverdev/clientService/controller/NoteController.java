package com.cleverdev.clientService.controller;

import com.cleverdev.clientService.exceptions.NoteNotFoundException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
import com.cleverdev.clientService.model.NoteModel;
import com.cleverdev.clientService.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Vladislav Domaniewski
 */

@Log4j2
@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
@ControllerAdvice
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/create")
    public ResponseEntity<Void> createNewNote(@RequestBody NoteModel noteModel) {
        try {
            noteService.createNewNote(noteModel);
            log.info("Note was be added!");
        } catch (UserNotFoundException e) {
            log.error("Note isn't be added, because user not found");
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-note")
    public Map<Long, String> showNoteForUser (@RequestParam String userLogin) {
        return noteService.showNotes(userLogin);
    }

    @PatchMapping("/change")
    public ResponseEntity<?> changeNote(@RequestBody NoteModel noteModel,
                                        @RequestParam Long id) {
        noteService.updateOneNote(noteModel, id);
        log.info("Note was be change");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNoteFromDB(@RequestParam Long idNote)  {
        try {
            noteService.deleteNoteFromSystem(idNote);
            log.info("Note be deleted");
        } catch (NoteNotFoundException e) {
            log.error("Note was be not found! Please, write another id!");
        }
        return ResponseEntity.ok().build();
    }
}
