package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueService issueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIssues() {
        issueService.getAllIssues();
        verify(issueRepository, times(1)).findAll();
    }

    @Test
    void testGetIssueById() {
        UUID issueId = UUID.randomUUID();
        Issue issue = new Issue();
        issue.setId(issueId);
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        Issue foundIssue = issueService.getIssueById(issueId);
        assertNotNull(foundIssue);
        assertEquals(issueId, foundIssue.getId());
    }

    @Test
    void testCreateIssue() {
        Issue issue = new Issue();
        issue.setTitle("Sample Issue");
        issue.setDescription("Sample Description");
        when(issueRepository.save(issue)).thenReturn(issue);

        Issue createdIssue = issueService.createIssue(issue);
        assertNotNull(createdIssue);
        assertEquals("Sample Issue", createdIssue.getTitle());
        assertEquals("Sample Description", createdIssue.getDescription());
    }

    @Test
    void testUpdateIssue() {
        UUID issueId = UUID.randomUUID();
        Issue issue = new Issue();
        issue.setId(issueId);
        issue.setTitle("Updated Issue");
        issue.setDescription("Updated Description");

        when(issueRepository.existsById(issueId)).thenReturn(true);
        when(issueRepository.save(issue)).thenReturn(issue);

        Issue updatedIssue = issueService.updateIssue(issueId, issue);
        assertNotNull(updatedIssue);
        assertEquals("Updated Issue", updatedIssue.getTitle());
        assertEquals("Updated Description", updatedIssue.getDescription());
    }

    @Test
    void testDeleteIssue() {
        UUID issueId = UUID.randomUUID();

        when(issueRepository.existsById(issueId)).thenReturn(true);

        boolean result = issueService.deleteIssue(issueId);
        assertTrue(result);
        verify(issueRepository, times(1)).deleteById(issueId);
    }

    @Test
    void testDeleteIssueNotFound() {
        UUID issueId = UUID.randomUUID();

        when(issueRepository.existsById(issueId)).thenReturn(false);

        boolean result = issueService.deleteIssue(issueId);
        assertFalse(result);
        verify(issueRepository, never()).deleteById(issueId);
    }
}
