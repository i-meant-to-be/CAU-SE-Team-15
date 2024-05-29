package Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private UUID id;
    private String text;
    private LocalDateTime timestamp;
    private UUID authorId;

    public Comment(String text) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.timestamp = LocalDateTime.now().withNano(0);
    }

    public Comment(UUID id, String text, LocalDateTime timestamp, UUID authorId) {
        this.text = text;
        this.timestamp = timestamp;
        this.id = id;
        this.authorId = authorId;
    }

    public UUID getAuthorId(){
        return authorId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return text + ": " + timestamp; //댓글 출력할 때 댓글 입력한 시간도 같이 표시
    }
}
