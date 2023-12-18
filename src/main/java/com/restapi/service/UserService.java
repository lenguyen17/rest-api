package com.restapi.service;

import com.restapi.dto.UserDTO;
import com.restapi.entity.User;
import com.restapi.exception.UserAlreadyExistsException;
import com.restapi.exception.UserNotFoundException;
import com.restapi.repository.UserRepository;
import com.restapi.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailUtil emailUtil;

    public UserService() {
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        User user = User.build(0, userDTO.getUsername(), userDTO.getPassword(),
                userDTO.getFirst_name(), userDTO.getLast_name(), userDTO.getAddress(), userDTO.getBirthday(), userDTO.getEmail());
        log.info("ADD_NEW_USER: Added 1 user");
        return userRepository.save(user);
    }

    public void saveAllUser(List<User> userList) {
        userRepository.saveAll(userList);
    }

    public User updateUser(Integer id, UserDTO userDTO) throws UserNotFoundException {
        User existingUser = userRepository.findByUserId(id)
                .orElseThrow(UserNotFoundException::new);
        modelMapper.map(userDTO, existingUser);
        return userRepository.save(existingUser);
    }

    public User getUser(Integer id) throws UserNotFoundException {
        return userRepository.findByUserId(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public void deleteByUserId(Integer id){
        userRepository.deleteByUserId(id);
    }

    public List<User> getUsersByBirthday(LocalDate birthday){
        return userRepository.findByBirthday(birthday);
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void sendBirthdayEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<User> usersWithBirthday = getUsersByBirthday(tomorrow);
        log.info("Send happy birthday email to " + usersWithBirthday.size() +" client");
        for (User user : usersWithBirthday) {
            String content = emailUtil.getTemplateBirthday(user.getFirst_name() + " " + user.getLast_name());
            emailUtil.sendSimpleMessage(user.getEmail(), "Happy Birthday!",content);
        }
    }
}
