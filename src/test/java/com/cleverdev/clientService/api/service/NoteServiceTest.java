package com.cleverdev.clientService.api.service;

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
import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

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

    List<User> userList = initListUser();
    List<Patient> patientList = initListPatient();
    List<Note> noteList = initNoteFromDbEntity();

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

        Boolean actual = noteService.createNewNote(noteModel);
        Assertions.assertTrue(actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void createNewNoteExpectedExceptionWhenNotUserTest() {
        NoteModel noteModel = new NoteModel();
        Mockito.when(userRepository.findByLogin(noteModel.getUserLogin()))
                .thenReturn(null);
        noteService.createNewNote(noteModel);
    }

    @Test
    public void checkDeleteNote() throws NoteNotFoundException {
        List<Note> lists = initNoteFromDbEntity();
        Mockito.when(noteRepository.findById(1l)).thenReturn(Optional.of(lists.get(0)));
        Boolean actual = noteService.deleteNoteFromSystem(1l);
        Assertions.assertTrue(actual);
    }

    @Test
    public void updateNoteCheckTest() {
        NoteModel note = new NoteModel();
        note.setNote("Some notes, from England");
        note.setUserLogin("how.yang");
        Long idNote = 2l;
        Mockito.when(userRepository.findByLogin(note.getUserLogin())).thenReturn(userList.get(3));
        Mockito.when(noteRepository.getById(idNote)).thenReturn(noteList.get(2));
        Note noteFromService = noteService.updateOneNote(note, idNote);

        Note expected = new Note();
        expected.setComment(note.getNote());
        expected.setLastModifiedByUserId(userList.get(3));

        Note actual = new Note();
        actual.setComment(noteFromService.getComment());
        actual.setLastModifiedByUserId(noteFromService.getLastModifiedByUserId());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getListNoteForUserCheckTest() {
        String login = "ver.car";
        User user = initListUser().get(2);
        Mockito.when(userRepository.findByLogin(login)).thenReturn(user);

        user.setListNoteForUserCreated(initNoteFromDbEntity());
        Map<Long, String> expected = noteService.showNotes(login);
        Map<Long, String> actual = new HashMap<>();
        actual.put(1l, "Health coach daily reminder sent to patient.");
        actual.put(2l, "Health coach reminder sent.");
        actual.put(3l, "Health coach reminder sent.");
        actual.put(4l, "Health coach reminder sent.");

        Assertions.assertEquals(expected, actual);
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
                .login("vik.olk")
                .build();
        User userThirty = User.builder()
                .id(3l)
                .login("ver.car")
                .build();
        User userForty = User.builder()
                .id(4l)
                .login("how.yang")
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
                .listNoteForOnePatient(noteList)
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
