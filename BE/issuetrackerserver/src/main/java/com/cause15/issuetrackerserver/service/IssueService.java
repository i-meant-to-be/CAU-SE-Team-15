package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.model.IssueState;
import com.cause15.issuetrackerserver.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Issue getIssueById(UUID id) {
        return issueRepository.findById(id).orElse(null);
    }

    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue updateIssue(UUID id, Issue issue) {
        if (issueRepository.existsById(id)) {
            issue.setId(id);
            return issueRepository.save(issue);
        }
        return null;
    }

    public boolean deleteIssue(UUID id) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //CustomCrud api
    public List<Issue> getIssueByTitle(String title) {
        if (issueRepository.existsByTitle(title)) {
            return issueRepository.findAllByTitle(title);
        }
        return null;
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
}
