package com.cleverdev.clientService.repository;

import com.cleverdev.clientService.entity.ImportNoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteStatusRepository extends JpaRepository<ImportNoteStatus, Long> {
}
