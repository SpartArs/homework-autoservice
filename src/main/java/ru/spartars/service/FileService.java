package ru.spartars.service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService {
    private final String uploadPath;

    public FileService() throws IOException {
        uploadPath = System.getenv("UPLOAD_PATH");
        Files.createDirectories(Paths.get(uploadPath));
    }

    public void readFile(String id, ServletOutputStream os) throws IOException {
        var path = Paths.get(uploadPath).resolve(id);
        Files.copy(path, os);
    }

    public String writeFile(Part part) throws IOException {
        var id = UUID.randomUUID().toString();
        part.write(id);
        return id;
    }

    public Path getFile(String id) {
        return Paths.get(uploadPath).resolve(id);
    }

}
