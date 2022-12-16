package notes;

import com.cleverdev.clientService.patient.dto.ClientNoteRequestDto;
import com.cleverdev.clientService.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by Vladislav Domaniewski
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class PatientControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final PatientRepository patientRepository;

    @After
    public void deleteDataFromDb(){
        patientRepository.deleteAll();
    }

    @Test
    public void whenReadCLientsFromOldSystem() {
        ClientNoteRequestDto patient = new ClientNoteRequestDto();
        patient.setAgency("v01");
        patient.setClientGuid("33955E86-FD4E-5CF5-175A-721DABD79070");
        LocalDate date1 = LocalDate.of(2019, 2, 22);
        LocalDate date2 = LocalDate.of(2022, 2, 22);
        patient.setDateFrom(date1);
        patient.setDateTo(date2);
//        ResponseEntity<ClientNoteRequestDto> response = restTemplate.getForEntity("/persons", patient, ClientNoteRequestDto.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//        assertThat(response.getBody().getAgency(), notNullValue());
//        assertThat(response.getBody().getClientGuid(), is("Michail"));
    }


}
