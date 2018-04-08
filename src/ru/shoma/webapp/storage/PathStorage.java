package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;

import java.io.*;

/**
 * Created by Shoma on 07.04.2018.
 */
public class PathStorage extends AbstractPathStorage {

    protected PathStorage(String dir) {
        super(dir);
    }

    @Override
    protected void doWrite(Resume r, OutputStream os) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(os);
        stream.writeObject(r);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {

        try ( ObjectInputStream stream = new ObjectInputStream(is)){
            return (Resume) stream.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(null, "Ошибка чтения файла");
        }

    }
}
