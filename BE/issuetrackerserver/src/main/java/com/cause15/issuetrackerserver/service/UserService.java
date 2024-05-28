package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.repository.UserRepository;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    // Dependency injection
    private final UserRepository userRepository;
    private final MongoOperations mongoOperations;

    public UserService(UserRepository userRepository, MongoOperations mongoOperations) {
        this.userRepository = userRepository;
        this.mongoOperations = mongoOperations;
    }

    // Methods
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(UUID id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public User getUserByName(String name) {
        return mongoOperations.findOne(
                Query.query(Criteria.where("name").is(name)),
                User.class
        );
    }
}