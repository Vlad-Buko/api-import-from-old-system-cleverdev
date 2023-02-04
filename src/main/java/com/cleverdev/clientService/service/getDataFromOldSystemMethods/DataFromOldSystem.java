package com.cleverdev.clientService.service.getDataFromOldSystemMethods;

import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.UserRepository;
import com.cleverdev.clientService.service.NoteService;
import com.cleverdev.clientService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Vladislav Domaniewski
 */

@Component
@RequiredArgsConstructor
public class DataFromOldSystem {

    private final UserRepository userRepo;
    private final UserService userService;
    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private long countNote;

    public Long saveNoteInDB(JSONArray responseDetailsNotes, Patient patient) {

        for (Object it : responseDetailsNotes) {

            LinkedHashMap<Object, Object> jsonNoteKey = (LinkedHashMap) it;

            User findIdUserForWriteUserEntity = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));  // 0
            if (findIdUserForWriteUserEntity == null) {
                userService.saveUserFromOldVersionInNew((String) jsonNoteKey.get("loggedUser"));
                User findIfNotFound = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));
                findIdUserForWriteUserEntity = findIfNotFound;
            }

            // Логика проверки на наличие в БД заметки
            // ---
            Note note = noteService.createdNoteFromOldSystem(patient,
                    findIdUserForWriteUserEntity, jsonNoteKey);
            Optional<Note> findData = noteRepository.findByCreatedDateTime(note.getCreatedDateTime());
            if (findData.isEmpty()) {
                noteRepository.save(note);
                countNote++;
            }
        }
        return countNote;
    }

    public long getCountNote() {
        return countNote;
    }

    public void setCountNote(long countNote) {
        this.countNote = countNote;
    }
}

