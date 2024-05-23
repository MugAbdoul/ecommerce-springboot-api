package com.abdoul.backend.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abdoul.backend.repository.UserRepository;
import com.abdoul.backend.entities.User;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateUserDetails(User user, String firstname, String lastname, String phoneNumber) {

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPhoneNumber(phoneNumber);

        return userRepository.save(user);
    }

    public User updateProfileImage(User user, String profileImage) {

        user.setProfileImage(profileImage);

        return userRepository.save(user);
    }

    public User changePassword(User user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));

        return userRepository.save(user);
    }

    public boolean checkPassword(User user, String oldPassword) {

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        return true;
    }
}
