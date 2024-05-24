package com.cause15.issuetrackerserver.repository;

import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.model.IssueState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueRepository extends MongoRepository<Issue, UUID> {
    boolean existsByTitle(String title);
    List<Issue> findAllByTitle(String title);
    boolean existsByState(IssueState state);
    List<Issue> findAllByState(IssueState state);
    List<Issue> findAllByReporterId(UUID reporterId);
    List<Issue> findAllByAssigneeIdOrFixerId(UUID fixerId, UUID reporterId);
    List<Issue> findAllByTitleAndState(String title, IssueState state);
}
