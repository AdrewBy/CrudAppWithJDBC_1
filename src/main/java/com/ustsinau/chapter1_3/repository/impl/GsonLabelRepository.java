package com.ustsinau.chapter1_3.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.repository.LabelRepository;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GsonLabelRepository implements LabelRepository {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String FILE = "src/main/resources/labels.json";

    @Override
    public void create(Label value) {
        List<Label> labels = getAll();
        labels.add(value);

        saverFile(labels);
    }

    @Override
    public void update(Label newLabel) {

        long id = newLabel.getId();
        String newName = newLabel.getName();

        List<Label> labels = getAll();
        Label label = labels.stream().
                filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Post с ID "
                        + id + " не найден"));

        label.setName(newName);

        saverFile(labels);
    }

    @Override
    public void delete(Long id) {
        List<Label> labels = getAll();
        Label label = labels.stream().
                filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Post с ID "
                        + id + " не найден"));

        labels.remove(label);
        saverFile(labels);
    }

    @Override
    public Label getById(Long id) {

        List<Label> labels = getAll();
        Label label = labels.stream().
                filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Post с ID "
                        + id + " не найден"));

        return label;
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels;

        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Label>>() {
            }.getType();
            labels = gson.fromJson(reader, listType);
            if (labels == null) {
                labels = new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return labels;
    }

    public void saverFile(List<Label> labelList) {

        try (FileWriter fileWriter = new FileWriter(FILE)) {

            gson.toJson(labelList, fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

