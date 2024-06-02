package com.cause15.issuetrackerserver.repository;
import com.cause15.issuetrackerserver.model.Issue;
import com.cause15.issuetrackerserver.model.IssueState;
import com.cause15.issuetrackerserver.model.IssueType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class IssueRepositoryTest {

    @Autowired
    private IssueRepository issueRepository;

    private Issue issue1;
    private Issue issue2;
    //각 테스트 시작 전 setup함수
    //새로운 issue 생성
    @BeforeEach
    public void setUp() {
        // CREATE
        if(issueRepository.findAllByTitle("TestIssue1").isEmpty()) {
            issue1 = new Issue();
            issue1.setId(UUID.randomUUID());
            issue1.setTitle("TestIssue1");
            issue1.setType(IssueType.BLOCKER);
            issue1.setDescription("Description1");
            issue1.setState(IssueState.REOPENED);
            issue1.setReporterId(UUID.randomUUID());
            issue1.setAssigneeId(UUID.randomUUID());
            issue1.setFixerId(UUID.randomUUID());
            issueRepository.save(issue1);
        }else{
            List<Issue> issues=issueRepository.findAllByTitle("TestIssue1");
            issue1=issues.get(0);
        }

        if(issueRepository.findAllByTitle("TestIssue2").isEmpty()) {
            issue2 = new Issue();
            issue2.setId(UUID.randomUUID());
            issue2.setTitle("TestIssue2");
            issue2.setType(IssueType.MINOR);
            issue2.setDescription("Description2");
            issue2.setState(IssueState.REOPENED);
            issue2.setReporterId(UUID.randomUUID());
            issue2.setAssigneeId(UUID.randomUUID());
            issue2.setFixerId(UUID.randomUUID());
            issueRepository.save(issue2);
        }else{
            List<Issue> issues=issueRepository.findAllByTitle("TestIssue2");
            issue1=issues.get(0);
        }
    }
    //Issue Title을 바탕으로 Issue를 탐색
    @Test
    public void testFindAllByTitle(){
        List<Issue> issues = issueRepository.findAllByTitle("TestIssue1");
        assertThat(issues).isNotNull();
        assertThat(issues).hasSize(1);

        List<Issue> issues2 = issueRepository.findAllByTitle("TestIssue2");

        issueRepository.deleteAll(issues2);
    }
    //Issue State를 바탕으로 Issue를 탐색
    //현재 REOPEND된 issue는 2개이므로 assertthat 2를 출력
    @Test
    public void testFindAllByState(){
        List<Issue> issues = issueRepository.findAllByState(IssueState.REOPENED);
        assertThat(issues).isNotNull();
        assertThat(issues).hasSize(2);

        issueRepository.deleteAll(issues);
    }
    //Issue의 ReporterID를 바탕으로 issues를 탐색
    @Test
    public void testFindAllByReporterId(){
        List<Issue> issues = issueRepository.findAllByReporterId(issue1.getReporterId());
        assertThat(issues).isNotNull();
        assertThat(issues).hasSize(1);
        issueRepository.deleteAll(issues);

        List<Issue> issues2 = issueRepository.findAllByReporterId(issue2.getReporterId());
        assertThat(issues2).isNotNull();
        assertThat(issues2).hasSize(1);
        issueRepository.deleteAll(issues2);
    }

    // DELETE
    @Test
    public void testDeleteIssue() {
        List<Issue> issues = issueRepository.findAllByTitle("TestIssue1");
        assertThat(issues).isNotNull();
        assertThat(issues).hasSize(1);
        issueRepository.deleteAll(issues);

        List<Issue> deletedIssue = issueRepository.findAllByTitle("TestIssue1");
        assertThat(deletedIssue).isEmpty();

        List<Issue> issues2 = issueRepository.findAllByTitle("TestIssue2");
        assertThat(issues2).isNotNull();
        assertThat(issues2).hasSize(1);
        issueRepository.deleteAll(issues2);

        List<Issue> deletedIssue2 = issueRepository.findAllByTitle("TestIssue2");
        assertThat(deletedIssue2).isEmpty();
    }
    // New Tests
    @Test
    public void testFindAllByAssigneeIdOrFixerId() {
        List<Issue> issues = issueRepository.findAllByAssigneeIdOrFixerId(issue1.getAssigneeId(), issue1.getFixerId());
        assertThat(issues).isNotNull();
        assertThat(issues).hasSize(1);
        issueRepository.deleteAll(issues);

        List<Issue> issues2 = issueRepository.findAllByAssigneeIdOrFixerId(issue2.getAssigneeId(), issue2.getFixerId());
        assertThat(issues2).isNotNull();
        assertThat(issues2).hasSize(1);
        issueRepository.deleteAll(issues2);

    }

    @Test
    public void testFindAllByTitleAndState() {
        List<Issue> issues = issueRepository.findAllByTitleAndState("TestIssue1",IssueState.REOPENED);
        assertThat(issues).isNotNull();
        assertThat(issues).hasSize(1);

        List<Issue> issues2 = issueRepository.findAllByTitleAndState("TestIssue2",IssueState.REOPENED);
        assertThat(issues).isNotNull();
        assertThat(issues).hasSize(1);

        issueRepository.deleteAll(issues);
        issueRepository.deleteAll(issues2);
    }
}
