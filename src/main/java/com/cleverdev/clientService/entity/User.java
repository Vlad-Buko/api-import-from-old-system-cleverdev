package com.cleverdev.clientService.entity;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdByUserId")
    private List<Note> listNote;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lastModifiedByUserId")
//    private List<Note> list;
}
