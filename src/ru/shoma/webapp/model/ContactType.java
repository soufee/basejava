package ru.shoma.webapp.model;

/**
 * Created by Shoma on 04.03.2018.
 */
public enum ContactType {
    ADRESS ("Адрес"),
    CELLPHONE("Сотовый телефон"),
    HOMEPHONE("Домашний телефон"),
    WORKPHONE("Рабочий телефон"),
    EMAIL("E-mail"),
    SKYPE("Скайп"),
    SOCNETWORKS("Страницы в соц.сетях");

    private String title;

    public String getTitle() {
        return title;
    }

    ContactType(String title) {
     this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
