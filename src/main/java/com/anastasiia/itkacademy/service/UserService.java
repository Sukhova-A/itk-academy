package com.anastasiia.itkacademy.service;

import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.model.User;
import com.anastasiia.itkacademy.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found", id)));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public User updateById(UUID id, User updatedUser) {
        User existing = findById(id);
        existing.setName(updatedUser.getName());
        existing.setMail(updatedUser.getMail());
        return userRepository.save(existing);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}