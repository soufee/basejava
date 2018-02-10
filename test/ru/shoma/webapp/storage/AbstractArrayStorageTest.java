package ru.shoma.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;


public  class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";


    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }


    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));

    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(storage.size(), 0);

    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2);
        storage.update(resume);
        Resume resume1 = storage.get(UUID_2);
        Assert.assertEquals(resume, resume1);
    }

    @Test
    public void save() throws Exception {
    storage.save(new Resume("Ashamaz"));
    Assert.assertEquals(storage.size(), 4);
    Resume r = storage.get("Ashamaz");
    Assert.assertNotNull(r);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Resume[] r = new Resume[2];
        r[0] = new Resume(UUID_1);
        r[1] = new Resume(UUID_2);

        Assert.assertArrayEquals(storage.getAll(), r);
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

    @Test (expected = StorageException.class)
    public void isOverFlow() throws Exception {
        for (int i = 0; i < 10001; i++) {
            storage.save(new Resume());
        }
    }

    @Test
    public void getAll() throws Exception {
        Resume[]resumes = new Resume[3];
        resumes[0] = (new Resume(UUID_1));
        resumes[1] = (new Resume(UUID_2));
        resumes[2] = (new Resume(UUID_3));

        Assert.assertArrayEquals(storage.getAll(), resumes);
    }

}