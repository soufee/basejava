package ru.shoma.webapp.exception;

public class StorageException extends RuntimeException {
    private final String uuid;

    public StorageException(String msg, String uuid, Exception e) {
        super(msg, e);
        this.uuid = uuid;
    }

    public StorageException(String msg, Exception e) {
        this(null, msg, e);
    }

    public StorageException(String msg, String uuid) {
        super(msg);
        this.uuid = uuid;
    }

    public StorageException(String msg) {
        this(msg, null, null);
    }

    public String getUuid() {
        return uuid;
    }
}
