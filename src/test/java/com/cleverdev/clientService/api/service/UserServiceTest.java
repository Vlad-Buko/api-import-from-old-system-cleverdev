package com.cleverdev.clientService.api.service;

import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.entity.User;
import com.cleverdev.clientService.exceptions.UserAlreadyExistException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
import com.cleverdev.clientService.repository.UserRepository;

import com.cleverdev.clientService.service.UserService;
import com.cleverdev.clientService.service.converter.UsersConverter;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Vladislav Domaniewski
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UsersConverter usersConverter;

    @MockBean
    private UserRepository userRepository;

    @Test(expected = UserAlreadyExistException.class)
    public void waitingForAnUserAlreadyException() throws UserAlreadyExistException {
        List<User> userList = initUserForRepo();
        UserDto userDto = new UserDto();
        userDto.setLogin("Misha");
        User userModel = new User();
        Mockito.when(usersConverter.fromUserDtoToUser(userDto)).thenReturn(userModel);
        Mockito.when(userRepository.findByLogin(usersConverter.
                fromUserDtoToUser(userDto).getLogin()))
                        .thenReturn(userList.get(2));
        userService.addUser(userDto);
    }

    @Test
    public void saveUserInDb() {
        UserDto userDto = new UserDto();
        userDto.setLogin("VLad");
        User userModel = new User();
        Mockito.when(usersConverter.fromUserDtoToUser(userDto)).thenReturn(userModel);
        Mockito.when(userRepository.findByLogin(usersConverter.
                        fromUserDtoToUser(userDto).getLogin()))
                .thenReturn(null);
        try {
            Assertions.assertTrue(userService.addUser(userDto));
        } catch (UserAlreadyExistException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkGetUser() {
        List<User> listUser = initUserForRepo();

        Mockito.when(userRepository.getById(2l)).thenReturn(listUser.get(2));
        User expected = listUser.get(2);
        User actual = userService.getUser(2l);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getUserById() {
        List<User> listUserTest = initUserForRepo();

        Mockito.when(userRepository.getById(1l)).thenReturn(listUserTest.get(0));
        User expected = listUserTest.get(0);
        User result = userService.getUser(1l);
        Assertions.assertEquals(expected, result);
    }

    @Test(expected = UserNotFoundException.class)
    public void catchUserNotFoundException() throws UserNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setLogin("Vlad");
        Mockito.when(userRepository.getById(2l)).thenReturn(null);
        userService.updateUser(userDto, 2l);
    }

    @Test
    public void updateUserInOurDB() {
        List<User> userList = initUserForRepo();
        UserDto userDto = new UserDto();
        userDto.setLogin("VLad");

        Mockito.when(userRepository.getById(2l)).thenReturn(userList.get(2));
        try {
            userService.updateUser(userDto, 2l);
            String expectedName = "Vlad";
            Assertions.assertEquals(expectedName, userList.get(2).getLogin());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteUserFromDb() {
        List<User> listUserTest = initUserForRepo();

        Mockito.when(userRepository.findByLogin("Mike")).thenReturn(listUserTest.get(0));
        User actual = userRepository.findByLogin("Mike");
        Assertions.assertEquals(actual, listUserTest.get(0));
        Assertions.assertTrue(userService.deleteUser("Mike"));
    }

    private List<User> initUserForRepo() {
        User userFirst = new User();
        User userSecond = new User();
        User userThirty = new User();
        User userForty = new User();

        userFirst.setId(1l);
        userFirst.setLogin("Mike");
        userFirst.setListNoteForUserCreated(null);
        userFirst.setListNoteForUserUpdated(null);

        userSecond.setId(2l);
        userSecond.setLogin("Grommew");
        userFirst.setListNoteForUserCreated(null);
        userFirst.setListNoteForUserUpdated(null);

        userThirty.setId(3l);
        userThirty.setLogin("Misha");
        userFirst.setListNoteForUserCreated(null);
        userFirst.setListNoteForUserUpdated(null);

        userForty.setId(4l);
        userForty.setLogin("Viktor Moses");
        userFirst.setListNoteForUserCreated(null);
        userFirst.setListNoteForUserUpdated(null);

        return List.of(userFirst, userSecond, userThirty, userForty);
    }
}
