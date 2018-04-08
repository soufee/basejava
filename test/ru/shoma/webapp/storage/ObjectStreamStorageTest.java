package ru.shoma.webapp.storage;

/**
 * Created by Shoma on 25.03.2018.
 */
public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
