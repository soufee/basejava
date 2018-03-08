package ru.shoma.webapp.model;

/**
 * Created by Shoma on 04.03.2018.
 */
public class TextSection extends Section {
    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
