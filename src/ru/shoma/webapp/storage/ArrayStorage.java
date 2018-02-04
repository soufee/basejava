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
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }


    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid))
                return i;
        }
        return -1;

    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index != -1) {

            storage[index] = r;

        } else {
            System.out.println("Резюме с uuid " + r.getUuid() + " в базе нет.");
        }
    }


    public void save(Resume r) {
        if (size == storage.length) {
            System.out.println("База переполнена. Удалите ненужные записи");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Такая запись уже существует.");
        } else {
            storage[size] = r;
            size++;
        }
    }


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];

        } else {
            System.out.println("Не удалось найти запись с идентификатором " + uuid);
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
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

        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }
}
