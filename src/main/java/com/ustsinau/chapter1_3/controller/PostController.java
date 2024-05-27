package com.ustsinau.chapter1_3.controller;

import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.repository.LabelRepository;
import com.ustsinau.chapter1_3.repository.PostRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonLabelRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonPostRepositoryImpl;

import java.util.*;

import static com.ustsinau.chapter1_3.services.impl.CacheServiceImpl.cache;

public class PostController {

    private  PostRepository posts = new GsonPostRepositoryImpl();
    private  LabelRepository label = new GsonLabelRepository();

    private List<Label> labelsPost = new ArrayList<>();

    public List<Post> showAll() {
        return posts.getAll();
    }

    public void createPostWithLabel(String title, String content, List<Label> labels) {
        long maxPostId = cache.getMaxPostId();
        maxPostId++;
        posts.create(new Post(maxPostId,title, content, labels));
        cache.setMaxPostId(maxPostId);
    }

    public void createPostWithoutLabel(String title, String content) {
        long maxPostId = cache.getMaxPostId();
        maxPostId++;
        posts.create(new Post(maxPostId, title, content));
        cache.setMaxPostId(maxPostId);
    }

    public void updatePost(long id, String title, String content, String labels) {

        long[] numArr = Arrays.stream(labels.split(" ")).mapToLong(e -> Long.parseLong(e)).toArray();

        for (int i = 0; i < numArr.length; i++) {       // подумать над этим
            labelsPost.add(label.getById(numArr[i]));
        }

        Post pst = new Post(id, title, content, labelsPost);
        posts.update(pst);
    }

    public void deletePost(long index) {
        posts.delete(index);
    }

    public Post getValueByIndex(long id) {
        return posts.getById(id);
    }

}
