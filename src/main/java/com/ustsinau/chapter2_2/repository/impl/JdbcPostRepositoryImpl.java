package com.ustsinau.chapter2_2.repository.impl;

import com.ustsinau.chapter2_2.mappers.LabelMapper;
import com.ustsinau.chapter2_2.mappers.PostMapper;
import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import com.ustsinau.chapter2_2.repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JdbcPostRepositoryImpl implements PostRepository {

    private final PostMapper postMapper = new PostMapper();



    @Override
    public Post create(Post post) {
        String sql = "INSERT INTO posts (content, postStatus, created, updated) VALUES (?,?,?,?)";
        try (PreparedStatement statement = DatabaseConnection.getPreparedStatementWithGenKey(sql)) {

            statement.setString(1, post.getContent());
            statement.setString(2, post.getPostStatus().name());
            statement.setTimestamp(3, new Timestamp(post.getCreated().getTime()));
            statement.setTimestamp(4, new Timestamp(post.getUpdated().getTime()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Создание поста не удалось, не удалось получить ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        String updatePostSql = "UPDATE posts SET content = ?, postStatus = ?, updated = ? WHERE id = ?";
        String updateLabelSql = "INSERT INTO Post_Label (post_id, label_id) VALUES (?, ?) ON DUPLICATE KEY UPDATE post_id = VALUES(post_id)";
        String deleteLabelsSql = "DELETE FROM Post_Label WHERE post_id = ?";
        try (PreparedStatement updatePostStatement = DatabaseConnection.getPreparedStatement(updatePostSql);
             PreparedStatement updateLabelsStatement = DatabaseConnection.getPreparedStatement(updateLabelSql);
             PreparedStatement deleteLabelsStatement = DatabaseConnection.getPreparedStatement(deleteLabelsSql)) {


            updatePostStatement.setString(1, post.getContent());
            updatePostStatement.setString(2, post.getPostStatus().name());
            updatePostStatement.setTimestamp(3, new Timestamp(post.getUpdated().getTime()));
            updatePostStatement.setLong(4, post.getId());
            updatePostStatement.executeUpdate();


            deleteLabelsStatement.setLong(1, post.getId());
            deleteLabelsStatement.executeUpdate();

            for (Label label : post.getLabels()) {
                updateLabelsStatement.setLong(1, post.getId());
                updateLabelsStatement.setLong(2, label.getId());
                updateLabelsStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return post;
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        try (PreparedStatement statement = DatabaseConnection.getPreparedStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getById(Long id) {
        String sql = "SELECT " +
                "p.id AS post_id, p.content, p.created, p.updated, p.postStatus, " +
                "l.id AS label_id, l.name " +
                "FROM posts p " +
                "LEFT JOIN post_label pl ON p.id = pl.post_id " +
                "LEFT JOIN labels l ON pl.label_id = l.id " +
                "WHERE p.id = ?";
        Post post = null;
        try (PreparedStatement statement = DatabaseConnection.getPreparedStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            List<Label> labels = new ArrayList<>();

            while (resultSet.next()) {
                if (post == null) {
                    post = new Post();
                    post.setLabels(labels);
                    postMapper.mapPost(resultSet, post, labels);
                } else {
                    postMapper.mapPost(resultSet, post, labels);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    private List<Post> getAllPostsInternal() {

        String sql = "SELECT p.id AS post_id, p.content, p.postStatus, p.created, p.updated, " +
                "l.id AS label_id, l.name " +
                "FROM posts p " +
                "LEFT JOIN post_label pl ON p.id = pl.post_id " +
                "LEFT JOIN labels l ON pl.label_id = l.id";

        Map<Long, Post> postMap = new HashMap<>();
        try (PreparedStatement statement = DatabaseConnection.getPreparedStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long postId = resultSet.getLong("post_id");
                Post post = postMap.computeIfAbsent(postId, k -> {
                    Post p = new Post();
                    p.setLabels(new ArrayList<>());
                    return p;
                });

                postMapper.mapPost(resultSet, post, post.getLabels());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>(postMap.values());
    }


    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

}