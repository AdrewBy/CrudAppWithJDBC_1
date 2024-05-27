package com.ustsinau.chapter13.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter13.models.Label;
import com.ustsinau.chapter13.models.Post;
import com.ustsinau.chapter13.models.Writer;
import com.ustsinau.chapter13.repository.PostRepository;
import com.ustsinau.chapter13.view.HeadConsole;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class GsonPostRepositoryImpl implements PostRepository {


    private static String FILE = "src\\main\\java\\com\\ustsinau\\chapter13\\resources\\posts.json";


    @Override
    public void create(Post value) {
        List<Post> posts = getAll();
        posts.add(value);
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(FILE)) {
            g.toJson(posts, fileWriter);
            System.out.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Post value) {
        List<Post> postList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        long id = value.getId();
        String newTitle = value.getTitle();
        String newContent = value.getContent();
        List<Label> newLabels = value.getLabels();

        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Post>>() {}.getType();
            postList = gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<Post> postOpt = postList.stream().filter(e -> e.getId() == id).findFirst();

        if (postOpt.isPresent()) {                    //  как это работает?
            Post post = postOpt.get();
            post.setTitle(newTitle);
            post.setContent(newContent);
            post.setLabels(newLabels);

        } else {
            System.err.println("Post с ID " + id + " не найден.");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(FILE)){

            gson.toJson(postList,fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Long id) {
        List<Post> postListList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Post>>() {}.getType();
            postListList = gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<Post> postOpt = postListList.stream().filter(e -> e.getId() == id).findFirst();

        if (postOpt.isPresent()) {                    //  как это работает?
            Post post = postOpt.get();
            postListList.remove(post);
        } else {
            System.err.println("Post с ID " + id + " не найден.");

        }

        try (FileWriter fileWriter = new FileWriter(FILE)){

            gson.toJson(postListList,fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public Post getById(Long id) {
        List<Post> postsList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader fileReader = new FileReader(FILE)){
            Type collectionType = new TypeToken<List<Post>>() {}.getType();
            postsList = gson.fromJson(fileReader, collectionType);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла: " + e.getMessage(), e);
        }
        Post post = postsList.stream().filter(e -> e.getId() == id).
                findFirst().orElseThrow(()->new RuntimeException("Post с ID " + id + " не найден"));

        // если использовать if для проверки isPresent то что возвращать тогда?
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> postAll ;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Post>>() {}.getType();
            postAll = gson.fromJson(reader, listType);
            if (postAll == null) {
                postAll = new ArrayList<>();
            }

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
//
//        if (postAll.isEmpty()) {
//            return Collections.emptyList(); // нужно ли это?
//        }

        return postAll;
    }
}

