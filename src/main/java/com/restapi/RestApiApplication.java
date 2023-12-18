package com.restapi;

import com.restapi.entity.User;
import com.restapi.repository.UserRepository;
import com.restapi.service.ExcelService;
import com.restapi.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository repository, ExcelService excelService) {
        return args -> {
            for (int i = 1; i <= 10; i++) {
                LocalDate birthday = LocalDate.parse("2000-01-11");
                birthday = birthday.plusMonths(i);
                birthday = birthday.plusDays(i + 3);
                repository.save(User.build(i, "username_" + i, "password" + i, "First" + i,
                        "Last_" + i, "Address_" + i, birthday, "theloveneverdie1997@gmail.com")
                );
            }
        };
    }

}
