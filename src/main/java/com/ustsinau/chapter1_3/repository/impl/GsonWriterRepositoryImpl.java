package com.ustsinau.chapter1_3.repository.impl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.models.Status;
import com.ustsinau.chapter1_3.models.Writer;
import com.ustsinau.chapter1_3.repository.WriterRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GsonWriterRepositoryImpl implements WriterRepository {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String FILE = "src/main/resources/writers.json";


    @Override
    public void create(Writer value) {
        List<Writer> writerList = getAll();
        writerList.add(value);

        saverFile(writerList);
    }


    @Override
    public void update(Writer value) {

        long id = value.getId();
        String newName = value.getFirstName();
        String newLastName = value.getLastName();
        List<Post> newPosts = value.getPosts();

        List<Writer> writerList = getAll();

        Writer writer = writerList.stream().
                filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Автор с ID "
                        + id + " не найден"));

        writer.setFirstName(newName);
        writer.setLastName(newLastName);
        writer.setPosts(newPosts);

        saverFile(writerList);
    }

    @Override
    public void delete(Long id) {
        List<Writer> writerList = getAll();

        Writer writer = writerList.stream().
                filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Автор с ID "
                        + id + " не найден"));

        writer.setStatus(Status.DELETED);

        saverFile(writerList);
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writerALL;

        try (FileReader reader = new FileReader(FILE)) {

            Type listType = new TypeToken<List<Writer>>() {
            }.getType();
            writerALL = gson.fromJson(reader, listType);
            if (writerALL == null) {
                writerALL = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writerALL;
    }

    @Override
    public Writer getById(Long id) {
        List<Writer> writerList = getAll();

        Writer writer = writerList.stream().
                filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Автор с ID "
                        + id + " не найден"));

        return writer;
    }

    @Override
    public void saverFile(List<Writer> writerList) {

        try (FileWriter fileWriter = new FileWriter(FILE)) {

            gson.toJson(writerList, fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
