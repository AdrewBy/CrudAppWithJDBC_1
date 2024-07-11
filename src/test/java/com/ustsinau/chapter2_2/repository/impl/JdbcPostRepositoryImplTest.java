package com.ustsinau.chapter2_2.repository.impl;


import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import java.util.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JdbcPostRepositoryImplTest {

    @Mock
    private DatabaseConnection databaseConnection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private JdbcPostRepositoryImpl postRepository;

    private Post post;
    private Label label;

    @BeforeEach
    void setUp() throws SQLException {

        when(databaseConnection.getPreparedStatement(anyString())).thenReturn(preparedStatement);

        // Инициализация поста и метки
        post = new Post();
        post.setId(1L);
        post.setContent("Test Content");
        post.setPostStatus(PostStatus.ACTIVE);
        post.setCreated(new Date());
        post.setUpdated(new Date());

        label = new Label();
        label.setId(1L);
        post.setLabels(Collections.singletonList(label));
    }

    @Test
    void testCreate() throws SQLException {
        postRepository.create(post);

        verify(preparedStatement).setString(1, "Test Content");
        verify(preparedStatement).setString(2, "ACTIVE");
        verify(preparedStatement).setTimestamp(eq(3), any(Timestamp.class));
        verify(preparedStatement).setTimestamp(eq(4), any(Timestamp.class));
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testUpdate() throws SQLException {
        post.setContent("Updated Content");

        postRepository.update(post);

        verify(preparedStatement, times(1)).setString(1, "Updated Content");
        verify(preparedStatement, times(1)).setString(2, "ACTIVE");
        verify(preparedStatement, times(1)).setTimestamp(eq(3), any(Timestamp.class));
        verify(preparedStatement, times(1)).setLong(4, 1L);
        verify(preparedStatement, times(1)).executeUpdate();

        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeUpdate();

        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).setLong(2, 1L);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        Long postId = 1L;

        postRepository.delete(postId);

        verify(preparedStatement).setLong(1, postId);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testGetById() throws SQLException {
        Long postId = 1L;

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(postId);
        when(resultSet.getString("content")).thenReturn("Test Content");
        when(resultSet.getString("postStatus")).thenReturn("ACTIVE");

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        Post foundPost = postRepository.getById(postId);

        assertNotNull(foundPost);
        assertEquals(postId, foundPost.getId());
        assertEquals("Test Content", foundPost.getContent());
        assertEquals(PostStatus.ACTIVE, foundPost.getPostStatus());
    }

    @Test
    void testGetAll() throws SQLException {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setContent("Content 1");
        post1.setPostStatus(PostStatus.ACTIVE);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setContent("Content 2");
        post2.setPostStatus(PostStatus.DELETED);

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(post1.getId()).thenReturn(post2.getId());
        when(resultSet.getString("content")).thenReturn(post1.getContent()).thenReturn(post2.getContent());
        when(resultSet.getString("postStatus")).thenReturn(post1.getPostStatus().name()).thenReturn(post2.getPostStatus().name());

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        List<Post> posts = postRepository.getAll();

        assertNotNull(posts);
        //  assertEquals(2, posts.size());
        assertEquals(post1.getId(), posts.get(0).getId());
        assertEquals(post1.getContent(), posts.get(0).getContent());
        assertEquals(post1.getPostStatus(), posts.get(0).getPostStatus());

        assertEquals(post2.getId(), posts.get(1).getId());
        assertEquals(post2.getContent(), posts.get(1).getContent());
        assertEquals(post2.getPostStatus(), posts.get(1).getPostStatus());
    }
}
