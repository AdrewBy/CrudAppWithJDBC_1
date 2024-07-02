package com.ustsinau.chapter2_2.repository.impl;


import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import com.ustsinau.chapter2_2.models.Writer;
import com.ustsinau.chapter2_2.repository.WriterRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class JdbcWriterRepositoryImpl implements WriterRepository {

    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/CrudAppWithJDBC_1";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final String USER = "root";
    private final String PASSWORD = "mysql";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Writer create(Writer writer) throws SQLException {

        String sql = "INSERT INTO writers (firstName, lastName) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                writer.setId(generatedKeys.getLong(1));
            }
        }
        return writer;
    }



    @Override
    public Writer update(Writer writer) throws SQLException {

        String updateWriterSql = "UPDATE Writers SET firstName = ?, lastName = ? WHERE id = ?";
        String updatePostSql = "UPDATE Posts SET writer_id = ? WHERE id = ?";
        String clearWriterPostsSql = "UPDATE Posts SET writer_id = NULL WHERE writer_id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement updateWriterStatement = connection.prepareStatement(updateWriterSql);
             PreparedStatement updatePostStatement = connection.prepareStatement(updatePostSql);
             PreparedStatement clearWriterPostsStatement = connection.prepareStatement(clearWriterPostsSql)) {

            // Обновление информации об авторе
            updateWriterStatement.setString(1, writer.getFirstName());
            updateWriterStatement.setString(2, writer.getLastName());
            updateWriterStatement.setLong(3, writer.getId());
            updateWriterStatement.executeUpdate();

            // Очистка старых связей автора с постами
         //   clearWriterPostsStatement.setLong(1, writer.getId());
         //   clearWriterPostsStatement.executeUpdate();

            // Обновление связей автора с новыми постами
            for (Post post : writer.getPosts()) {
                updatePostStatement.setLong(1, writer.getId());
                updatePostStatement.setLong(2, post.getId());
                updatePostStatement.executeUpdate();
            }
        }
        return writer;
    }


    @Override
    public void delete(Long id) throws SQLException {
        String sql = "Delete from writers where id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        }
    }

    @Override
    public List<Writer> getAll() throws SQLException {
        return getAllWritersInternal();
    }

    private List<Writer> getAllWritersInternal() throws SQLException {
        String sql = "Select * from writers";
        List<Writer> writers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Writer writer = new Writer();
                writer.setId(resultSet.getLong("id"));
                writer.setFirstName(resultSet.getString("firstName"));
                writer.setLastName(resultSet.getString("lastName"));
                writers.add(writer);

                List<Post> posts = getPostsForWriter(connection, writer.getId());
                writer.setPosts(posts);
            }

        }
        return writers;
    }

    private List<Post> getPostsForWriter(Connection connection, long writer_id) throws SQLException {
        String sql = "Select * from posts where writer_id = ?";
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, writer_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getLong("id"));
                post.setContent(resultSet.getString("content"));
                post.setPostStatus(PostStatus.valueOf(resultSet.getString("postStatus")));
                post.setCreated(resultSet.getTimestamp("created"));
                post.setUpdated(resultSet.getTimestamp("updated"));
                posts.add(post);
                List<Label> labels = getLabelsForPost(connection, post.getId());
                post.setLabels(labels);
            }
        }
        return posts;
    }

    private List<Label> getLabelsForPost(Connection connection, Long id) throws SQLException {
        String sql = "SELECT l.id, l.name FROM labels l INNER JOIN post_label pl ON l.id = pl.label_id WHERE pl.post_id = ?";
        List<Label> labels = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Label label = new Label();
                label.setId(resultSet.getLong("id"));
                label.setName(resultSet.getString("name"));
                labels.add(label);
            }
        }
        return labels;
    }


    @Override
    public Writer getById(Long id) throws SQLException {
        String sql = "Select * from writers where id = ?";
        Writer writer = null;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                writer = new Writer();
                writer.setId(resultSet.getLong("id"));
                writer.setFirstName(resultSet.getString("firstName"));
                writer.setLastName(resultSet.getString("lastName"));

                List<Post> posts = getPostsForWriter(connection, writer.getId());
                writer.setPosts(posts);
            }
        }
        return writer;
    }

}
