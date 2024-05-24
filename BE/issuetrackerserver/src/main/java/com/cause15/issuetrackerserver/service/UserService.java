package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.repository.UserRepository;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final MongoClient mongo;

    public UserService(UserRepository userRepository, MongoOperations mongoOperations, MongoClient mongo) {
        this.userRepository = userRepository;
        this.mongoOperations = mongoOperations;
        this.mongo = mongo;
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
        return false;
    }

    public User getUserByUsername(String username) {
        return mongoOperations.findOne(
                Query.query(Criteria.where("username").is(username)),
                User.class
        );
    }
}