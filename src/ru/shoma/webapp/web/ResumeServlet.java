package ru.shoma.webapp.web;

import ru.shoma.webapp.Config;
import ru.shoma.webapp.model.ContactType;
import ru.shoma.webapp.model.Resume;
import ru.shoma.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage storage = Config.getInstance().getStorage();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        Writer writer = response.getWriter();
        putHtmlText(new File("C:/BaseJava/basejava/src/ru/shoma/webapp/config/header.txt"), writer);
        if (name == null) {
            List<Resume> list = storage.getAllSorted();
            for (Resume r : list) {
                writer.write("<tr><td colspan=\"5\" height=\"30\">" + r.getFullName() + "</td>");
                Map<ContactType, String> contacts = r.getContacts();
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<ContactType, String> entry:contacts.entrySet()) {
                  builder.append(entry.getKey().getTitle()+" : "+entry.getValue()+"<br>");
                }
                writer.write("<td colspan=\"5\" height=\"30\">" + builder.toString().trim() + "</td></tr>");

            }
        } else {
            writer.write("<tr><td colspan=\"5\" height=\"30\">" + storage.get(name).getFullName() + "</td>");
            Map<ContactType, String> contacts = storage.get(name).getContacts();
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<ContactType, String> entry:contacts.entrySet()) {
                builder.append(entry.getKey()+" : "+entry.getValue()+"<br>");
            }
            writer.write("<td colspan=\"5\" height=\"30\">" + builder.toString().trim() + "</td></tr>");
        }
        putHtmlText(new File("C:/BaseJava/basejava/src/ru/shoma/webapp/config/footer.txt"), writer);
    }

    private void putHtmlText(File file, Writer writer) {
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

}
