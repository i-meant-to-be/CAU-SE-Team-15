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
    private ArrayList<Issue> issues;
    private List<UUID> users;
    private LocalDateTime creationDate;
    private String description;

    public Project(String name, String description, List<UUID> users) {
        this.creationDate = LocalDateTime.now();
        this.id = UUID.randomUUID();
        this.name = name;
        this.issues = new ArrayList<>();
        this.users = users;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void addUser(UUID user) {
        users.add(user);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsers(List<UUID> users) {
        this.users = users;
    }


    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
    }
}