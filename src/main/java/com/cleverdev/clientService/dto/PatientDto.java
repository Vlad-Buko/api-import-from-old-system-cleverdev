package com.cleverdev.clientService.dto;

import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Vladislav Domaniewski
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDto implements Serializable {

    @Schema(description = "Agency")
    private String agency;
    @Schema(description = "Client guid")
    private String guid;
    @Schema(description = "First name")
    private String firstName;
    @Schema(description = "Last name")
    private String lastName;
    @Schema(description = "Client status")
    private PatientStatusEnum status;
    @Schema(description = "Client Date of Birth", format = "yyyy-MM-dd", example = "1920-05-25")
    private LocalDate dob;
    @Schema(description = "Client creation date", format = "yyyy-MM-dd HH:mm:ss", example = "2000-10-20 03:10:24")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;
}
