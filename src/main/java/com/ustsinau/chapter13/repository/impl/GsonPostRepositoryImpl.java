package com.ustsinau.chapter13.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter13.models.Post;
import com.ustsinau.chapter13.models.Writer;
import com.ustsinau.chapter13.repository.PostRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GsonPostRepositoryImpl implements PostRepository {
  private   List<Post> posts = new ArrayList<>();
    private static String FILE = "src\\main\\java\\com\\ustsinau\\chapter13\\resources\\posts.json";


    @Override
    public void create(Post value) {
//        if(posts==null){
//            posts = new ArrayList<>();
//        }
        posts.add(value);
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(FILE)) {
            g.toJson(value, fileWriter);

            System.out.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Post value) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Post getById(Long id) {
        return null;
    }

    @Override
    public List<Post> getAll() {
        List<Post> postAll;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try( FileReader reader = new FileReader(FILE)){

            Type listType = new TypeToken<List<Post>>(){}.getType();
            postAll = gson.fromJson(reader, listType);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if(postAll==null){
            return Collections.emptyList();
        }
        if(postAll.isEmpty()){
            return Collections.emptyList();
        }

        return postAll;
    }
}

