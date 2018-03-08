package ru.shoma.webapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoma on 04.03.2018.
 */
public class ListSection extends Section {
    private List<String> contents;

    public ListSection() {
        this.contents = new ArrayList<>();
    }

    public void addItem(String item) {
        contents.add(item);
    }

    public void deleteItem(String item) {
        contents.remove(item);
    }

    public List<String> getAll() {
        return contents;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s:contents) {
            sb.append("- "+s+"\n");
        }
        return sb.toString();
    }
}
