package com.learn.TaskMaster.service;

import com.learn.TaskMaster.entity.User;
import com.learn.TaskMaster.entity.UserDTO;
import com.learn.TaskMaster.entity.VerificationToken;
import com.learn.TaskMaster.repository.TokenRepository;
import com.learn.TaskMaster.repository.UserRepository;
import com.learn.TaskMaster.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;


    public User registerUser(UserDTO userDTO) {
        User user = new User();

        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(false);
        user.setRole("ADMIN");
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("User Not Found");
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), java.util.Collections.emptyList());
    }

    public void persistRegistrationToken(User registeredUser, String generatedToken) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(registeredUser);
        verificationToken.setToken(generatedToken);
        verificationToken.setExpiryDate(new java.util.Date(System.currentTimeMillis() + 24*60*60*1000));
        tokenRepository.save(verificationToken);
    }

    public boolean verifyRegistrationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken==null)
            return false;
        // check the expiry date
        long registeredExpiryDate = verificationToken.getExpiryDate().getTime();
        if (registeredExpiryDate < System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

    public void enableUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) return;

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
    }

    public String signinUser(UserDTO userDTO) {
        String username = userDTO.getUserName();
        String password = userDTO.getPassword();

        // check if user is registered or not
        User registeredUser = userRepository.findByUserName(username);
        if (registeredUser == null) {
            return "User is not registered in the system!!";
        }

        // Check if user is enabled
        if (!registeredUser.isEnabled()) {
            return "User is registered but not yet enabled. Please complete the verification";
        }

        // Verify the password
        boolean isPasswordMatch = passwordEncoder.matches(password, registeredUser.getPassword());
        if (!isPasswordMatch) {
            return "Invalid credentials!";
        }

        // Everything is good... generate JWT token
        return JwtUtil.generateToken(username, registeredUser.getRole());
    }
}
