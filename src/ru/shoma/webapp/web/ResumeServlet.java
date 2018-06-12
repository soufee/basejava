package ru.shoma.webapp.web;

import ru.shoma.webapp.Config;
import ru.shoma.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Storage storage = Config.getInstance().getStorage();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        if (name == null) response.getWriter().write(String.valueOf(storage.getAllSorted()));
        else response.getWriter().write(String.valueOf(storage.get(name)));
    }


}
