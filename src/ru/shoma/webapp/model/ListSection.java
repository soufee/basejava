package ru.shoma.webapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shoma on 04.03.2018.
 */
public class ListSection extends Section {
    private List<String> contents = new ArrayList<>();

    public ListSection(List<String> contents) {
        this.contents = contents;
    }

    public void addItem(String item) {
        contents.add(item);
    }

    public void updateItem(String item) {

    }

    public void deleteItem(String item) {

    }

    public List<String> getAll() {
        return contents;
    }


}
