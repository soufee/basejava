import java.util.ArrayList;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        // код тут
        storage = new Resume[10000];
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }

    }

    Resume get(String uuid) {
        // код тут
        for (Resume r : storage) {
            try {
                if (r.toString().equals(uuid))
                    return r;

            } catch (NullPointerException e) {
                continue;
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length - 1; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = null;
                break;
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
        return storage.length;
    }
}
