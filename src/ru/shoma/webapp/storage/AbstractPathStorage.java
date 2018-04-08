package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if ((!Files.isDirectory(directory)) || (!Files.isWritable(directory))) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", directory.toString());
        }
    }

    @Override
    public int size() {
        try {
            int size = (int) Files.list(directory).count();
            return size;
        } catch (IOException e) {
            throw new StorageException(null, "Directory read error");
        }

    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, Files.newOutputStream(path));
        }
        catch (IOException e) {
            throw new StorageException(r.getUuid(), "Path write error");
        }
    }

    @Override
    protected boolean isExist(Path path) {
        boolean b = Files.exists(path);
        return b;
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        doUpdate(r, path);
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected Resume doGet(Path path) {
        try {
          Resume r = doRead(Files.newInputStream(path));
            return r;
        } catch (IOException e) {
            try {
                throw new StorageException(Files.getFileStore(path).name(), "Path read error");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } return null;
    }

    @Override
    protected void doDelete(Path path) {
        try {
            if (!Files.deleteIfExists(path)) {
                throw new StorageException(Files.getFileStore(path).name(), "Path delete error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<Resume> getAllResumes() {

        Path[] paths = new Path[0];
        try {
            paths = (Path[]) Files.list(directory).toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (paths == null) {
            throw new StorageException(null, "Directory read error");
        }
        List<Resume> list = new ArrayList<>(paths.length);

        for (Path path : paths) {
            list.add(doGet(path));
        }
        return list;

    }
}
