package ru.shoma.webapp.storage;

import ru.shoma.webapp.exception.NotExistStorageException;
import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.*;
import ru.shoma.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                statement.setString(1, r.getFullName());
                statement.setString(2, r.getUuid());
                if (statement.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContacts(conn, r);
            deleteSections(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        helper.transactionalExecute(conn -> {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                statement.setString(1, r.getUuid());
                statement.setString(2, r.getFullName());
                statement.execute();
            }
            insertContacts(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.execute(" SELECT * FROM resume r" +
                " LEFT JOIN contact c " +
                " ON r.uuid = c.resume_uuid " +
                " LEFT JOIN section s ON r.uuid = s.resume_uuid "+
                " WHERE r.uuid = ?", statement -> {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(rs, r);
                addSection(rs, r);
            } while (rs.next());
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.execute("DELETE FROM resume WHERE uuid = ?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.execute("SELECT * FROM resume r " +
                "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                "LEFT JOIN section s ON r.uuid = s.resume_uuid "+
                "ORDER BY  full_name, uuid", statement -> {
            ResultSet results = statement.executeQuery();
            Map<String, Resume> map = new LinkedHashMap<>();
            while (results.next()) {
                String uuid = results.getString("uuid");
                Resume resume = map.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, results.getString("full_name"));
                    map.put(uuid, resume);
                }
                addContact(results, resume);
                addSection(results, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return helper.execute("SELECT count(*) FROM resume", statement -> {
            ResultSet results = statement.executeQuery();
            return results.next() ? results.getInt(1) : 0;
        });
    }

    private void deleteContacts(Connection conn, Resume r) {
        helper.execute("DELETE FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void deleteSections(Connection conn, Resume r) {
        helper.execute("DELETE FROM section WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                statement.setString(1, r.getUuid());
                statement.setString(2, e.getKey().name());
                statement.setString(3, e.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO section (resume_uuid, type_s, value_s) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                statement.setString(1, r.getUuid());
                statement.setString(2, e.getKey().name());
                switch (e.getKey().name()) {
                    case "PERSONAL":
                    case "OBJECTIVE":
                        TextSection sectionText = (TextSection) e.getValue();
                        statement.setString(3, sectionText.getContent());
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATIONS":
                        ListSection sectionList = (ListSection) e.getValue();
                        StringBuilder sb = new StringBuilder("");
                        sectionList.getAll().stream().forEach(s -> sb.append(s).append("\n"));
                        statement.setString(3, sb.toString().trim());
                        break;
                    default:
                        throw new StorageException("Тип секции не распознан");
                }
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }


    private void addContact(ResultSet results, Resume resume) throws SQLException {
        String value = results.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(results.getString("type")), value);
        }
    }

    private void addSection(ResultSet results, Resume resume) throws SQLException {
        String sectionType = results.getString("type_s");
        if (sectionType!=null){
        String value = results.getString("value_s");
        switch (sectionType) {
            case "PERSONAL":
            case "OBJECTIVE":
                if (value != null) {
                    TextSection section = new TextSection(value);
                    resume.addSectionItem(SectionType.valueOf(sectionType), section);
                }
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                if (value != null) {
                    String[] list = value.split("\n");
                    List<String> sectionValues = new ArrayList<>(Arrays.asList(list));
                    ListSection listSection = new ListSection(sectionValues);
                    resume.addSectionItem(SectionType.valueOf(sectionType), listSection);
                }
                break;
            default:
                throw new StorageException("Тип секции не распознан");
        }
        }
    }

}
