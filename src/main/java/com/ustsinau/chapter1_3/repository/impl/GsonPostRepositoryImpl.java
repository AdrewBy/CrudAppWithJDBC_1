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
import java.util.stream.Collectors;


public class GsonPostRepositoryImpl implements PostRepository {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String FILE = "src/main/resources/posts.json";


    @Override
    public Post create(Post post) {
        List<Post> posts = getAllPostsInternal();
        long maxLabelId = getNewId(getAllPostsInternal());
        post.setId(maxLabelId);
        posts.add(post);
        writerPostsToFile(posts);
        return post;
    }

    @Override
    public Post update(Post value) {

        List<Post> posts = getAllPostsInternal()
                .stream().map(l-> {
                    if (value.getId()==l.getId()){
                        return value;
                    }
                   return l;
                }).collect(Collectors.toList());

        writerPostsToFile(posts);
        return value;
    }

    @Override
    public void delete(Long id) {
        List<Post> posts = getAllPostsInternal();
        posts.removeIf(l -> l.getId() == id);
        writerPostsToFile(posts);
    }

    @Override
    public Post getById(Long id) {

        return getAllPostsInternal().stream()
                .filter(e -> e.getId() == id).findFirst()
                .orElse(null);
    }

    private void writerPostsToFile(List<Post> postList) {

        try (FileWriter fileWriter = new FileWriter(FILE)) {
            gson.toJson(postList, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Post> getAllPostsInternal() {
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

    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

    private Long getNewId(List<Post> posts) {
        return posts.stream().max(Comparator.comparingLong(Post::getId))
                .map(post -> post.getId() + 1).orElseGet(()->1L);
    }
}

