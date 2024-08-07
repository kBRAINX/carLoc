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
    private final UserRepository userRepository;
    private final CassandraIdGenerator cassandraIdGenerator;

    @Autowired
    public UserService(UserRepository userRepository, CassandraIdGenerator cassandraIdGenerator) {
        this.userRepository = userRepository;
        this.cassandraIdGenerator = cassandraIdGenerator;
    }

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

    public Optional<User> login(UserDTO loginDto) {
        Optional<User> userOpt = userRepository.findByEmail(loginDto.getEmail());
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(user.getPassword().equals(loginDto.getPassword())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
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

