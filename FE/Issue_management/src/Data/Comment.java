package Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private UUID id;
    LocalDateTime created_date;
    UUID created_user;
    String body;
}

