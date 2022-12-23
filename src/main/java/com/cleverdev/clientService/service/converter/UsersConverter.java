package com.cleverdev.clientService.service.converter;

import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.entity.User;
import org.springframework.stereotype.Component;

/**
 * Created by Vladislav Domaniewski
 */

@Component
public class UsersConverter {
    public User fromUserDtoToUser(UserDto userDto) {
        return User.builder()
                .login(userDto.getLogin())
                .build();
    }

//    public UserDto fromUserToUserDto(User user) {
//        return UserDto.builder()
//                .login(user.getLogin())
//                .build();
//    }
}
