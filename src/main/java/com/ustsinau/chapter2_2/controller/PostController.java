package com.ustsinau.chapter2_2.controller;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import com.ustsinau.chapter2_2.repository.PostRepository;
import com.ustsinau.chapter2_2.repository.impl.JdbcPostRepositoryImpl;
import com.ustsinau.chapter2_2.service.PostService;
import com.ustsinau.chapter2_2.service.impl.PostServiceImpl;

import java.util.*;

public class PostController {

    private final PostService posts = new PostServiceImpl();


    public void createPost(String content, PostStatus postStatus, Date created) {
        posts.createPost(content, postStatus, created);
    }

    public void updatePost(long id, String content, Date updated, List<Label> labels, PostStatus postStatus) {
        posts.updatePost(id, content, updated, labels, postStatus);
    }

    public void deletePost(long id) {
        posts.deletePost(id);
    }

    public Post getPostById(long id) {
        return posts.getPostById(id);
    }

    public List<Post> showAll() {
        return posts.getAllPosts();
    }

}
