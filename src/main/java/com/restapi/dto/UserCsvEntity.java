package com.restapi.dto;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@CsvEntity(header = true)
@Data
public class UserCsvEntity {
    @Id
    @GeneratedValue
    @CsvColumn(name = "UserID")
    private Integer userId;

    @CsvColumn(name = "Username")
    private String username;

    @CsvColumn(name = "Password")
    private String password;

    @CsvColumn(name = "First_name")
    private String first_name;

    @CsvColumn(name = "Last_name")
    private String last_name;

    @CsvColumn(name = "Address")
    private String address;

    // Add birthday and email as required
    @CsvColumn(name = "Birthday")
    private String birthday;

    @CsvColumn(name = "Email")
    private String email;
}