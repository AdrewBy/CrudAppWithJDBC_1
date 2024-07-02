package com.ustsinau.chapter2_2.repository.impl;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import com.ustsinau.chapter2_2.repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcPostRepositoryImpl implements PostRepository {

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
    public Post create(Post post) throws SQLException {
        String sql = "INSERT INTO posts (content, postStatus, created, updated) VALUES (?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, post.getContent());
            statement.setString(2, post.getPostStatus().name());
            statement.setTimestamp(3, new Timestamp(post.getCreated().getTime()));
            statement.setTimestamp(4, new Timestamp(post.getUpdated().getTime()));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getLong(1));
            }

            savePostLabels(connection, post);

        }
        return post;
    }

    private void savePostLabels(Connection connection, Post post) throws SQLException {
        String sql = "INSERT INTO post_label (post_id, label_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Label label : post.getLabels()) {
                statement.setLong(1, post.getId());
                statement.setLong(2, label.getId());
                statement.executeUpdate();
            }

        }
    }
    private void deletePostLabels(Connection connection, Post post) throws SQLException {
        String sql = "DELETE FROM post_label WHERE post_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, post.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public Post update(Post post) throws SQLException {
        String sql = "UPDATE posts SET content = ?, postStatus = ?, updated = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, post.getContent());
            statement.setString(2, post.getPostStatus().name());
            statement.setTimestamp(3, new Timestamp(post.getUpdated().getTime()));
            statement.setLong(4, post.getId());
            statement.executeUpdate();

            deletePostLabels(connection, post);
            savePostLabels(connection, post);

        }
        return post;
    }



    @Override
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Post getById(Long id) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToPost(resultSet, connection);
            }
        }
        return null;
    }

    @Override
    public List<Post> getAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM posts";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Post post = mapResultSetToPost(resultSet, connection);
                posts.add(post);
            }
        }
        return posts;
    }

    private Post mapResultSetToPost(ResultSet resultSet, Connection connection) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getLong("id"));
        post.setContent(resultSet.getString("content"));
        post.setPostStatus(PostStatus.valueOf(resultSet.getString("postStatus")));
        post.setCreated(resultSet.getTimestamp("created"));
        post.setUpdated(resultSet.getTimestamp("updated"));

        List<Label> labels = getLabelsForPost(connection, post.getId());
        post.setLabels(labels);

        return post;
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
}