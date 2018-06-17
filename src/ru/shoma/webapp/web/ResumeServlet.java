package ru.shoma.webapp.web;

import ru.shoma.webapp.Config;
import ru.shoma.webapp.model.*;
import ru.shoma.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private static final File HEADER = new File("C:/BaseJava/basejava/src/ru/shoma/webapp/config/header.txt");
    private static final File FOOTER = new File("C:/BaseJava/basejava/src/ru/shoma/webapp/config/footer.txt");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage storage = Config.getInstance().getStorage();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        Writer writer = response.getWriter();

        if (name == null) {
            List<Resume> list = storage.getAllSorted();
            setHtmlFromFile(writer, HEADER);
            for (Resume r : list) {
                writer.write(getResumeByUuid(r));
            }
            setHtmlFromFile(writer, FOOTER);
        } else {
            setHtmlFromFile(writer, HEADER);
            writer.write(getResumeByUuid(storage.get(name)));
            setHtmlFromFile(writer, FOOTER);
        }

    }

    private void setHtmlFromFile(Writer writer, File file) {
        try (FileInputStream fstream = new FileInputStream(file);
             BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "UTF-8"))) {
            String strLine;
            while ((strLine = br.readLine()) != null) {
                writer.write(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getResumeByUuid(Resume r) {
        StringBuilder sb = new StringBuilder();
        sb.append("<center>\n" +
                "<table border=\"1\" width=\"640\">\n" +
                "<tr bgcolor=\"#fff2d6\">\n" +
                "<td colspan=\"5\" height=\"30\"><center><b>Резюме " + r.getUuid() + "</b></center></td>\n" +
                "</tr>");
        sb.append("<tr><td><b> ФИО</b><br>" + r.getFullName() + "</td></tr>");
        Map<ContactType, String> contacts = r.getContacts();
        StringBuilder contactList = new StringBuilder();
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            contactList.append(entry.getKey().getTitle() + " : " + entry.getValue() + "<br>");
        }
        sb.append("<tr><td><b> Контакты</b><br>" + contactList.toString().trim() + "</td></tr>");
        Map<SectionType, Section> sections = r.getSections();
        StringBuilder sectionList = new StringBuilder();
        for(Map.Entry<SectionType, Section> entry: sections.entrySet()){
            sectionList.append(entry.getKey().getTitle()+" : <br>");
            switch (entry.getKey()){
                case PERSONAL:
                case OBJECTIVE:
                    TextSection ts = (TextSection) entry.getValue();
                    sectionList.append("<pre> - "+ts.getContent()+"</pre> <br>");
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    ListSection ls = (ListSection) entry.getValue();
                    List <String> contents = ls.getAll();
                    for (String s:contents) {
                        sectionList.append("<pre> - "+s+"</pre> <br>");
                    }
                    break;
                 default:
                     break;

            }
        }
        sb.append("<tr><td><b> Информация </b><br>" + sectionList.toString().trim() + "</td></tr>");
        sb.append("<br></table>");
        return sb.toString();
    }

}
