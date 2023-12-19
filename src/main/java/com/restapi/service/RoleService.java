package com.restapi.service;

import com.restapi.dto.RoleDTO;
import com.restapi.entity.Role;
import com.restapi.exception.RoleException;
import com.restapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public RoleService() {
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role getRole(Integer id) throws RoleException {
        return roleRepository.findById(id)
                .orElseThrow(() ->new RoleException("Id is not exists"));
    }

    public Role saveRole(RoleDTO roleDTO) throws RoleException {
        if(roleRepository.existsByName(roleDTO.getName())){
            throw new RoleException("Role already exists");
        }
        Role role = Role.build(0, roleDTO.getName());
        return roleRepository.save(role);
    }

    public void deleteById(Integer id){
        roleRepository.deleteById(id);
    }

    public Role updateRole(Integer id, RoleDTO roleDTO) throws RoleException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() ->  new RoleException("Role not found"));
        role.setName(roleDTO.getName());
        return roleRepository.save(role);
    }

}
