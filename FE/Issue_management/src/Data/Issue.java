package Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class Issue {
    private UUID id;
    private String title;
    private String reporter_id;
    private LocalDateTime reported_date;
    private String description;
    private UUID assignee_id;
    private IssueType type;
    private IssueState state;
    private String[] comments; // comment기능 구현을 위한 변수 UUID 대신 문자열 배열로 변경

    public Issue(String title, String reporter_id, LocalDateTime reported_date, String description, UUID assignee_id, IssueType type, IssueState state, String[] comments) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.reporter_id = reporter_id;
        this.reported_date = reported_date;
        this.description = description;
        this.assignee_id = assignee_id;
        this.type = type;
        this.state = state;
        this.comments = comments;
    }

    // Getter 메서드들

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReporterId() {
        return reporter_id;
    }

    public LocalDateTime getReportedDate() {
        return reported_date;
    }

    public String getDescription() {
        return description;
    }

    public UUID getAssigneeId() {
        return assignee_id;
    }

    public IssueType getType() {
        return type;
    }

    public IssueState getState() {
        return state;
    }

    public String[] getComments() {
        return comments;
    }

    // 코멘트 추가 메서드
    public void addComment(String comment) {
        if (comments == null) {
            comments = new String[1]; // 코멘트 배열이 비어있을 경우 크기 1로 초기화
            comments[0] = comment;
        } else {
            String[] newComments = new String[comments.length + 1]; // 이전 코멘트 배열보다 크기가 1 큰 새로운 배열 생성
            System.arraycopy(comments, 0, newComments, 0, comments.length); // 이전 코멘트 배열의 내용을 새로운 배열로 복사
            newComments[comments.length] = comment; // 마지막 원소로 새로운 코멘트 추가
            comments = newComments; // 새로운 배열로 참조 업데이트
        }
    }
}
