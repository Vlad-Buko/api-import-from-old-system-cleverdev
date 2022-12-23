package com.cleverdev.clientService.api.service;

import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.exceptions.UserAlreadyExistException;
import com.cleverdev.clientService.repository.UserRepository;

import com.cleverdev.clientService.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Created by Vladislav Domaniewski
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void initialDataForTestUser() {

    }

    @Test
    public void getByIdTest() {
        User userExpected = new User();
        userExpected.setId(2l);
        userExpected.setLogin("Vasya");
        given(this.userRepository.getById(any())).willReturn(userExpected);

        User user = userService.getUser(2l);
        assertThat(user.getId()).isEqualTo(2l);
    }

    @Test
    @Ignore
    public void checkAddedUserTest() throws IOException {
//        try {

//        } catch (User)
        Throwable thrown = catchThrowable(() -> {
            User user = new User();
            user.setLogin("Vladislav");
            userRepository.save(user);
            userService.addUser(new UserDto("Vladislav"));
        });
        assertThat(thrown).isInstanceOf(UserAlreadyExistException.class);
        assertThat(thrown.getMessage()).isNotBlank();
    }
}
