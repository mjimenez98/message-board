package models;

import java.sql.Blob;

public class Attachment {
    private final int attachmentId;
    private final int postId;
    private final int size;
    private final Blob file;
    private final String name;
    private final String type;

    public Attachment(int attachmentId, int postId, int size, Blob file, String name, String type) {
        this.attachmentId = attachmentId;
        this.postId = postId;
        this.size = size;
        this.file = file;
        this.name = name;
        this.type = type;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public int getPostId() {
        return postId;
    }

    public int getSize() {
        return size;
    }

    public Blob getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return type;
    }

    public String printSize() {
        if (size < 1000000)
            return String.format("%.2f", size / 1000.0) + "KB";

        return String.format("%.2f", size / 1000000.0) + "MB";
    }
}
