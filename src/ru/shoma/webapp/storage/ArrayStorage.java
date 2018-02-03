package ru.shoma.webapp.storage;

import ru.shoma.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        // код тут
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    private boolean isResumeExists(Resume r) {
        return isResumeExists(r.getUuid());
    }

    private boolean isResumeExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid))
                return true;
        }
        return false;

    }

    public void update(Resume r, String s) {

        if (isResumeExists(r)) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    storage[i].setUuid(s);
                }
            }
        } else {
            System.out.println("Резюме с uuid " + r.getUuid() + " в базе нет.");
        }
    }


    public void save(Resume r) {
        if (size == 10000) {
            System.out.println("База переполнена. Удалите ненужные записи");
        } else {
            if (!isResumeExists(r)) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Такая запись уже существует.");
            }
        }
    }

    public Resume get(String uuid) {
        if (isResumeExists(uuid)) {
            for (int i = 0; i < size; i++) {
                if (storage[i] != null) {
                    if (storage[i].getUuid().equals(uuid))
                        return storage[i];

                }
            }
        } else {
            System.out.println("Не удалось найти запись с идентификатором " + uuid);
        }
        return null;
    }

    public void delete(String uuid) {
        if (isResumeExists(uuid)) {
            for (int i = 0; i < size; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    storage[i] = storage[size - 1];
                    storage[size - 1] = null;
                    size--;
                    break;
                }
            }
        } else {
            System.out.println("Не удалось найти запись с идентификатором " + uuid);
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] result = Arrays.copyOf(storage, size);
        return result;
    }

    public int size() {
        return size;
    }
}
