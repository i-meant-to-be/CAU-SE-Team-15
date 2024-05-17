package com.cause15.issuetrackerserver.repository;

import com.cause15.issuetrackerserver.model.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IssueRepository extends MongoRepository<Issue, UUID> {
}
