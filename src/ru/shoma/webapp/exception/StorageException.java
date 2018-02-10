package ru.shoma.webapp.exception;

/**
 * Created by Shoma on 09.02.2018.
 */
public class StorageException extends RuntimeException {
    private final String uuid;


    public StorageException(String uuid, String msg) {
        super(msg);
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
