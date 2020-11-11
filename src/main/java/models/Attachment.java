package models;

import java.io.InputStream;

public class Attachment {
    private final int attachmentId;
    private final int postId;
    private final int size;
    private final InputStream file;
    private final String name;
    private final String type;

    public Attachment(int attachmentId, int postId, int size, InputStream file, String name, String type) {
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

    public InputStream getFile() {
        return file;
    }

    public String getFilename() {
        return name;
    }

    public String getContentType() {
        return type;
    }
}
