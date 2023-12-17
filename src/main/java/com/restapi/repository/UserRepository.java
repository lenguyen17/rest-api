package com.restapi.repository;

import com.restapi.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(Integer id);

    @Transactional
    void deleteByUserId(Integer id);

}
