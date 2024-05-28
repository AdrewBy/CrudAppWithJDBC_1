package com.ustsinau.chapter1_3.controller;

import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.repository.PostRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonPostRepositoryImpl;

import java.util.*;

public class PostController {

    private final PostRepository posts = new GsonPostRepositoryImpl();


    public List<Post> showAll() {
        return posts.getAll();
    }

    public void createPostWithoutLabel(String title, String content) {
        posts.create(new Post( title, content));
    }

    public void updatePost(long id, String title, String content, List<Label> labels) {

        Post pst = new Post(id, title, content, labels);
        posts.update(pst);
    }

    public void deletePost(long index) {
        posts.delete(index);
    }

    public Post getValueByIndex(long id) {
        return posts.getById(id);
    }

}
