package com.cleverdev.clientService.entity;

import javax.persistence.*;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@Table(name = "guid")
public class Guid {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
