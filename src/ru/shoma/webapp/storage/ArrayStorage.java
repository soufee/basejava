package ru.shoma.webapp.storage;

import ru.shoma.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deletedElement(int index) {
       storage[index] = storage[size-1];
    }

    @Override
    protected void insertElement(Resume r, int index) {
       storage[size] = r;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
