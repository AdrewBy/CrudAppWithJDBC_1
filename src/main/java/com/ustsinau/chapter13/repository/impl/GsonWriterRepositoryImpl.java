package com.ustsinau.chapter13.repository.impl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter13.models.Label;
import com.ustsinau.chapter13.models.Post;
import com.ustsinau.chapter13.models.Status;
import com.ustsinau.chapter13.models.Writer;
import com.ustsinau.chapter13.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GsonWriterRepositoryImpl implements WriterRepository {

    private static String FILE = "src\\main\\java\\com\\ustsinau\\chapter13\\resources\\writers.json";


    @Override
    public void create(Writer value) {
        List<Writer> writerList = getAll();
        writerList.add(value);
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(FILE)) {
            g.toJson(writerList, fileWriter);
            System.out.println(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Writer value) {

        List<Writer> writerList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        long id = value.getId();
        String newName = value.getFirstName();
        String newLastName = value.getLastName();
        List<Post> newPosts = value.getPosts();

        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Writer>>() {}.getType();
            writerList = gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<Writer> writerOpt = writerList.stream().filter(e -> e.getId() == id).findFirst();

        if (writerOpt.isPresent()) {                    //  как это работает?
            Writer writer = writerOpt.get();
            writer.setFirstName(newName);
            writer.setLastName(newLastName);
            writer.setPosts(newPosts);
//           if (!(newPosts == null)) {
//               writer.setPosts(newPosts);
//           }else {
//              writer.setPosts();
//           }

        } else {
            System.err.println("Автор1 с ID " + id + " не найден.");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(FILE)){

            gson.toJson(writerList,fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Long id) {
        List<Writer> writerList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Writer>>() {}.getType();
            writerList = gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<Writer> writerOpt = writerList.stream().filter(e -> e.getId() == id).findFirst();

        if (writerOpt.isPresent()) {                    //  как это работает?
            Writer writer = writerOpt.get();
            writer.setStatus(Status.DELETED);
        } else {
            System.err.println("Label с ID " + id + " не найден.");

        }

        try (FileWriter fileWriter = new FileWriter(FILE)){

            gson.toJson(writerList,fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writerALL;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(FILE)) {

            Type listType = new TypeToken<List<Writer>>() {}.getType();
            writerALL = gson.fromJson(reader, listType);
            if (writerALL == null) {
                writerALL = new ArrayList<>();
            }
        } catch (IOException e) {
            // System.err.println("Файл не найден или ошибка при чтении, будет создан новый: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return writerALL;
    }

    @Override
    public Writer getById(Long id) {
        List<Writer> writerList;
        Gson gson = new GsonBuilder().create();

        try (FileReader fileReader = new FileReader(FILE)){
            Type collectionType = new TypeToken<List<Writer>>() {}.getType();
            writerList = gson.fromJson(fileReader, collectionType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Writer writer = writerList.stream().filter(e -> e.getId() == id).findFirst().get();
        return writer;
    }


}
