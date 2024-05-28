package com.ustsinau.chapter1_3.controller;

import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.repository.PostRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonPostRepositoryImpl;

import java.util.*;

import static com.ustsinau.chapter1_3.repository.impl.GeneratorIdRepositoryImpl.generatorId;

public class PostController {

    private final PostRepository posts = new GsonPostRepositoryImpl();


    public List<Post> showAll() {
        return posts.getAll();
    }

    public void createPostWithoutLabel(String title, String content) {
        long maxPostId = generatorId.getMaxPostId();
        maxPostId++;
        posts.create(new Post(maxPostId, title, content));
        generatorId.setMaxPostId(maxPostId);
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
