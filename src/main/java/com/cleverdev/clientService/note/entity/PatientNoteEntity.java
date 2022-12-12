package com.cleverdev.clientService.note.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@Table(name = "patient_note")
@Data
public class PatientNoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time")
    private LocalDateTime lastModifiedDateTime;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "last_modified_by_user_id")
    private Long lastModifiedByUserId;

    @Column(name = "note")
    private String note;

//    @Column(name = "patient_id")
//    private Patient;
}
