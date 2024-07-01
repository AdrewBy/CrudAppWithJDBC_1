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

    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/CrudAppWithJDBC_1";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    static final String USER = "root";
    static final String PASSWORD = "mysql";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Writer create(Writer writer) {

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

            saveWriterPosts(connection, writer);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;


    }

    private void saveWriterPosts(Connection connection, Writer writer) {
        String selectSql = "SELECT id FROM posts WHERE id = ?";
        String insertSql = "INSERT INTO posts (id, writer_id, content, postStatus, created, updated) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {

            for (Post post : writer.getPosts()) {
                selectStatement.setLong(1, post.getId());

                ResultSet resultSet = selectStatement.executeQuery();

                if (!resultSet.next()) {
                    insertStatement.setLong(1, post.getId());
                    insertStatement.setLong(2, writer.getId());
                    insertStatement.setString(3, post.getContent());
                    insertStatement.setString(4, post.getPostStatus().name());
                    insertStatement.setTimestamp(5, new Timestamp(post.getCreated().getTime()));
                    insertStatement.setTimestamp(6, new Timestamp(post.getUpdated().getTime()));
                    insertStatement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteWriterPosts(Connection connection, Writer writer) {
        String sql = "DELETE FROM posts WHERE writer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, writer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Writer update(Writer writer) {
        List<Post> posts = writer.getPosts();
        String sql = "Update writers set firstName =?, lastName =? where id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.setLong(3, writer.getId());
            statement.executeUpdate();

            deleteWriterPosts(connection, writer);
            saveWriterPosts(connection, writer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }


    @Override
    public void delete(Long id) {
        String sql = "Delete from writers where id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    private List<Writer> getAllWritersInternal() {
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writers;
    }

    private List<Post> getPostsForWriter(Connection connection, long id) {
        String sql = "Select * from posts where id = ?";
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public Writer getById(Long id) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

}
