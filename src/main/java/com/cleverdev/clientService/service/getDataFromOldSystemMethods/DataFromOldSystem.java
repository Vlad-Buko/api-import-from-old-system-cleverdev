package com.cleverdev.clientService.service.getDataFromOldSystemMethods;

import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import com.cleverdev.clientService.repository.UserRepository;
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
    private final NoteRepository noteRepo;
    private final NoteConverter noteConverter;

    public void saveNoteInDB(JSONArray responseDetailsNotes, LinkedHashMap<Object, Object> jsonPatientKey) {

//        List<Note> noteList = noteRepo.findAll();
//        Set<Note> setNote = new LinkedHashSet<>(noteList);

        for (Object it : responseDetailsNotes) {
            Patient findIdPatientForWriteForNoteEntity = patientRepo.findByOldClientGuid((String) jsonPatientKey.get("guid"));
            NoteDto noteDto;

            LinkedHashMap<Object, Object> jsonNoteKey = (LinkedHashMap) it;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

            User findIdUserForWriteUserEntity = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));  // 0
            if (findIdUserForWriteUserEntity == null) {
                userService.saveUserFromOldVersionInNew((String) jsonNoteKey.get("loggedUser"));
                User findIfNotFound = userRepo.findByLogin((String) jsonNoteKey.get("loggedUser"));
                findIdUserForWriteUserEntity = findIfNotFound;
            }
            noteDto = NoteDto.builder()
                    .createdDateTime(LocalDateTime.parse((String) jsonNoteKey.get("createdDateTime"), formatter))
                    .lastModifiedDateTime(LocalDateTime.parse(jsonNoteKey.get("modifiedDateTime").toString(), formatter))
                    .createdByUserId(findIdUserForWriteUserEntity)
                    .lastModifiedByUserId(findIdUserForWriteUserEntity)
                    .comment((String) jsonNoteKey.get("comments"))
                    .patient(findIdPatientForWriteForNoteEntity)
                    .build();

            // Логика проверки на наличие в БД заметки
            // ---
            noteRepo.save(noteConverter.fromNoteDtoToNote(noteDto));
        }
    }
}
