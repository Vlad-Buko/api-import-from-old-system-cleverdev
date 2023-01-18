package com.cleverdev.clientService.service.getDataFromOldSystemMethods;

import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import com.cleverdev.clientService.repository.UserRepository;
import com.cleverdev.clientService.service.NoteService;
import com.cleverdev.clientService.service.UserService;
import com.cleverdev.clientService.service.converter.NoteConverter;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Vladislav Domaniewski
 */

@Component
@RequiredArgsConstructor
public class DataFromOldSystem {

    private final PatientRepository patientRepo;
    private final UserRepository userRepo;
    private final UserService userService;
    private final NoteConverter noteConverter;
    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private List<Note> listNote = new ArrayList<>();

    public List<Note> saveNoteInDB(JSONArray responseDetailsNotes, LinkedHashMap<Object, Object> jsonPatientKey) {

        for (Object it : responseDetailsNotes) {
            Patient findIdPatientForWriteForNoteEntity = patientRepo.findByOldClientGuid((String) jsonPatientKey.get("guid"));

            LinkedHashMap<Object, Object> jsonNoteKey = (LinkedHashMap) it;

            User findIdUserForWriteUserEntity = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));  // 0
            if (findIdUserForWriteUserEntity == null) {
                userService.saveUserFromOldVersionInNew((String) jsonNoteKey.get("loggedUser"));
                User findIfNotFound = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));
                findIdUserForWriteUserEntity = findIfNotFound;
            }

            // Логика проверки на наличие в БД заметки
            // ---
            NoteDto noteDto = noteService.createdNoteFromOldSystem(findIdPatientForWriteForNoteEntity,
                    findIdUserForWriteUserEntity, jsonNoteKey);
            Optional<Note> findData = noteRepository.findByCreatedDateTime(noteDto.getCreatedDateTime());
            if (findData.isEmpty()) {
                listNote.add(noteConverter.fromNoteDtoToNote(noteDto));
            }
//            if (!findData.get().getComment().equals(noteDto.getComment())){
//                Long id = findData.get().getId();
//                noteRepository.deleteById(id);
//                Note note = noteConverter.fromNoteDtoToNote(noteDto);
//                note.setId(id);
//                listNote.add(note);
//            }

        }
        return listNote;
    }
}
