package ru.spartars.service;

import ru.spartars.domain.Auto;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AutoService {
    private final DataSource dataSource;

    public AutoService() throws NamingException, SQLException {
        var context = new InitialContext();
        dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
        try(var conn = dataSource.getConnection()) {
            try(var stmt = conn.createStatement()) {
                stmt.execute("CREATE  TABLE IF NOT EXISTS autos " +
                        "(id TEXT PRIMARY KEY, NAME TEXT NOT NULL, description TEXT NOT NULL, image TEXT);");
            }
        }
    }

    public void create(String name, String description, String image) throws IOException {

        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement(
                    "INSERT INTO autos\n" +
                            "(id, name, description, image)\n" +
                            "VALUES (?, ?, ?, ?);")) {

                stmt.setString(1, UUID.randomUUID().toString());
                stmt.setString(2, name);
                stmt.setString(3, description);
                stmt.setString(4, image);
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update(String id, String name, String description, String image) throws IOException {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement(
                    "UPDATE autos SET name=?, description=?, image=? WHERE id=?")) {
                stmt.setString(1, name);
                stmt.setString(2, description);
                stmt.setString(3, image);
                stmt.setString(4, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Auto> getAll() {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.createStatement()) {
                try (var rs = stmt.executeQuery("SELECT id, name, description, image FROM autos")) {
                    var list = new ArrayList<Auto>();

                    while (rs.next()) {
                        var id = rs.getString("id");
                        var name = rs.getString("name");
                        var description = rs.getString("description");
                        var image = rs.getString("image");
                        list.add(new Auto(id, name, description, image));

                    }

                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public Auto getById(String id) {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement(
                    "SELECT id, name, description, image FROM autos WHERE id = ?")) {

                stmt.setString(1, id);
                try (var rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new RuntimeException("404");
                    }

                    var name = rs.getString("name");
                    var description = rs.getString("description");
                    var image = rs.getString("image");
                    return new Auto(id, name, description, image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Auto> findByName(String q) {
        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("SELECT id, name, description, image FROM autos WHERE name = ?")) {
                stmt.setString(1, q);
                try (var rs = stmt.executeQuery()) {
                    var list = new ArrayList<Auto>();

                    while (rs.next()) {
                        var id = rs.getString("id");
                        var name = rs.getString("name");
                        var description = rs.getString("description");
                        var image = rs.getString("image");
                        list.add(new Auto(id, name, description, image));

                    }

                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {
        var auto = getById(id);
        var image = auto.getImage();

        try (var conn = dataSource.getConnection()) {
            try (var stmt = conn.prepareStatement("DELETE FROM autos WHERE id = ?")) {
                stmt.setString(1, id);
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}