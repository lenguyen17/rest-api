package com.restapi.repository;

import com.restapi.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(Integer id);

    @Transactional
    void deleteByUserId(Integer id);

    List<User> findByBirthday(LocalDate birthday);

    boolean existsByUsername(String username);
}
