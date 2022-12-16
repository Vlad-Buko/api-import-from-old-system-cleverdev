package com.cleverdev.clientService.user.repository;

import com.cleverdev.clientService.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
