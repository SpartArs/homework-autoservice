package ru.spartars.servlet;

import ru.spartars.domain.Auto;
import ru.spartars.service.AutoService;
import ru.spartars.service.FileService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;

public class CatalogServlet extends HttpServlet {
    private AutoService autoService;
    private FileService fileService;

    @Override
    public void init() throws ServletException {
        try {
            var context = new InitialContext();
            autoService = (AutoService) context.lookup("java:/comp/env/bean/auto-service");
            fileService = (FileService) context.lookup("java:/comp/env/bean/file-service");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = req.getParameter("id");
        Auto auto = null;
        if (id != null) {
            auto = autoService.getById(id);
            req.setAttribute("item", auto);
        }

        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "create":
                    req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);
                    return;
                case "details":
                    req.getRequestDispatcher("/WEB-INF/views/details.jsp").forward(req, resp);
                    return;
                case "update":
                    req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
                    return;
                case "delete":
                    doDelete(req, resp);
                    return;
                default:
                    break;
            }
        }

        var q = req.getParameter("q");
        if (q != null) {
            var list = autoService.findByName(q);
            req.setAttribute("items", list);
            req.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
            return;
        }

        var list = autoService.getAll();
        req.setAttribute("items", list);
        req.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var name = req.getParameter("name");
        var file = req.getPart("file");

        var description = req.getParameter("description");
        String action = req.getParameter("action");

        if ("update".equals(action)) {
            var id = req.getParameter("id");
            var oldAuto = autoService.getById(id);
            var image = oldAuto.getImage();
            if (file.getSize() > 0) {
                var path = fileService.getFile(image);
                try {
                    if(Files.exists(path)) {
                        Files.delete(path);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("File not found");
                } finally {
                    image = fileService.writeFile(file);
                }
            }
            autoService.update(id, name, description, image);

        }

        if ("create".equals(action)) {
            var image = fileService.writeFile(file);
            autoService.create(name, description, image);
        }

        resp.sendRedirect("/catalog");

    }



    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        var auto = autoService.getById(id);
        var image = auto.getImage();
        autoService.delete(id);
        var path = fileService.getFile(image);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/catalog");
    }
}