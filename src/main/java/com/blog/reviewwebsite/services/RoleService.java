package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.controller.Roles;
import com.blog.reviewwebsite.entities.Role;
import com.blog.reviewwebsite.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        createRoleIfDoesNotExist();
    }

    public Role getOneByName(Roles role) {
        return roleRepository.getOneByName(role.name());
    }

    public void createRoleIfDoesNotExist() {
        Roles[] roles = Roles.values();
        for (Roles role : roles) {
            if (getOneByName(role) == null) {
                Role newRole = new Role(role);
                roleRepository.save(newRole);
            }
        }

    }
}
