package com.cleverdev.clientService.entity;

import com.cleverdev.clientService.service.enums.NoteStatusEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@Getter
@Setter
@Table(name = "status_note")
public class ImportNoteStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private NoteStatusEnum noteStatusEnum;

    @OneToOne(mappedBy = "noteStatus")
    private Note note;
}
