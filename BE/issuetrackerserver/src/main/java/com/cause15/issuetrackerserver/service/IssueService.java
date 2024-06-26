package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.model.IssueState;
import com.cause15.issuetrackerserver.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService {
    // Dependency injection
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    // Methods
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Optional<Issue> getIssueById(UUID id) {
        return issueRepository.findById(id);
    }

    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue updateIssue(UUID id, Issue issue) {
        if (issueRepository.existsById(id)) {
            issue.setId(id);
            return issueRepository.save(issue);
        }
        else return null;
    }

    public boolean deleteIssue(UUID id) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    //CustomCrud api
    public List<Issue> getIssueByTitle(String title) {
        if (issueRepository.existsByTitle(title)) {
            return issueRepository.findAllByTitle(title);
        }
        else return null;
    }

    public List<Issue> getIssueByState(IssueState state){
        if(issueRepository.existsByState(state)){
            return issueRepository.findAllByState(state);
        }
        return null;
    }

    public List<Issue> getIssueByTitleAndState(String title,IssueState state) {
        return issueRepository.findAllByTitleAndState(title, state);
    }

    public List<Issue> getIssueForTester(UUID testerId) {
        return issueRepository.findAllByReporterId(testerId);
    }

    public List<Issue> getIssueForDeveloper(UUID developerId) {
        return issueRepository.findAllByAssigneeIdOrFixerId(developerId, developerId);
    }
}
