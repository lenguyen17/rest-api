package com.restapi;

import com.restapi.entity.User;
import com.restapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository repository) {
		return args -> {
			for (int i = 1; i <= 10; i++) {
				repository.save(User.build(i, "username_" + i, "password" + i, "First" + i,
						"Last_" + i, "Address_" + i)
				);
			}
		};
	}

}
