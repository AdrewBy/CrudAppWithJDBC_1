package com.ustsinau.chapter2_2.controller;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;
import com.ustsinau.chapter2_2.repository.PostRepository;
import com.ustsinau.chapter2_2.repository.impl.JdbcPostRepositoryImpl;

import java.util.*;

public class PostController {

    private final PostRepository posts = new JdbcPostRepositoryImpl();


    public List<Post> showAll() {
        return posts.getAll();
    }

    public void createPost(String content,PostStatus postStatus, Date created) {
        posts.create(new Post( content, postStatus, created ));
    }

    public void updatePost(long id, String content, Date updated, List<Label> labels, PostStatus postStatus) {

        Post pst = new Post(id, content, postStatus, updated, labels);
        posts.update(pst);
    }

    public void deletePost(long index) {
        posts.delete(index);
    }

    public Post getPostById(long id) {
        return posts.getById(id);
    }

}
