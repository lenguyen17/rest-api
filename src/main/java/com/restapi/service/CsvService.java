package com.restapi.service;

import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.manager.CsvEntityManager;
import com.restapi.dto.UserCsvEntity;
import com.restapi.entity.User;
import com.restapi.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CsvService {
    @Autowired
    private UserRepository userRepository;

    public CsvService() {
    }

    public List<User> importFile(File file) throws IOException {
        List<User> listUsers = new ArrayList<>();

        CsvConfig cfg = new CsvConfig();
        cfg.setNullString("NULL");
        cfg.setIgnoreLeadingWhitespaces(true);
        cfg.setIgnoreTrailingWhitespaces(true);
        cfg.setIgnoreEmptyLines(true);
        cfg.setIgnoreLinePatterns(Pattern.compile("^#.*$"));
        cfg.setVariableColumns(false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        listUsers = new CsvEntityManager()
                .config(cfg)
                .load(UserCsvEntity.class)
                .from(file)
                .stream().map(u ->
                     User.build(u.getUserId(), u.getUsername(), u.getPassword(),
                            u.getFirst_name(), u.getLast_name(), u.getAddress(),
                            LocalDate.parse(u.getBirthday(), formatter), u.getEmail())
                )
                .collect(Collectors.toList());
        log.info("List user size: " + listUsers.size());
        file.delete();
        return userRepository.saveAll(listUsers);
    }
}
