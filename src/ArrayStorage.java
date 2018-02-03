import java.util.Arrays;

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

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].toString())) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                break;
            }
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = Arrays.copyOf(storage, size);
        return result;
    }

    int size() {
        return size;
    }
}
