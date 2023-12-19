package com.restapi.controller;

import com.restapi.dto.RoleDTO;
import com.restapi.entity.Role;
import com.restapi.exception.RoleException;
import com.restapi.repository.UserRepository;
import com.restapi.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @GetMapping("/")
    public ResponseEntity<List<Role>> getAll(){
        return  ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable("id") String id) throws RoleException {
        try {
            Integer intId = Integer.parseInt(id);
            return ResponseEntity.ok(roleService.getRole(intId));
        } catch (NumberFormatException e){
            throw new RoleException("ID have to be Integer");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<Role> saveRole(@Valid @RequestBody RoleDTO roleDTO) throws RoleException {
        return new ResponseEntity<>(roleService.saveRole(roleDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") String id) throws RoleException {
        try {
            Integer intId = Integer.parseInt(id);
            roleService.deleteById(intId);
            return new ResponseEntity<>("Role with ID " + id + " has been deleted successfully", HttpStatus.OK);
        } catch (NumberFormatException e){
            throw new RoleException("ID have to be Integer");
        } catch (Exception e){
            return new ResponseEntity<>("Error deleting role with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") String id,
                                           @Valid @RequestBody RoleDTO roleDTO) throws RoleException {
        try {
            Integer intId = Integer.parseInt(id);
            return ResponseEntity.ok(roleService.updateRole(intId, roleDTO));
        } catch (NumberFormatException e){
            throw new RoleException("ID have to be Integer");
        } catch (Exception e){
            throw new RoleException("No exists Role ID: " + id) ;
        }
    }

}
