package com.nursultan.service;


import com.nursultan.dao.UserRepository;
import com.nursultan.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //read all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //read user by id
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    //createUser
    public User createUser(User user) {
        return userRepository.save(user);
    }
    //delete user
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    //delete by id
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
    //update
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
