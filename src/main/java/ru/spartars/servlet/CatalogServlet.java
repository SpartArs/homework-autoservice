package ru.spartars.servlet;

import ru.spartars.domain.Auto;
import ru.spartars.service.AutoService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatalogServlet extends HttpServlet {
    private AutoService service;

    @Override
    public void init() throws ServletException {
        try {
            var context = new InitialContext();
            service = (AutoService) context.lookup("java:/comp/env/bean/auto-service");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = req.getParameter("id");
        Auto auto = null;
        if (id != null) {
            auto = service.getById(id);
            req.setAttribute("item", auto);
        }

        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "create" :
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
            var list = service.findByName(q);
            req.setAttribute("items", list);
            req.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
            return;
        }

        var list = service.getAll();
        req.setAttribute("items", list);
        req.getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var name = req.getParameter("name");
        var file = req.getPart("file");

        var description = req.getParameter("description");
        String action = req.getParameter("action");

        if("create".equals(action)) {
            service.create(name, description, file);
        } else if ("update".equals(action)) {
            var id = req.getParameter("id");
            var auto = new Auto();
            if (file.getSize() <= 0) {
                file = null;
            }
            service.update(id, name, description, file);
        }

        resp.sendRedirect("/catalog");

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/catalog");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        service.delete(id);

        resp.sendRedirect("/catalog");
    }
}