package com.cleverdev.clientService.client.repository;

import com.cleverdev.clientService.client.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
