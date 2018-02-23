package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.ExistStorageException;
import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.model.Resume;

import java.util.*;


public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected static final Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getUuid);

    public void update(Resume r) {
        Object searchKey = getExistedSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public void save(Resume r) {
        Object searchKey = getNotExistedSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        doDelete(searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return doGet(searchKey);
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
    public List<Resume> getAllSorted() {
        List<Resume> list = getAllResumes();
        Collections.sort(list);
        return list;
        }

    protected abstract List<Resume> getAllResumes();

    public static class ArrayStorage extends AbstractArrayStorage {

        @Override
        protected void fillDeletedElement(int index) {
            storage[index] = storage[size - 1];
        }

        @Override
        protected void insertElement(Resume r, int index) {
            storage[size] = r;
        }

        protected Integer getSearchKey(String uuid) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    return i;
                }
            }
            return -1;
        }
    }
}
