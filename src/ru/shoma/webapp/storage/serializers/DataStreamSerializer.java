package ru.shoma.webapp.storage.serializers;

import ru.shoma.webapp.exception.StorageException;
import ru.shoma.webapp.model.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamSerializer implements SerializeStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            //TODO: SECTIONS
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                Section s = entry.getValue();
                String sectionType = s.getClass().getName();
                dos.writeUTF(sectionType);
                switch (sectionType) {
                    case "ru.shoma.webapp.model.TextSection":
                        TextSection textSection = (TextSection) s;
                        dos.writeUTF(textSection.getContent());
                        break;
                    case "ru.shoma.webapp.model.ListSection":
                        ListSection listSection = (ListSection) s;
                        dos.writeInt(listSection.getAll().size());
                        listSection.getAll().forEach(st -> {
                            try {
                                dos.writeUTF(st);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        break;
                    case "ru.shoma.webapp.model.OrgSection":
                        OrgSection orgSection = (OrgSection) s;
                        dos.writeInt(orgSection.getOrganizations().size());
                        System.out.println(orgSection.getOrganizations().size());
                        for (Organization o : orgSection.getOrganizations()) {
                            Link link = o.getHomepage();
                            String name = link.getName();
                            dos.writeUTF(name);
                            String url = link.getUrl();
                            dos.writeUTF(url != null ? url : "_");
                            dos.writeInt(o.getPositions().size());
                            for (Organization.Position position : o.getPositions()) {
                                dos.writeUTF(position.getTitle());
                                String descr = position.getDescription();
                                dos.writeUTF(descr != null ? descr : "_");
                                dos.writeUTF(String.valueOf(position.getStartDate()));
                                dos.writeUTF(String.valueOf(position.getEndDate()));
                            }
                        }

                        break;
                    default:
                        throw new IOException("Неизвестная ошибка");
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                ContactType type = ContactType.valueOf(dis.readUTF());
                String contactValue = dis.readUTF();
                resume.addContact(type, contactValue);
            }
            //TODO: SECTIONS
            int sizeOfSections = dis.readInt();
            Map<SectionType, Section> sections = new HashMap<>(sizeOfSections);

            for (int i = 0; i < sizeOfSections; i++) {
                String sectionName = dis.readUTF();
                String sectionType = dis.readUTF();

                switch (sectionType) {
                    case "ru.shoma.webapp.model.TextSection":
                        Section section = new TextSection(dis.readUTF());
                        sections.put(SectionType.valueOf(sectionName), section);
                        break;
                    case "ru.shoma.webapp.model.ListSection":
                        int sizeOfListSection = dis.readInt();
                        List<String> list = new ArrayList<>(sizeOfListSection);
                        for (int j = 0; j < sizeOfListSection; j++) {
                            list.add(dis.readUTF());
                        }
                        Section section1 = new ListSection(list);
                        sections.put(SectionType.valueOf(sectionName), section1);
                        break;
                    case "ru.shoma.webapp.model.OrgSection":
                        int orgListSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>(orgListSize);
                        for (int j = 0; j < orgListSize; j++) {
                            String linkName = dis.readUTF();
                            String url = dis.readUTF();
                            url = url.equals("_") ? null: url;
                            Link link = new Link(linkName, url);
                            int positionsSize = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>(positionsSize);
                            for (int k = 0; k < positionsSize; k++) {
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                description = description.equals("_") ? null: description;
                                String start = dis.readUTF();
                                String end = dis.readUTF();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate startDate = LocalDate.parse(start, formatter);
                                LocalDate endDate = LocalDate.parse(end, formatter);
                                Organization.Position position = new Organization.Position(title, startDate, endDate, description);
                                positions.add(position);
                            }
                            Organization organization = new Organization(link, positions);
                            organizations.add(organization);
                        }
                        Section section2 = new OrgSection(organizations);
                        sections.put(SectionType.valueOf(sectionName), section2);
                        break;
                    default:
                        throw new IOException("Неизвестная ошибка");
                }

            }
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                resume.addSectionItem(entry.getKey(), entry.getValue());
            }
            return resume;

        }
    }
}
