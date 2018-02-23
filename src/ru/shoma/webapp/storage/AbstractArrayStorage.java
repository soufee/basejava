package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage[(Integer) searchKey] = r;
    }

    @Override
    protected void doDelete(Object searchKey) {
        fillDeletedElement((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException(r.getUuid(), "Хранилище переполнено");
        } else {
            insertElement(r, (Integer) searchKey);
            size++;
        }
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected boolean isExist(Object searchKey) {
       return  (Integer) searchKey >= 0;
    }

    public int size() {
        return size;
    }

    protected List<Resume> getAllResumes() {
        List<Resume> list = Arrays.asList(storage).stream().filter(s -> s != null).collect(Collectors.toList());
        return list;
    }


    protected abstract Integer getSearchKey(String uuid);

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);
}
