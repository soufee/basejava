package ru.shoma.webapp.storage;

import ru.shoma.webapp.Config;
import ru.shoma.webapp.storage.serializers.SerializeStrategy;
import ru.shoma.webapp.storage.serializers.XmlStreamSerializer;

/**
 * Created by Shoma on 18.05.2018.
 */
public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}