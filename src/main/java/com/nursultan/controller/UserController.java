package com.nursultan.controller;


import com.nursultan.entity.User;
import com.nursultan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping
    public User addNew(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public Optional getById(@PathVariable int id) {
        return userService.getUserById(id);
    }


    @PutMapping
    public User update(@RequestBody User user) {
        return userService.updateUser(user);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        userService.deleteUserById(id);
    }


    @DeleteMapping
    public void delete(@RequestBody User user) {
        userService.deleteUser(user);
    }



}
