package com.cleverdev.clientService.client.entity;

import com.cleverdev.clientService.client.enums.PatientStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@Data
@Table(name = "patient_profile")
@NoArgsConstructor
public class Patient {
    public Patient(String firstName, String lastName, String oldClientGuid, PatientStatusEnum statusId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oldClientGuid = oldClientGuid;
        this.statusId = statusId;
    }

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
    private PatientStatusEnum statusId;
    // value from Old version
//    private String agency;
}
