package ru.shoma.webapp;

import ru.shoma.webapp.storage.SqlStorage;
import ru.shoma.webapp.storage.Storage;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private Storage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
  } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }
}
