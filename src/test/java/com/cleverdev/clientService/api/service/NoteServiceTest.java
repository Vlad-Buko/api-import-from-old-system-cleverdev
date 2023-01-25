package com.cleverdev.clientService.api.service;

import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.exceptions.NoteNotFoundException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
import com.cleverdev.clientService.model.NoteModel;
import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.repository.PatientRepository;
import com.cleverdev.clientService.repository.UserRepository;
import com.cleverdev.clientService.service.NoteService;
import com.cleverdev.clientService.service.converter.NoteConverter;
import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import org.aspectj.weaver.ast.Not;
import org.checkerframework.checker.nullness.Opt;
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
import java.util.Optional;

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

    List<Note> noteList = initNoteFromDbEntity();
    List<User> userList = initListUser();
    List<Patient> patientList = initListPatient();

    @Before
    public void initTestForNotes() {

    }

    @Test
    public void createdNewNoteTest() {

        NoteModel noteModel = new NoteModel();
        noteModel.setNote("Something with heart");
        noteModel.setPatientGuid("1111-2222-3333-44444");
        noteModel.setUserLogin("mar.var");
        Mockito.when(userRepository.findByLogin(noteModel.getUserLogin()))
                .thenReturn(userList.get(0));


        Mockito.when(patientRepository.findByOldClientGuid(noteModel.getPatientGuid()))
                .thenReturn(patientList.get(0));

        try {
            Note actual = noteService.createNewNote(noteModel);
            Note expected = noteList.get(0);
            Assertions.assertEquals(expected, actual);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test(expected = NoteNotFoundException.class)
    public void checkDeleteNote() throws NoteNotFoundException {
        List<Note> lists = initNoteFromDbEntity();
        Mockito.when(noteRepository.findById(1l)).thenReturn(null);
        noteService.deleteNoteFromSystem(1l);
    }

    // Наши данные хранятся в БД
    // Здесь якобы БД

    private List<User> initListUser() {
        User userFirst = User.builder()
                .id(1l)
                .login("mor.var")
                .build();

        User userSecond = User.builder()
                .id(2l)
                .login("mor.var")
                .build();
        User userThirty = User.builder()
                .id(3l)
                .login("mor.var")
                .build();
        User userForty = User.builder()
                .id(4l)
                .login("mor.var")
                .build();
        return List.of(userFirst, userSecond, userThirty, userForty);
    }

    private List<Note> initNoteFromDbEntity() {
        Note noteFirst = Note.builder()
                .id(1l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .lastModifiedByUserId(userList.get(0))
                .comment("Health coach daily reminder sent to patient.")
                .patient(patientList.get(0))
                .build();

        Note noteSecond = Note.builder()
                .id(2l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .createdByUserId(userList.get(0))
                .lastModifiedByUserId(userList.get(0))
                .comment("Health coach reminder sent.")
                .patient(patientList.get(1))
                .build();

        Note noteThirty = Note.builder()
                .id(3l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .createdByUserId(userList.get(0))
                .lastModifiedByUserId(userList.get(0))
                .comment("Health coach reminder sent.")
                .patient(patientList.get(2))
                .build();

        Note noteForty = Note.builder()
                .id(4l)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .createdByUserId(userList.get(0))
                .lastModifiedByUserId(userList.get(0))
                .comment("Health coach reminder sent.")
                .patient(patientList.get(3))
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
                .lastName("Robilko")
                .oldClientGuid("0000-0000-3333-0000")
                .statusId(PatientStatusEnum.ACTIVE)
                .build();

        return List.of(patientFirst, patientSecond, patientThirty, patientForty);
    }
}
