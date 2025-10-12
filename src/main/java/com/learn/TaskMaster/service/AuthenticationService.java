package com.learn.TaskMaster.service;

import com.learn.TaskMaster.entity.User;
import com.learn.TaskMaster.entity.UserDTO;
import com.learn.TaskMaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        User user = new User();

        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(false);
        user.setRole("ADMIN");
        return userRepository.save(user);
    }
}
