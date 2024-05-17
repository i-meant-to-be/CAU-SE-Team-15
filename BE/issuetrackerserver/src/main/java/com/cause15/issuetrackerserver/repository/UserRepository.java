package com.cause15.issuetrackerserver.repository;
import com.cause15.issuetrackerserver.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
