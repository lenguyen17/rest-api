package com.restapi.repository;

import com.restapi.dto.RoleDTO;
import com.restapi.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Transactional
    void deleteById(Integer id);

    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}
