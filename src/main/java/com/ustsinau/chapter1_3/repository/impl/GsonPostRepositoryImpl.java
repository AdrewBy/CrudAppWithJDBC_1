package com.ustsinau.chapter1_3.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.repository.PostRepository;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class GsonPostRepositoryImpl implements PostRepository {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String FILE = "src/main/resources/posts.json";


    @Override
    public void create(Post value) {
        List<Post> posts = getAll();
        long maxLabelId = getNewId(getAll());
        value.setId(maxLabelId);
        posts.add(value);
        saverFile(posts);
    }

    @Override
    public void update(Post value) {

        long id = value.getId();
        String newTitle = value.getTitle();
        String newContent = value.getContent();
        List<Label> newLabels = value.getLabels();

        List<Post> posts = getAll();
        Post post = posts.stream().filter(e -> e.getId() == id).findFirst()
                .orElse(null);

        if (post != null) {
            post.setTitle(newTitle);
            post.setContent(newContent);
            post.setLabels(newLabels);
        }
        saverFile(posts);
    }

    @Override
    public void delete(Long id) {
        List<Post> posts = getAll();

        Post post = posts.stream().filter(e -> e.getId() == id).findFirst()
                .orElse(null);

        posts.remove(post);
        saverFile(posts);
    }

    @Override
    public Post getById(Long id) {
        List<Post> posts = getAll();

        Post post = posts.stream().filter(e -> e.getId() == id).findFirst()
                .orElse(null);

        return post;
    }

    @Override
    public void saverFile(List<Post> postList) {

        try (FileWriter fileWriter = new FileWriter(FILE)) {
            gson.toJson(postList, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> postAll;
        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Post>>() {
            }.getType();
            postAll = gson.fromJson(reader, listType);
            if (postAll == null) {
                postAll = new ArrayList<>();
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return postAll;
    }
    private Long getNewId(List<Post> posts) {
        return   posts.stream().max(Comparator.comparingLong(Post::getId))
                .map(post -> post.getId()+1).orElse((1L));
    }
}

