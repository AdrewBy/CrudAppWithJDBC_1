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
    private final LabelMapper labelMapper = new LabelMapper();

    @Override
    public Post create(Post post) {
        String sql = "INSERT INTO posts (content, postStatus, created, updated) VALUES (?,?,?,?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {

            statement.setString(1, post.getContent());
            statement.setString(2, post.getPostStatus().name());
            statement.setTimestamp(3, new Timestamp(post.getCreated().getTime()));
            statement.setTimestamp(4, new Timestamp(post.getUpdated().getTime()));
            statement.executeUpdate();


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
        try (PreparedStatement updatePostStatement = DatabaseConnection.getInstance().getPreparedStatement(updatePostSql);
             PreparedStatement updateLabelsStatement = DatabaseConnection.getInstance().getPreparedStatement(updateLabelSql);
             PreparedStatement deleteLabelsStatement = DatabaseConnection.getInstance().getPreparedStatement(deleteLabelsSql)) {


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
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getById(Long id) {
        List<Post> posts = getAllPostsInternal();
        for (Post post : posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        return null;
    }

    private List<Post> getAllPostsInternal() {

        String sql = "SELECT p.id as post_id, p.content, p.postStatus, p.created, p.updated," +
                " l.id AS label_id, l.name " +
                "FROM posts p " +
                "LEFT JOIN post_label pl ON p.id = pl.post_id " +
                "LEFT JOIN labels l ON pl.label_id = l.id";

        Map<Long, Post> postMap = new HashMap<>();
        List<Post> posts = new ArrayList<>();

        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                long postId = resultSet.getLong("post_id");
                Post post = postMap.get(postId);

                if (post == null) {
                    post = new Post();
                    postMapper.mapPost(resultSet, post);
                    post.setLabels(new ArrayList<>());
                    posts.add(post);
                    postMap.put(postId, post);
                }

                long labelId = resultSet.getLong("label_id");
                if (labelId > 0) {
                    Label label = new Label();
                    labelMapper.mapLabel(resultSet, label);
                    post.getLabels().add(label);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

}