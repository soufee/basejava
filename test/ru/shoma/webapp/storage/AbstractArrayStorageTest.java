package ru.shoma.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.Resume;


public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static Resume UUID1 = new Resume (UUID_1);
    private static Resume UUID2 = new Resume (UUID_2);
    private static Resume UUID3 = new Resume (UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
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
        storage.save(new Resume("Ashamaz"));
        Assert.assertEquals(storage.size(), 4);
        Resume r = storage.get("Ashamaz");
        Assert.assertNotNull(r);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Assert.assertEquals(storage.size(), 2);
        Resume[] r = new Resume[2];
        r[0] = UUID1;
        r[1] = UUID2;
        Assert.assertArrayEquals(storage.getAll(), r);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(new Resume().getUuid());

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

    @Test(expected = StorageException.class)
    public void isOverFlow() throws Exception {
        try{
        for (int i = 3; i < 10000; i++) {
            storage.save(new Resume());
        }
        }catch (Exception e){
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(3, storage.size());
        Resume[] resumes = new Resume[3];
        resumes[0] = (UUID1);
        resumes[1] = (UUID2);
        resumes[2] = (UUID3);
        Assert.assertArrayEquals(storage.getAll(), resumes);
    }

}