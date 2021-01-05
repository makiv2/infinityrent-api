package com.infinityrent.api.API.service;

import com.infinityrent.api.API.model.Role;
import com.infinityrent.api.API.model.RoleType;
import com.infinityrent.api.API.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(RoleType roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role not found")); //TODO: Should exception be thrown if not found?
    }
}
