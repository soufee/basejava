package ru.shoma.webapp.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contacts = new HashMap<>();
    private Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(fullName, "fullName must not be null or empty");
        Objects.requireNonNull(uuid, "uuid must not be null or empty");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    public void addContact(ContactType ct, String item){
        contacts.put(ct, item);
    }

    public void addSectionItem(SectionType ct, Section s){
        sections.put(ct, s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (contacts != null ? !contacts.equals(resume.contacts) : resume.contacts != null) return false;
        return sections != null ? sections.equals(resume.sections) : resume.sections == null;
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return uuid + " : " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int nameCmp = this.fullName.compareTo(o.getFullName());
        return nameCmp==0 ? this.uuid.compareTo(o.getUuid()):nameCmp;
    }

}
