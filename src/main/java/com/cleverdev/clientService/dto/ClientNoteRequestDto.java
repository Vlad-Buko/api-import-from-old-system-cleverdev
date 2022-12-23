package com.cleverdev.clientService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by Vladislav Domaniewski
 */

@Data
@NoArgsConstructor
public class ClientNoteRequestDto {
    @JsonProperty("agency")
    private String agency;
    @JsonProperty("dateFrom")
    private LocalDate dateFrom;
    @JsonProperty("dateTo")
    private LocalDate dateTo;
    @JsonProperty("clientGuid")
    private String clientGuid;
}
