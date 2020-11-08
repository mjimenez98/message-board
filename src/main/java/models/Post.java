package models;

import java.time.LocalDateTime;

public class Post {
    private final String title;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String message;

    public Post(String title, String username, LocalDateTime createdAt, LocalDateTime updatedAt, String message) {
        this.title = title;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
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