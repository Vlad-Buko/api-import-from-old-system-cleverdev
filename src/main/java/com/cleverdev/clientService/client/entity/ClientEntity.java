package com.cleverdev.clientService.client.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@Data
public class ClientEntity {
    @Id
    public Long id;
    public String agency;
    public String lastName;
}
