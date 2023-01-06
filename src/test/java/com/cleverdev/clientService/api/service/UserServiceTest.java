package com.cleverdev.clientService.api.service;

import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.exceptions.UserAlreadyExistException;
import com.cleverdev.clientService.repository.UserRepository;

import com.cleverdev.clientService.service.UserService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;

/**
 * Created by Vladislav Domaniewski
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @Before
    public void setup () {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUserById() {
        List<User> listUserTest = initUserForRepo();

        Mockito.when(userRepository.getById(1l)).thenReturn(listUserTest.get(0));
        User expected = listUserTest.get(0);
        User result = userService.getUser(1l);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void deleteUserFromDb() {
        List<User> listUserTest = initUserForRepo();

        Mockito.when(userRepository.findByLogin("Mike")).thenReturn(listUserTest.get(0));
        User usere = userService.deleteUser("Mike");
        Assertions.assertNull(usere);
    }

    private List<User> initUserForRepo() {
        User userFirst = new User();
        User userSecond = new User();
        User userThirty = new User();
        User userForty = new User();

        userFirst.setId(1l);
        userFirst.setLogin("Mike");
        userFirst.setListNote(null);
        userFirst.setList(null);

        userSecond.setId(2l);
        userSecond.setLogin("Grommew");
        userSecond.setListNote(null);
        userSecond.setList(null);

        userThirty.setId(3l);
        userThirty.setLogin("Misha");
        userThirty.setListNote(null);
        userThirty.setList(null);

        userForty.setId(4l);
        userForty.setLogin("Viktor Moses");
        userForty.setListNote(null);
        userForty.setList(null);

        return List.of(userFirst, userSecond, userThirty, userForty);
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
