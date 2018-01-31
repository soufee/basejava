import java.util.ArrayList;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private ArrayList<Resume> storage = new ArrayList<>();

    void clear() {
        // код тут
       storage.clear();
    }

    void save(Resume r) {
       storage.add(r);
        // код тут

    }

    Resume get(String uuid) {
        // код тут
        for (int i = 0; i < storage.size() - 1; i++) {
            if (storage.get(i).toString().equals(uuid))
               return storage.get(i);

        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.size() - 1; i++) {
            if (storage.get(i).toString().equals(uuid))
                storage.remove(i);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return storage.toArray(new Resume[storage.size()]);
    }

    int size() {
     return   storage.size();
    }
}
