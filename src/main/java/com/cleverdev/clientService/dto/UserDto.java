package com.cleverdev.clientService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Created by Vladislav Domaniewski
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @Size(min = 3, max = 20, message = "Login in length 3-20")
    private String login;
}
