package ru.shoma.webapp;

import ru.shoma.webapp.model.Resume;
import ru.shoma.webapp.storage.ArrayStorage;
import ru.shoma.webapp.storage.SortedArrayStorage;
import ru.shoma.webapp.storage.Storage;


public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid3");
        Resume r3 = new Resume();
        r3.setUuid("uuid4");
        Resume r4 = new Resume();
        r4.setUuid("uuid2");
        Resume r5 = new Resume();
        r5.setUuid("uuid6");
        Resume r6 = new Resume();
        r6.setUuid("uuid7");
        Resume r7 = new Resume();
        r7.setUuid("uuid9");
        Resume r8 = new Resume();
        r8.setUuid("uuid8");
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r5);
        ARRAY_STORAGE.save(r6);
        ARRAY_STORAGE.save(r7);
        ARRAY_STORAGE.save(r8);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    private static void printAll() {
        System.out.println("\nGet All");

        for (Resume r : ARRAY_STORAGE.getAll()) {

            System.out.println(r);
        }
    }
}
