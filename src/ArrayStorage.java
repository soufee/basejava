import java.util.ArrayList;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        // код тут
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;

    }

    Resume get(String uuid) {
        // код тут
        for (int i = 0; i < size; i++) {
            if (storage[i] != null) {
                if (storage[i].uuid.equals(uuid))
                    return storage[i];

            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = -1;
        for (int i = 0; i < storage.length - 1; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = null;
                size--;
                index = i;
                break;
            }
        }

        for (int i = storage.length - 1; i > 0; i--) {
            if (index >= 0) {
                if (storage[i] != null) {
                    storage[index] = storage[i];
                    storage[i] = null;
                    break;
                }
            }
        }


    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return storage;
    }

    int size() {
        return size;
    }
}
