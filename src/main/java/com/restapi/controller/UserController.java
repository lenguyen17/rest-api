package com.restapi.controller;

import com.restapi.dto.UserDTO;
import com.restapi.entity.User;
import com.restapi.exception.UserNotFoundException;
import com.restapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/signup")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id)
            throws UserNotFoundException {
        try{
            int intId = Integer.parseInt(id);
            return ResponseEntity.ok(userService.getUser(intId));
        }catch (NumberFormatException e){
            throw new UserNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id)
            throws UserNotFoundException {
        try {
            Integer intId = Integer.parseInt(id);
            userService.deleteByUserId(intId);
            return new ResponseEntity<>("User with ID " + id + " has been deleted successfully", HttpStatus.OK);
        }catch (NumberFormatException e){
            throw new UserNotFoundException();
        }catch (Exception e) {
            return new ResponseEntity<>("Error deleting user with ID " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
