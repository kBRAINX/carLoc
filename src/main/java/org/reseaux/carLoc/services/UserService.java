package org.reseaux.carLoc.services;

import org.reseaux.carLoc.dto.UserDTO;
import org.reseaux.carLoc.exceptions.ResourceNotFoundException;
import org.reseaux.carLoc.models.User;
import org.reseaux.carLoc.repositories.UserRepository;
import org.reseaux.carLoc.utils.CassandraIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CassandraIdGenerator cassandraIdGenerator;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(long userId) {
        return userRepository.findById(userId);
    }

    public User create(UserDTO userDTO) {
        User user = new User();
        Long newId = cassandraIdGenerator.getNextId("proprietaires");
        user.setId(newId);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setSexe(userDTO.getSexe());
        return userRepository.save(user);
    }

    public User update(long userId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setSexe(userDTO.getSexe());
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
    }

    public void delete(long userId) {
        userRepository.deleteById(userId);
    }
}

