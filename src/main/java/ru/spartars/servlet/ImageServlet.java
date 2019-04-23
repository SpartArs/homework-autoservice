package ru.spartars.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageServlet extends HttpServlet {
    private final static String UPLOAD_PATH = "D:/SpringCourse/homeWork/02.Autoservice/upload";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() != null) {
            String[] split = req.getPathInfo().split("/");
            if (split.length != 2) {
                throw new RuntimeException();
            }
            var id = split[1];
            var path = Paths.get(UPLOAD_PATH).resolve(id);
            if (!Files.exists(path)) {
                throw new RuntimeException("404");
            }

            Files.copy(path, resp.getOutputStream());
        }
    }
}