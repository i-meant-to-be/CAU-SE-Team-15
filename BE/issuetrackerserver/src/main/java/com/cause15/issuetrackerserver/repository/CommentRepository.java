package com.cause15.issuetrackerserver.repository;

import com.cause15.issuetrackerserver.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CommentRepository extends MongoRepository<Comment, UUID> {

}
