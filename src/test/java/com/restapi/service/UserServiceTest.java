package com.restapi.service;

import com.restapi.entity.User;
import com.restapi.repository.UserRepository;
import com.restapi.util.EmailUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailUtil emailUtil;

    @Test
    void getUsersByBirthday(){
        LocalDate today = LocalDate.now();
        User userWithBirthday = User.build(1, "user1","123123", "John",
                "Wick", "HCM",today, "theloveneverdie1997@gmail.com");
        List<User> usersWithBirthdayToday = Collections.singletonList(userWithBirthday);
        when(userRepository.findByBirthday(today)).thenReturn(usersWithBirthdayToday);

        assertEquals(userService.getUsersByBirthday(today).size(), 1);

        verify(userRepository).findByBirthday(today);
    }

    // Write email test later
    @Test
    void sendBirthdayEmails() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        User userWithBirthday = User.build(1, "user1","123123", "John",
                "Wick", "HCM",tomorrow, "theloveneverdie1997@gmail.com");

        List<User> usersWithBirthdayTomorrow = Collections.singletonList(userWithBirthday);

        when(userRepository.findByBirthday(tomorrow)).thenReturn(usersWithBirthdayTomorrow);
        when(emailUtil.sendSimpleMessage("theloveneverdie1997@gmail.com","Happy birthday","Test"))
                .thenReturn("success");
        when(emailUtil.getTemplateBirthday("John Wick"))
                .thenReturn("Test");
        userService.sendBirthdayEmails();

        verify(userRepository, times(1)).findByBirthday(tomorrow);

    }


}