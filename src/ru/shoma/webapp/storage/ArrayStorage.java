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


    private int isResumeExists(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid))
                return i;
        }
        return -1;

    }

    public void update(Resume r) {
        int index = isResumeExists(r.getUuid());
        if (index != -1) {

            storage[index] = r;

        } else {
            System.out.println("Резюме с uuid " + r.getUuid() + " в базе нет.");
        }
    }


    public void save(Resume r) {
        if (size == 10000) {
            System.out.println("База переполнена. Удалите ненужные записи");
        } else {
            int index = isResumeExists(r.getUuid());
            if (index == -1) {
                storage[size] = r;
                size++;
            } else {
                System.out.println("Такая запись уже существует.");
            }
        }
    }

    public Resume get(String uuid) {
        int index = isResumeExists(uuid);
        if (index != -1) {
            return storage[index];

        } else {
            System.out.println("Не удалось найти запись с идентификатором " + uuid);
        }
        return null;
    }

    public void delete(String uuid) {
        int index = isResumeExists(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;

        } else {
            System.out.println("Не удалось найти запись с идентификатором " + uuid);
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
