package ru.shoma.webapp.storage;

import ru.shoma.webapp.model.Resume;

/**
 * Created by Shoma on 04.02.2018.
 */
public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}
