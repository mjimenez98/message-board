package models;

import java.time.LocalDateTime;

public class Post {
    private final int postId;
    private final String title;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String message;

    private Attachment attachment;

    public Post(int postId, String title, String username, LocalDateTime createdAt, LocalDateTime updatedAt, String message) {
        this.postId = postId;
        this.title = title;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
        this.attachment = null;
    }

    public int getPostId() { return postId; }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getMessage() {
        return message;
    }
}
