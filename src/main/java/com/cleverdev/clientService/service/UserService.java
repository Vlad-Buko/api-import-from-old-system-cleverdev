package com.cleverdev.clientService.service;

import com.cleverdev.clientService.exceptions.UserAlreadyExistException;
import com.cleverdev.clientService.exceptions.UserNotFoundException;
import com.cleverdev.clientService.service.converter.UsersConverter;
import com.cleverdev.clientService.dto.UserDto;
import com.cleverdev.clientService.repository.UserRepository;
import com.cleverdev.clientService.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Vladislav Domaniewski
 */

@Service
@Log
public class UserService {
    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    private final UserRepository userRepo;
    public  UsersConverter userConverter;

    public User addUser(UserDto userGet) throws UserAlreadyExistException {
        if ((userRepo.findByLogin(userConverter.fromUserDtoToUser(userGet).getLogin())) == null) {
            return userRepo.save(userConverter.fromUserDtoToUser(userGet));
        } else {
            throw new UserAlreadyExistException("This login already! Please choose another login!");
        }
    }

    public User getUser(Long id) {
        return userRepo.getById(id);
    }

    public void updateUser(UserDto userDto, Long idUser) throws UserNotFoundException {
        if (userRepo.findById(idUser).isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = userRepo.getById(idUser);
        user.setLogin(userDto.getLogin());
        userRepo.save(user);
    }

    public boolean deleteUser(String login) {
        User user = userRepo.findByLogin(login);
        if (userRepo.findByLogin(login) != null) {
            userRepo.deleteById(user.getId());
            return true;
        } else {
            return false;
        }
    }

    public void saveUserFromOldVersionInNew(String loginUser) {
        UserDto userDto = new UserDto();
        userDto.setLogin(loginUser);
        userRepo.save(userConverter.fromUserDtoToUser(userDto));
    }
}
