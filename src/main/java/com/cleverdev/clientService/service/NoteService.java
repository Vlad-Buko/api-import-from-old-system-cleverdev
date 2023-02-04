package com.cleverdev.clientService.service;

import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.exceptions.NoteNotFoundException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
import com.cleverdev.clientService.model.NoteModel;
import com.cleverdev.clientService.repository.UserRepository;
import com.cleverdev.clientService.service.converter.NoteConverter;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Vladislav Domaniewski
 */

@Service()
@RequiredArgsConstructor
public class NoteService {
    private final PatientRepository patientRepo;
    private final UserRepository userRepo;
    private final NoteRepository noteRepo;
    private final NoteConverter noteConvert;

    public Boolean createNewNote(NoteModel noteModel) {
        User user = userRepo.findByLogin(noteModel.getUserLogin());
        if (user == null) {
            throw new UserNotFoundException("User not found in DB, please, try else one");
        }

        Patient patient = patientRepo.findByOldClientGuid(noteModel.getPatientGuid());
        NoteDto noteDto = NoteDto.builder()
                         .comment(noteModel.getNote())
                        .createdDateTime(LocalDateTime.now())
                        .lastModifiedDateTime(LocalDateTime.now())
                        .createdByUserId(user)
                        .lastModifiedByUserId(user)
                        .patient(patient).build();
        noteRepo.save(noteConvert.fromNoteDtoToNote(noteDto));
        return true;
    }

    public Note updateOneNote(NoteModel noteModel, Long idNote) {
        User user = userRepo.findByLogin(noteModel.getUserLogin());
        Note note = noteRepo.getById(idNote);
        if (user == null) {
            throw new UserNotFoundException("User not found in DB, please, try else one");
        }
        note.setComment(noteModel.getNote());
        note.setLastModifiedByUserId(user);
        note.setLastModifiedDateTime(LocalDateTime.now());
        noteRepo.save(note);
        return note;
    }

    public Map<Long, String> showNotes(String userLogin) {
        User user = userRepo.findByLogin(userLogin);
        List<Note> listNote = user.getListNoteForUserCreated();
        Map<Long, String> notes = new HashMap<>();
        for (Note note : listNote) {
            notes.put(note.getId(), note.getComment());
        }
        return notes;
    }

    public boolean deleteNoteFromSystem(Long id) throws NoteNotFoundException {
        if (noteRepo.findById(id).isEmpty()) {
            throw new NoteNotFoundException();
        } else {
            noteRepo.deleteById(id);
        }
        return true;
    }

    public Note createdNoteFromOldSystem(Patient findIdPatientForWriteForNoteEntity,
                                            User findIdUserForWriteUserEntity,
                                            Map jsonNoteKey) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        NoteDto noteDto;

        // Проверяем дату со старой системой

        Optional<Note> note = noteRepo.findByCreatedDateTime(LocalDateTime.parse((String) jsonNoteKey.get("createdDateTime"), formatter));
        LocalDateTime dateTimeFromOldSystem = LocalDateTime.parse(jsonNoteKey.get("modifiedDateTime").toString(), formatter);
        String commentUpdate = (String) jsonNoteKey.get("comments");

        if (note.isPresent()) {
            if(note.get().getLastModifiedDateTime().equals(dateTimeFromOldSystem)) {
                return note.get();
            }
            if(note.get().getLastModifiedDateTime().isBefore(dateTimeFromOldSystem)) {
                note.get().setLastModifiedDateTime(dateTimeFromOldSystem);
                note.get().setComment(commentUpdate);
                noteRepo.save(note.get());
            }
            if(note.get().getLastModifiedDateTime().isAfter(dateTimeFromOldSystem)) {
                noteRepo.save(note.get());
            }
        }

        noteDto = NoteDto.builder()
                // дата создания заметки равняется дате создания в старой системе
                .createdDateTime(LocalDateTime.parse((String) jsonNoteKey.get("createdDateTime"), formatter))
                .lastModifiedDateTime(LocalDateTime.parse(jsonNoteKey.get("modifiedDateTime").toString(), formatter))
                .createdByUserId(findIdUserForWriteUserEntity)
                .lastModifiedByUserId(findIdUserForWriteUserEntity)
                .comment((String) jsonNoteKey.get("comments"))
                .patient(findIdPatientForWriteForNoteEntity)
                .build();
        return noteConvert.fromNoteDtoToNote(noteDto);
    }
}