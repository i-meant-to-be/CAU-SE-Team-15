package com.cause15.issuetrackerserver.repository;

import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.model.IssueState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ComponentScan(basePackages = "com.cause15.issuetrackerserver")
public class IssueRepositoryTest {

    @Autowired
    private IssueRepository issueRepository;

    private Issue issue1;
    private Issue issue2;

    @BeforeEach
    public void setUp() {
        issueRepository.deleteAll();

        issue1 = new Issue();
        issue1.setId(UUID.randomUUID());
        issue1.setTitle("Driver");
        issue1.setState(IssueState.NEW);
        issue1.setReporterId(UUID.randomUUID());
        issue1.setAssigneeId(UUID.randomUUID());
        issue1.setFixerId(UUID.randomUUID());

        issueRepository.save(issue1);
    }

    @Test
    public void testExistsByTitle() {
        boolean exists = issueRepository.existsByTitle("Driver");
        assertThat(exists).isTrue();
    }

    @Test
    public void testFindAllByTitle() {
        List<Issue> issues = issueRepository.findAllByTitle("Driver");
        assertThat(issues).hasSize(1);
    }

    @Test
    public void testExistsByState() {
        boolean exists = issueRepository.existsByState(IssueState.NEW);
        assertThat(exists).isTrue();
    }

    @Test
    public void testFindAllByState() {
        List<Issue> openIssues = issueRepository.findAllByState(IssueState.NEW);

        assertThat(openIssues).hasSize(1);
    }

    @Test
    public void testFindAllByReporterId() {
        List<Issue> issues = issueRepository.findAllByReporterId(issue1.getReporterId());
        assertThat(issues).hasSize(1);

    }


    @Test
    public void testFindAllByTitleAndState() {
        List<Issue> issues = issueRepository.findAllByTitleAndState("Driver", IssueState.NEW);
        assertThat(issues).hasSize(1);
    }
}
