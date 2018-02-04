package ru.shoma.webapp.model;

/**
 * com.urise.webapp.model.ru.shoma.webapp.model.Resume class
 */
public class Resume {
    // Unique identifier
    private String uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return uuid;
    }
}
