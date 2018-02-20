package ru.shoma.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void isOverFlow() throws Exception {
        try {
            for (int i = 3; i < 10000; i++) {
                storage.save(new Resume("Ashamaz"));
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume("Ashamaz"));
    }


}