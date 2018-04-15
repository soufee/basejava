package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;
import ru.shoma.webapp.storage.serializers.SerializeStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Shoma on 07.04.2018.
 */
public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private SerializeStrategy strategy;

    protected PathStorage(String dir, SerializeStrategy strategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if ((!Files.isDirectory(directory)) || (!Files.isWritable(directory))) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        this.strategy = strategy;
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
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException(null, "Directory read error");
        }

    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            strategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException(r.getUuid(), "Path write error");
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException(r.getUuid(), "Ошибка записи файла");
        }
        doUpdate(r, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {

            throw new StorageException(path.getFileName().toString(), "Path read error");
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<Resume> getAllResumes() {

        try {
            return Files.list(directory).map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException(null, "Cant get all resumes");
        }
    }
}
