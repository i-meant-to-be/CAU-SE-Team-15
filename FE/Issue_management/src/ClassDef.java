import java.time.LocalDateTime;
import java.util.UUID;

public class ClassDef {
    public enum UserType {
        Admin, Manager, Tester, Dev
    }
    public enum IssueType {
        BLOCKER, CRITICAL, MAJOR, MINOR, TRIVIAL;
    }
    public enum IssueState {
        NEW, ASSIGNED, FIXED, RESOLVED, CLOSED, REOPENED;
    }

    public class User {
        private UUID id;
        private String username;
        private String password;
        UserType type;

        User()
        {
            this.username = "Anonymous";
        }

        public void setUser(String username, String password, UserType type) {
            this.username = username;
            this.password = password;
            this.type = type;
        }

        public UUID getUUID() {
            return id;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }
    public class Issue {
        private UUID id;

        String title;
        String reporter_id;
        LocalDateTime reported_date;
        String description;
        UUID assignee_id;
        IssueType type;
        IssueState state;
        UUID[] comments;
    }
    public class Comment {
        private UUID id;

        LocalDateTime created_date;
        UUID created_user;
        String body;
    }
}
