package ru.shoma.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static Resume UUID1 = new Resume(UUID_1, UUID_1);
    private static Resume UUID2 = new Resume(UUID_2, UUID_2);
    private static Resume UUID3 = new Resume(UUID_3, UUID_3);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(UUID1);
        storage.save(UUID2);
        storage.save(UUID3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(storage.size(), 0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = UUID2;
        storage.update(resume);
        Assert.assertTrue(resume == storage.get(UUID_2));
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume("Ashamaz","Ashamaz"));
        Assert.assertEquals(storage.size(), 4);
        Resume r = storage.get("Ashamaz");
        Assert.assertNotNull(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(new Resume("Ashamaz").getUuid());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(storage.get(UUID_2).getUuid(), "uuid2");
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(storage.size(), 2);
        storage.get(UUID_1);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        List<Resume> listExpected = new ArrayList<>();
        listExpected.add(UUID1);
        listExpected.add(UUID2);
        listExpected.add(UUID3);

        Assert.assertEquals(list, listExpected);
    }

}