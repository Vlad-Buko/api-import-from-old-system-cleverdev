package com.cleverdev.clientService.dto;

import com.cleverdev.clientService.entity.Patient;
import com.cleverdev.clientService.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by Vladislav Domaniewski
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDto {
    @Column(name = "created_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDateTime;

    @Column(name = "created_by_user_id")
    private User createdByUserId;

    @Column(name = "last_modified_by_user_id")
    private User lastModifiedByUserId;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
