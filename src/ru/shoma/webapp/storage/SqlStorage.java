package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.model.Resume;
import ru.shoma.webapp.sql.ConnectionFactory;
import ru.shoma.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        helper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        helper.execute("DELETE from resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        helper.execute("UPDATE resume SET full_name = ? where uuid = ?", statement -> {
            statement.setString(1, r.getFullName());
            statement.setString(2, r.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        helper.execute("INSERT  INTO resume (uuid, full_name) VALUES (?, ?)", statement -> {
            statement.setString(1, r.getUuid());
            statement.setString(2, r.getFullName());
            statement.executeUpdate();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute("SELECT * FROM resume r where r.uuid = ?", statement -> {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute("DELETE FROM resume where uuid = ?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.execute("Select uuid, full_name FROM resume ORDER by uuid, full_name", statement -> {
            ResultSet results = statement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (results.next()) {
                resumes.add(new Resume(results.getString("uuid").trim(), results.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return helper.execute("Select count(*) FROM resume", statement -> {
            ResultSet results = statement.executeQuery();
            return results.next() ? results.getInt(1) : 0;
        });
    }

}
