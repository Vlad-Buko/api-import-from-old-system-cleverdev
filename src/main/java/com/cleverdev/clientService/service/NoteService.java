package com.cleverdev.clientService.service;

import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.exceptions.NoteNotFoundException;
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
import java.util.HashMap;
import java.util.Map;

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

    public Note createNewNote(NoteModel noteModel, String userLogin, String patientGuid) {
        User user = userRepo.findByLogin(userLogin);
        Patient patient = patientRepo.findByOldClientGuid(patientGuid);
        NoteDto newNoteDto = noteConvert.fromNoteModelToNoteDto(noteModel);
        newNoteDto.setCreatedDateTime(LocalDateTime.now());
        newNoteDto.setLastModifiedDateTime(LocalDateTime.now());
        newNoteDto.setCreatedByUserId(user);
        newNoteDto.setLastModifiedByUserId(user);
        newNoteDto.setPatient(patient);
        return noteRepo.save(noteConvert.fromNoteDtoToNote(newNoteDto));
    }

    public void updateOneNote(NoteModel noteModel, Long idNote) {
        Note note = noteRepo.getById(idNote);
        note.setComment(noteModel.getNote());
//        note.setLastModifiedByUserId();
        noteRepo.save(note);
    }

    public HashMap<Object, Object> showNotes(String userLogin) {
        User user = userRepo.findByLogin(userLogin);
        return noteRepo.findByCreatedByUserId(user.getId());
    }

    public boolean deleteNoteFromSystem(Long id) throws NoteNotFoundException {
        if (noteRepo.findById(id).isEmpty()) {
            throw new NoteNotFoundException();
        }
        noteRepo.deleteById(id);
        return true;
    }

    public NoteDto createdNoteFromOldSystem(Patient findIdPatientForWriteForNoteEntity,
                                            User findIdUserForWriteUserEntity,
                                            Map jsonNoteKey) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        NoteDto noteDto;

        noteDto = NoteDto.builder()
                .createdDateTime(LocalDateTime.parse((String) jsonNoteKey.get("createdDateTime"), formatter))
                .lastModifiedDateTime(LocalDateTime.parse(jsonNoteKey.get("modifiedDateTime").toString(), formatter))
                .createdByUserId(findIdUserForWriteUserEntity)
                .lastModifiedByUserId(findIdUserForWriteUserEntity)
                .comment((String) jsonNoteKey.get("comments"))
                .patient(findIdPatientForWriteForNoteEntity)
                .build();
        return noteDto;
    }

}