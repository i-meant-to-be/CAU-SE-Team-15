package com.cause15.issuetrackerserver.repository;

import com.cause15.issuetrackerserver.model.Project;
import com.cause15.issuetrackerserver.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjectRepository extends MongoRepository<Project, UUID>{
    Project getByTitle(String title);
}
