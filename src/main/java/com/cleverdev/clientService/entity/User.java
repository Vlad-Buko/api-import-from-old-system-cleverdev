package com.cleverdev.clientService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 3, max = 20, message = "Длина в пределах 3-20")
    @Column(name = "login")
    private String login;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "createdByUserId")
    private List<Note> listNote;

    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "lastModifiedByUserId")
    private List<Note> list;
}
