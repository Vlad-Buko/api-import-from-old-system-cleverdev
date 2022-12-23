package com.cleverdev.clientService.entity;

import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@Data
@Table(name = "patient_profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "old_client_guid")
    private String oldClientGuid;

    @Column(name = "status_id")
    @Enumerated(EnumType.ORDINAL)
    private PatientStatusEnum statusId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Note> listNoteForOnePatient;
}
