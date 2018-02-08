package ru.shoma.webapp.storage;

import ru.shoma.webapp.model.Resume;

import java.util.Arrays;
import java.util.Random;


public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            storage[index] = r;
            if (size>1)  Arrays.sort(storage);
        }
    }

    @Override
    public void save(Resume r) {
        if (getIndex(r.getUuid()) >=0) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        } else if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            storage[size] = r;
            size++;
          if (size>1)
              Arrays.sort(storage);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >=0) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            if (size>1)  Arrays.sort(storage);
        }
    }


    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}