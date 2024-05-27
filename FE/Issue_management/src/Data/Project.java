package Data;

import jdk.jfr.Description;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {
    private UUID id;
    private String name;
    private List<Issue> issues;
    private List<UUID> users;
    private LocalDateTime creationDate;
    private String description;

    public Project(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.issues = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getUsers() {
        return users;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }


    public List<Issue> getIssues() {
        return issues;
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
    }

    @Override
    public String toString() { //이거 지우면 프로젝트 리스트에서 프로젝트 이름 정상적으로 안나오니 지우지 마세요!
        return name;
    }
}