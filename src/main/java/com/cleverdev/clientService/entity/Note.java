package com.cleverdev.clientService.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * Created by Vladislav Domaniewski
 */

@Entity
@Table(name = "patient_note")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Note {
    @Id
    @NotNull
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @Column(name = "last_modified_date_time")
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @Null
    @JoinColumn(name = "created_by_user_id")
    private User createdByUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @Null
    @JoinColumn(name = "last_modified_by_user_id")
    private User lastModifiedByUserId;

    @Column(name = "note", length = 4000)
    @Null
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

}
