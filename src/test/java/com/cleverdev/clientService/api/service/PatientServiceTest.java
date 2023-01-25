package com.cleverdev.clientService.api.service;

import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.exceptions.GuidAlreadyExistException;
import com.cleverdev.clientService.exceptions.PatientNotFoundException;
import com.cleverdev.clientService.model.PatientModel;
import com.cleverdev.clientService.repository.PatientRepository;
import com.cleverdev.clientService.service.PatientService;
import com.cleverdev.clientService.service.converter.PatientConvert;
import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientConvert patientConvert;

    @Test
    public void createNewPatient() {
        PatientModel patientModel = new PatientModel();
        patientModel.setFirstName("Joker");
        patientModel.setLastName("Black");
        patientModel.setStatusId(PatientStatusEnum.ACTIVE);
        patientModel.setOldClientGuid("4444-4444-2525");
        Mockito.when(patientRepository.findByOldClientGuid(patientModel.getOldClientGuid())).thenReturn(null);
        try {
            Assertions.assertTrue(patientService.createPatient(patientModel));
        } catch (GuidAlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = GuidAlreadyExistException.class)
    public void waitingForAnGuidAlreadyExistException() throws GuidAlreadyExistException {
        List<Patient> patientList = initPatientList();

        PatientModel patientModel = new PatientModel();
        patientModel.setFirstName("Vlad");
        patientModel.setLastName("Brodskiy");
        patientModel.setStatusId(PatientStatusEnum.ACTIVE);
        patientModel.setOldClientGuid("4444-4444-4444");

        Mockito.when(patientRepository.findByOldClientGuid(patientModel
                .getOldClientGuid())).thenReturn(patientList.get(2));

        patientService.createPatient(patientModel);
    }

    @Test
    public void checkUpdatePatientInDb() {
        List<Patient> patientList = initPatientList();
        String guid = "4444-2222-4444";
        Mockito.when(patientRepository.findByOldClientGuid(guid)).thenReturn(patientList.get(3));
        PatientModel patientModel = new PatientModel();
        patientModel.setFirstName("Vlad");
        patientModel.setLastName("Brodskiy");
        patientModel.setStatusId(PatientStatusEnum.ACTIVE);
        patientModel.setOldClientGuid("4444-4444-4444");

        Patient active = patientService.pathMappingPatient(patientModel, guid);
        Mockito.when(patientConvert.fromPatientModelToPatient(patientModel)).thenReturn(active);
        Patient expected = patientConvert.fromPatientModelToPatient(patientModel);
        Assertions.assertEquals(expected, active);
    }

    @Test(expected = PatientNotFoundException.class)
    public void waitingForAnPatientNotFoundException() throws PatientNotFoundException {
        List<Patient> list = initPatientList();
        Mockito.when(patientRepository.getById(44l)).thenReturn(null);
        patientService.getPatientFromDb(2l);
    }

    @Test
    public void checkGetPatient() throws PatientNotFoundException {
        List<Patient> patientList = initPatientList();
        Mockito.when(patientRepository.getById(1l)).thenReturn(patientList.get(0));
        Boolean expected = false;
        if (patientService.getPatientFromDb(1l) != null) {
            expected = true;
        }
        Assertions.assertTrue(expected);
    }

    @Test(expected = PatientNotFoundException.class)
    public void checkDeletePatientFromDb () throws PatientNotFoundException {
        // Если пациента не находим, выбрасывает исключение
        List<Patient> patientList = initPatientList();
        Mockito.when(patientRepository.getById(1l)).thenReturn(patientList.get(0));
        patientService.deletePatientFromDb(2l);
    }

    private List<Patient> initPatientList() {
        Patient patientOne = new Patient();
        Patient patientTwo = new Patient();
        Patient patientThree = new Patient();
        Patient patientFour = new Patient();

        patientOne.setId(1l);
        patientOne.setFirstName("Joo");
        patientOne.setLastName("Pike");
        patientOne.setOldClientGuid("1111-2222-3333");
        patientOne.setStatusId(PatientStatusEnum.PENDING);
        patientOne.setListNoteForOnePatient(null);

        patientTwo.setId(2l);
        patientTwo.setFirstName("Viktor");
        patientTwo.setLastName("Belyavski");
        patientTwo.setOldClientGuid("2222-3333-1111");
        patientTwo.setStatusId(PatientStatusEnum.INACTIVE);
        patientTwo.setListNoteForOnePatient(null);

        patientThree.setId(3l);
        patientThree.setFirstName("Katya");
        patientThree.setLastName("Rusakova");
        patientThree.setOldClientGuid("4444-4444-4444");
        patientThree.setStatusId(PatientStatusEnum.ACTIVE);
        patientThree.setListNoteForOnePatient(null);

        patientFour.setId(4l);
        patientFour.setFirstName("Viktoriya");
        patientFour.setLastName("Damoniewskaya");
        patientFour.setOldClientGuid("4444-2222-4444");
        patientFour.setStatusId(PatientStatusEnum.ACTIVE);
        patientFour.setListNoteForOnePatient(null);

        return List.of(patientOne, patientTwo, patientThree, patientFour);
    }

}
