package com.infinityrent.api.API.repository;

import com.infinityrent.api.API.model.Role;
import com.infinityrent.api.API.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
