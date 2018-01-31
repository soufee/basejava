import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
   ArrayList<Resume> storage = new ArrayList<>();

    void clear() {
        // код тут
        for (int i = 0; i < storage.size()-1; i++) {
            storage.set(i, null);
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.size()-1; i++) {
            if (storage.get(i)==null){
                storage.set(i,r);
                break;
            }
        }
        // код тут

    }

    Resume get(String uuid) {
        // код тут
        for (int i = 0; i < storage.size()-1; i++) {
            if (storage.get(i).toString().equals(uuid))
                return storage.get(i);
            break;
        }
       return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.size()-1; i++) {
            if (storage.get(i).toString().equals(uuid))
                storage.remove(i);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        // код тут
Resume[] resumes =  storage.toArray(new Resume[storage.size()]);
        return resumes;
    }

    int size() {
        int counter =  0;
        for (int i = 0; i < storage.size()-1; i++) {
            if (storage.get(i)!=null)
                counter++;
            else continue;
        }

        return counter;
    }
}
