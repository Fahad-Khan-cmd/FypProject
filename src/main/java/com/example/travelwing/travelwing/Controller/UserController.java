package com.example.travelwing.travelwing.Controller;


import com.example.travelwing.travelwing.Domain.User;
import com.example.travelwing.travelwing.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/createUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createUser(@RequestBody User user)
    {
        userService.insertUsers(user);
        return ResponseEntity.ok("User Added Successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers()
    {
//        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(userService.getAllUsers());
    }
}

