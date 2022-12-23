package com.cleverdev.clientService.repository;

import com.cleverdev.clientService.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedHashMap;

public interface NoteRepository extends JpaRepository<Note, Long> {
    LinkedHashMap<Object, Object> findByCreatedByUserId(Long id);
}
