package com.cleverdev.clientService.repository;

import com.cleverdev.clientService.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
