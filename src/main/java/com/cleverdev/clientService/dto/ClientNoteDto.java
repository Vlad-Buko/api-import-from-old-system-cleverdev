package com.cleverdev.clientService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by Vladislav Domaniewski
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Client note")
public class ClientNoteDto {
    @JsonProperty("comments")
    private String comments;
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("modifiedDateTime")
    private LocalDateTime modifiedDateTime;
    @JsonProperty("clientGuid")
    private String clientGuid;
    @JsonProperty("createdDateTime")
    private LocalDateTime createdDateTime;
    @JsonProperty("loggedUser")
    private String loggedUser;
}
