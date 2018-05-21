package ru.shoma.webapp.sql;

import ru.shoma.webapp.exception.ExistStorageException;
import ru.shoma.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory factory) {
        this.connectionFactory = factory;
    }

    public <T> T execute(String sql, SqlExecutor <T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
           return executor.executeAndGet(ps);
        } catch (SQLException e) {
           if (e.getSQLState().equals("23505")) throw new ExistStorageException(null);
            throw new StorageException(e);
        }
    }

}
