package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Project {
    private UUID id;
    private String name;
    private List<Issue> issues;

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

    public List<Issue> getIssues() {
        return issues;
    }
    @Override
    public String toString() {
        return name;
    }
    public void addIssue(Issue issue) {
        issues.add(issue);
    }
}