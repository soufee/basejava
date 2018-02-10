package ru.shoma.webapp.storage;

import ru.shoma.webapp.model.Resume;

import java.util.Arrays;


public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deletedElement(int index) {
        int numMove = size - index - 1;
        if (numMove > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMove);
        }
    }

    @Override
    protected void insertElement(Resume r, int index) {
        int indexToInsert = -index - 1;

        System.arraycopy(storage, indexToInsert, storage, indexToInsert + 1, size - indexToInsert);
        storage[indexToInsert] = r;

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}