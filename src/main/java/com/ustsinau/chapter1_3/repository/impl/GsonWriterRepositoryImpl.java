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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class GsonWriterRepositoryImpl implements WriterRepository {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String FILE = "src/main/resources/writers.json";


    @Override
    public Writer create(Writer value) {
        List<Writer> writerList = getAllWritersInternal();
        long maxWriterId = getNewId(getAllWritersInternal());
        value.setId(maxWriterId);
        writerList.add(value);
        writerToFile(writerList);
        return value;
    }


    @Override
    public Writer update(Writer value) throws NullPointerException {

        String newName = value.getFirstName();
        String newLastName = value.getLastName();
        List<Post> newPosts = value.getPosts();

        List<Writer> writerList = getAllWritersInternal().stream()
                .map(e-> {
                    if (value.getId() == e.getId()) {
                        value.setFirstName(newName);
                        value.setLastName(newLastName);
                        value.setPosts(newPosts);
                        return value;
                    }
                    return e;
                }).collect(Collectors.toList());

        writerToFile(writerList);
        return value;
    }

    @Override
    public void delete(Long id) {
        List<Writer> writerList = getAllWritersInternal();

        writerList.stream().
                filter(e -> e.getId() == id).findFirst()
                .ifPresent(writer -> writer.setStatus(Status.DELETED));

        writerToFile(writerList);
    }

    private List<Writer> getAllWritersInternal() {
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
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    @Override
    public Writer getById(Long id) {
        List<Writer> writerList = getAll();

        return writerList.stream().
                filter(e -> e.getId() == id).findFirst()
                .orElse(null);
    }

    public void writerToFile(List<Writer> writerList) {

        try (FileWriter fileWriter = new FileWriter(FILE)) {
            gson.toJson(writerList, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Long getNewId(List<Writer> writers) {
        return writers.stream().max(Comparator.comparingLong(Writer::getId))
                .map(writer -> writer.getId() + 1).orElseGet(() -> 1L);
    }

}
