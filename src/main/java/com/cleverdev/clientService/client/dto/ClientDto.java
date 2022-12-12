package com.cleverdev.clientService.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@JsonFormat
public class ClientDto implements Serializable{

    private List<ClientDtoOne> clients;

    public List<ClientDtoOne> getClients() {
            return clients;
    }
}
