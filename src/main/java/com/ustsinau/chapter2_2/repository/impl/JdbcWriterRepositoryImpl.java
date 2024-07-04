package com.ustsinau.chapter2_2.repository.impl;


import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import com.ustsinau.chapter2_2.models.Writer;
import com.ustsinau.chapter2_2.repository.WriterRepository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;


public class JdbcWriterRepositoryImpl implements WriterRepository {


    @Override
    public Writer create(Writer writer)  {

        String sql = "INSERT INTO writers (firstName, lastName) VALUES (?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatementWithGenerated(sql)) {
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
    public Writer update(Writer writer)  {

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

            // Очистка старых связей автора с постами
         //   clearWriterPostsStatement.setLong(1, writer.getId());
         //   clearWriterPostsStatement.executeUpdate();

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
    public void delete(Long id)  {
        String sql = "Delete from writers where id = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Writer> getAll()  {
        return getAllWritersInternal();
    }

    private List<Writer> getAllWritersInternal() {
        String sql = "Select * from writers";
        List<Writer> writers = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Writer writer = new Writer();
                writer.setId(resultSet.getLong("id"));
                writer.setFirstName(resultSet.getString("firstName"));
                writer.setLastName(resultSet.getString("lastName"));
                writers.add(writer);

        //        List<Post> posts = getPostsForWriter(connection, writer.getId());
        //        writer.setPosts(posts);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writers;
    }

    private List<Post> getPostsForWriter(Connection connection, long writer_id)  {
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
     //           List<Label> labels = getLabelsForPost(connection, post.getId());
     //           post.setLabels(labels);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    @Override
    public Writer getById(Long id)  {
        String sql = "Select * from writers w join posts p on (w.id = p.writer_id) where w.id = ?";
        Writer writer = null;

        try (PreparedStatement statement = DatabaseConnection.getInstance().getPreparedStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                writer = new Writer();
                writer.setId(resultSet.getLong("id"));
                writer.setFirstName(resultSet.getString("firstName"));
                writer.setLastName(resultSet.getString("lastName"));

        //        List<Post> posts = getPostsForWriter(connection, writer.getId());
        //        writer.setPosts(posts);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return writer;
    }

}
