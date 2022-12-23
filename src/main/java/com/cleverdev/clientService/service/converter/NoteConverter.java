package com.cleverdev.clientService.service.converter;

import com.cleverdev.clientService.dto.NoteDto;
import com.cleverdev.clientService.entity.Note;
import com.cleverdev.clientService.model.NoteModel;
import org.springframework.stereotype.Component;

/**
 * Created by Vladislav Domaniewski
 */

@Component
public class NoteConverter {

    public Note fromNoteDtoToNote(NoteDto noteDto) {
        return Note.builder()
                .createdDateTime(noteDto.getCreatedDateTime())
                .lastModifiedDateTime(noteDto.getLastModifiedDateTime())
                .createdByUserId(noteDto.getCreatedByUserId())
                .lastModifiedByUserId(noteDto.getLastModifiedByUserId())
                .comment(noteDto.getComment())
                .patient(noteDto.getPatient())
                .build();
    }

    public NoteDto fromNoteModelToNoteDto(NoteModel noteModel) {
        return NoteDto.builder()
                .comment(noteModel.getNote())
                .build();
    }
}
