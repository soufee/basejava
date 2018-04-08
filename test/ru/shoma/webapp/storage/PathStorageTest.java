package ru.shoma.webapp.storage;

import static ru.shoma.webapp.storage.AbstractStorageTest.STORAGE_DIR;

/**
 * Created by Shoma on 07.04.2018.
 */
public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath()));
    }
}
