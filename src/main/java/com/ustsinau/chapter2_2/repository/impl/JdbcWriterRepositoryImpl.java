package com.ustsinau.chapter2_2.repository.impl;


import com.ustsinau.chapter2_2.mappers.LabelMapper;
import com.ustsinau.chapter2_2.mappers.PostMapper;
import com.ustsinau.chapter2_2.mappers.WriterMapper;
import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.Writer;
import com.ustsinau.chapter2_2.repository.WriterRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JdbcWriterRepositoryImpl implements WriterRepository {

    private final WriterMapper writerMapper = new WriterMapper();
    private final PostMapper postMapper = new PostMapper();
    private final LabelMapper labelMapper = new LabelMapper();

    @Override
    public Writer create(Writer writer) {

        String sql = "INSERT INTO writers (firstName, lastName) VALUES (?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
            statement.setString(1, writer.getFirstName());
            statement.setString(2, writer.getLastName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                writer.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }


    @Override
    public Writer update(Writer writer) {

        String updateWriterSql = "UPDATE Writers SET firstName = ?, lastName = ? WHERE id = ?";
        String updatePostSql = "UPDATE Posts SET writer_id = ? WHERE id = ?";
        String clearWriterPostsSql = "UPDATE Posts SET writer_id = NULL WHERE writer_id = ?";

        try (PreparedStatement updateWriterStatement = DatabaseConnection.getInstance().getPreparedStatement(updateWriterSql);
             PreparedStatement updatePostStatement = DatabaseConnection.getInstance().getPreparedStatement(updatePostSql);
             PreparedStatement clearWriterPostsStatement = DatabaseConnection.getInstance().getPreparedStatement(clearWriterPostsSql)) {

            // Обновление информации об авторе
            updateWriterStatement.setString(1, writer.getFirstName());
            updateWriterStatement.setString(2, writer.getLastName());
            updateWriterStatement.setLong(3, writer.getId());
            updateWriterStatement.executeUpdate();

            //   Очистка старых связей автора с постами
            clearWriterPostsStatement.setLong(1, writer.getId());
            clearWriterPostsStatement.executeUpdate();

            // Обновление связей автора с новыми постами
            for (Post post : writer.getPosts()) {
                updatePostStatement.setLong(1, writer.getId());
                updatePostStatement.setLong(2, post.getId());
                updatePostStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }


    @Override
    public void delete(Long id) {
        String sql = "Delete from writers where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
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
        String sql = "SELECT w.id AS writer_id, w.firstName, w.lastName, " +
                "p.id AS post_id, p.content, p.created, p.updated, p.postStatus, " +
                "l.id AS label_id, l.name " +
                "FROM writers w " +
                "LEFT JOIN posts p ON (w.id = p.writer_id) " +
                "LEFT JOIN post_label pl ON (p.id = pl.post_id) " +
                "LEFT JOIN labels l ON (pl.label_id = l.id)";

        List<Writer> writers = new ArrayList<>();
        Map<Long, Post> postMap = new HashMap<>();
        Map<Long, Writer> writerMap = new HashMap<>();

        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long writerId = resultSet.getLong("writer_id");
                Writer writer = writerMap.get(writerId);
                if (writer == null) {
                    writer = new Writer();
                    writerMapper.mapWriter(resultSet, writer);
                    writers.add(writer);
                    writerMap.put(writerId, writer);
                }

                long postId = resultSet.getLong("post_id");
                if (postId > 0) {
                    Post post = postMap.get(postId);
                    if (post == null) {
                        post = new Post();
                        postMapper.mapPost(resultSet, post);
                        writer.getPosts().add(post);
                        postMap.put(postId, post);
                    }

                    long labelId = resultSet.getLong("label_id");
                    if (labelId > 0) {
                        Label label = new Label();
                        labelMapper.mapLabel(resultSet, label);
                        post.getLabels().add(label);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writers;
    }

    @Override
    public Writer getById(Long id) {
        List<Writer> allWriters = getAllWritersInternal();
        for (Writer writer : allWriters) {
            if (writer.getId() == id) {
                return writer;
            }
        }
        return null;
    }
}
