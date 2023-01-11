package com.cleverdev.clientService.api.service;

import com.cleverdev.clientService.repository.NoteRepository;
import com.cleverdev.clientService.service.NoteService;
import com.cleverdev.clientService.service.converter.NoteConverter;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

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
    private NoteConverter noteConverter;



}
