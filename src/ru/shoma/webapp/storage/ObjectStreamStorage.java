package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;

import java.io.*;

/**
 * Created by Shoma on 25.03.2018.
 */
public class ObjectStreamStorage extends AbstractFileStorage {

    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(r);

        }
    }
        @Override
        protected Resume doRead (InputStream is) throws IOException {
            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                return (Resume) ois.readObject();

            } catch (ClassNotFoundException e) {
                throw new StorageException(null, "Ошибка чтения файла");
            }
        }
    }
