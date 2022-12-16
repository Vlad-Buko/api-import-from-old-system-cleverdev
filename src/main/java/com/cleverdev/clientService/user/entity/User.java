package com.cleverdev.clientService.user.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@NoArgsConstructor
@Data
@Table(name = "company_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;
}
