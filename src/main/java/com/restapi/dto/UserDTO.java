package com.restapi.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserDTO{
    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 6, max = 30, message = "Username must be at least 6 and no more than 30 characters")
    @Column(unique = true)
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 30, message = "Password must be at least 6 and no more than 30 characters")
    private String password;

    @NotNull(message = "First name cannot be null")
    @NotBlank(message = "First name cannot be blank")
    private String first_name;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    private String last_name;

    @NotNull(message = "Last name cannot be null")
    @NotBlank(message = "Last name cannot be blank")
    private String address;

    // Add birthday and email as required
    @NotNull(message = "Birthday cannot be null")
    @Past(message = "Birthday must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;
}
