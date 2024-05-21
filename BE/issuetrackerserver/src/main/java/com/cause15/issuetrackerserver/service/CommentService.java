package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Comment;
import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
