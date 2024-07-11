package com.ustsinau.chapter2_2.service.impl;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import com.ustsinau.chapter2_2.repository.PostRepository;
import com.ustsinau.chapter2_2.repository.impl.JdbcPostRepositoryImpl;
import com.ustsinau.chapter2_2.service.PostService;

import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository = new JdbcPostRepositoryImpl();

    @Override
    public void createPost(String content, PostStatus postStatus, Date created) {
        Post post = new Post(content, postStatus, created);
        postRepository.create(post);
    }

    @Override
    public void updatePost(long id, String content, Date updated, List<Label> labels, PostStatus postStatus) {
        Post pst = new Post(id, content, postStatus, updated, labels);
        postRepository.update(pst);
    }

    @Override
    public void deletePost(long id) {
        postRepository.delete(id);
    }

    @Override
    public Post getPostById(long id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAll();
    }
}
