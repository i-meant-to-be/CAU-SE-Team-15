package Data;

import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String password;
    private UserType type;

    public User() {
        this.username = "Anonymous";
    }

    public User(String username, String password, UserType type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(UUID id, String username, String password, UserType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
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
    public UserType getType() { return type; }

    //서버와 연결 성공하면 나중에 지워주세요.
    public void setUUID(UUID id) {
        this.id = id;
    }
}
