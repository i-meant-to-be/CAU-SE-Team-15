package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Comment;
import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    // Dependency injection
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // Methods
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(UUID id) {
        return commentRepository.findById(id).orElse(null);

    }
    public boolean deleteComment(UUID id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
