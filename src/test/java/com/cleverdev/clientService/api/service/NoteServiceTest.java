package com.cleverdev.clientService.api.service;

import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.model.NoteModel;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import com.cleverdev.clientService.repository.UserRepository;
import com.cleverdev.clientService.service.NoteService;
import com.cleverdev.clientService.service.converter.NoteConverter;
import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import org.aspectj.weaver.ast.Not;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {

    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private NoteConverter noteConverter;

    @Before
    public void initTestForNotes() {

    }

    @Test
    public void createdNewNoteTest() {
        List<User> userList = initListUser();
        List<Patient> patientList = initListPatient();
        List<Note> noteList = initNoteFromDbEntity();

        NoteModel noteModel = new NoteModel();
        noteModel.setNote("Something with heart");
        noteModel.setPatientGuid("1111-2222-3333-44444");
        noteModel.setUserLogin("mar.var");

        Mockito.when(userRepository.findByLogin(noteModel.getUserLogin()))
                .thenReturn(userList.get(0));
        Mockito.when(patientRepository.findByOldClientGuid(noteModel.getPatientGuid()))
                .thenReturn(patientList.get(0));

        Note actual = noteService.createNewNote(noteModel);
        Note expected = noteList.get(0);
        Assertions.assertEquals(expected, actual);
    }

    // Наши данные хранятся в БД
    // Здесь якобы БД

    private List<User> initListUser() {
        List<Note> listNote = initNoteFromDbEntity();
        User userFirst = User.builder()
                .id(1l)
                .login("mor.var")
                .listNoteForUserCreated(listNote)
                .listNoteForUserUpdated(listNote)
                .build();
        return List.of(userFirst);
    }

    private List<Note> initNoteFromDbEntity() {
        List <User> listUser = initListUser();
        List <Patient> patientList = initListPatient();

        Note noteFirst = Note.builder()
                .id(1l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .createdByUserId(listUser.get(0))
                .lastModifiedByUserId(listUser.get(0))
                .comment("Health coach daily reminder sent to patient.")
                .patient(patientList.get(0))
                .build();

        Note noteSecond = Note.builder()
                .id(2l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .createdByUserId(listUser.get(0))
                .lastModifiedByUserId(listUser.get(0))
                .comment("Health coach reminder sent.")
                .build();

        Note noteThirty = Note.builder()
                .id(3l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .createdByUserId(listUser.get(0))
                .lastModifiedByUserId(listUser.get(0))
                .comment("Health coach reminder sent.")
                .build();

        Note noteForty = Note.builder()
                .id(4l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .createdByUserId(listUser.get(0))
                .lastModifiedByUserId(listUser.get(0))
                .comment("Health coach reminder sent.")
                .build();

        return List.of(noteFirst, noteSecond, noteThirty, noteForty);
    }

    private List<Patient> initListPatient() {
        Patient patientFirst = Patient.builder()
                .id(1l)
                .firstName("Viktor")
                .lastName("Bezrukov")
                .oldClientGuid("1111-2222-3333-44444")
                .statusId(PatientStatusEnum.ACTIVE)
//                .listNoteForOnePatient()
                .build();

        Patient patientSecond = Patient.builder()
                .id(2l)
                .firstName("Valik")
                .lastName("Domanikov")
                .oldClientGuid("1111-2222-3333-0000")
                .statusId(PatientStatusEnum.PENDING)
                .build();

        Patient patientThirty = Patient.builder()
                .id(3l)
                .firstName("Max")
                .lastName("Dolmikov")
                .oldClientGuid("9999-4444-3333-0000")
                .statusId(PatientStatusEnum.INACTIVE)
                .build();

        Patient patientForty = Patient.builder()
                .id(4l)
                .firstName("Ira")
                .lastName("Roilko")
                .oldClientGuid("0000-0000-3333-0000")
                .statusId(PatientStatusEnum.ACTIVE)
                .build();

        return List.of(patientFirst, patientSecond, patientThirty, patientForty);
    }
}
