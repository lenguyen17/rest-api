package com.restapi.service;

import com.restapi.dto.UserDTO;
import com.restapi.entity.User;
import com.restapi.exception.UserNotFoundException;
import com.restapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(UserDTO userDTO) {
        User user = User.build(0, userDTO.getUsername(), userDTO.getPassword(),
                userDTO.getFirst_name(), userDTO.getLast_name(), userDTO.getAddress());
        log.info("ADD_NEW_USER: Added 1 user");
        return userRepository.save(user);
    }

    public User getUser(Integer id) throws UserNotFoundException {
        return userRepository.findByUserId(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void deleteByUserId(Integer id){
        userRepository.deleteByUserId(id);
    }
}
