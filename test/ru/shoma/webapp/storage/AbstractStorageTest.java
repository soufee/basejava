package ru.shoma.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.model.*;

import java.io.File;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("C:\\BaseJava\\basejava\\storage");
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    //   private static final String UUID_4 = "uuid4";
    private static Resume R1;
    private static Resume R2;
    private static Resume R3;
    //  private static Resume R4;

    {
        R1 = new Resume(UUID_1, "Name 1");
        R2 = new Resume(UUID_2, "Name 2");
        R3 = new Resume(UUID_3, "Name 3");

        R1.addContact(ContactType.EMAIL, "soufee@mail.ru");
        R1.addContact(ContactType.CELLPHONE, "+79604268452");

        R1.addSectionItem(SectionType.OBJECTIVE, new TextSection("Java Developer"));
        R1.addSectionItem(SectionType.PERSONAL, new TextSection("Личные качества очень хорошие, мамой клянус!"));
        R1.addSectionItem(SectionType.ACHIEVEMENT, new ListSection("OCA сертификат", "Сертификат Университета Иннополис"));
        R1.addSectionItem(SectionType.QUALIFICATIONS, new ListSection("Java EE", "Pega BPM", "Spring MVC", "Spring IoC", "Spring security", "Hibernate", "SQL"));
       R1.addSectionItem(SectionType.EXPERIENCE,
              new OrgSection(
                       new Organization("Ай-Теко", "http:\\\\i-teco.ru", new Organization.Position(2017, Month.JULY, "Старший разработчик", "Java & Pega developer")),
                      new Organization("Новые информационные технологии", "", new Organization.Position(2007, Month.JULY, 2009, Month.SEPTEMBER, "Программист", "Delphi"))));
      R1.addSectionItem(SectionType.EDUCATION,
               new OrgSection(new Organization("КБЭПЛ", "",
                        new Organization.Position(2000, Month.SEPTEMBER, 2003, Month.JUNE, "Student", "")),
                        new Organization("КБИБ", "",
                                new Organization.Position(2003, Month.SEPTEMBER, 2008, Month.JULY, "Student", ""))));

        R2.addContact(ContactType.EMAIL, "zarina@mail.ru");
        R2.addContact(ContactType.CELLPHONE, "+79778339880");

        R2.addSectionItem(SectionType.OBJECTIVE, new TextSection("Корреспондент"));
        R2.addSectionItem(SectionType.PERSONAL, new TextSection("Личные данные"));
        R2.addSectionItem(SectionType.ACHIEVEMENT, new ListSection("Достиженние 1", "Достижение 2", "Достижение 3"));
        R2.addSectionItem(SectionType.QUALIFICATIONS, new ListSection("Умение 1", "Умение 2", "Умение 3"));
        R2.addSectionItem(SectionType.EXPERIENCE,
                new OrgSection(
                        new Organization("Мед академия, Владикавказ", null, new Organization.Position(2013, Month.JULY, 2014, Month.AUGUST, "Деканат", null)),
                        new Organization("газета Терек", null, new Organization.Position(2017, Month.MARCH, 2017, Month.MAY, "Корреспондент", null))));

        R2.addSectionItem(SectionType.EDUCATION,
                new OrgSection(new Organization("Московский государственный педагогический институт", null,
                        new Organization.Position(2004, Month.SEPTEMBER, 2009, Month.JUNE, "Student", null))));

    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(storage.size(), 0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2, UUID_2);
        storage.update(resume);
        Assert.assertTrue(resume.equals(storage.get(UUID_2)));
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume("Ashamaz", "Ashamaz"));
        assertEquals(storage.size(), 4);
        Resume r = storage.get("Ashamaz");
        Assert.assertNotNull(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(new Resume("Ashamaz").getUuid());
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertEquals(storage.size(), 2);
        storage.get(UUID_1);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        List<Resume> arr = new ArrayList<>();
        arr.add(R1);
        arr.add(R2);
        arr.add(R3);
        assertEquals(list, arr);
    }
}