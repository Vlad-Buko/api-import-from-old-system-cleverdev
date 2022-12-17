package com.cleverdev.clientService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Vladislav Domaniewski
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String login;
}
