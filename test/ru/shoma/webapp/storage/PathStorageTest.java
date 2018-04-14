package ru.shoma.webapp.storage;

import ru.shoma.webapp.storage.serializers.PathSerializer;


public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new PathSerializer()));
            }
}
