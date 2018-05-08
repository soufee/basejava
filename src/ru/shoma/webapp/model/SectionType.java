package ru.shoma.webapp.model;

/**
 * Created by Shoma on 01.03.2018.
 */
public enum SectionType {
    PERSONAL ("Личные качества"),
    OBJECTIVE ("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
