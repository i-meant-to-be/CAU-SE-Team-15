package com.cause15.issuetrackerserver.repository;
import com.cause15.issuetrackerserver.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {
    List<User> getByName(String name);
}
