package com.ustsinau.chapter13.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter13.models.Label;
import com.ustsinau.chapter13.models.Writer;
import com.ustsinau.chapter13.repository.LabelRepository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GsonLabelRepository implements LabelRepository {

    private static String FILE = "src\\main\\java\\com\\ustsinau\\chapter13\\resources\\labels.json";

    @Override
    public Label getById(Long id) {
        List<Label> labelList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(FILE)) {

            Type collectionType = new TypeToken<List<Label>>() {
            }.getType();
            labelList = gson.fromJson(reader, collectionType);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Label label = labelList.stream().filter(e -> e.getId() == id).findFirst().get();

        return label;
    }

    @Override
    public void create(Label value) {
        List<Label> labels = getAll();
        labels.add(value);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(FILE)) {
            gson.toJson(labels, fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Label newLabel) {
        List<Label> labelList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        long id = newLabel.getId();
        String newName = newLabel.getName();

        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Label>>() {}.getType();
            labelList = gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<Label> labelOpt = labelList.stream().filter(e -> e.getId() == id).findFirst();

        if (labelOpt.isPresent()) {                    //  как это работает?
            Label label = labelOpt.get();
            label.setName(newName);
        } else {
            System.err.println("Label с ID " + id + " не найден.");
            return;
        }

        try (FileWriter fileWriter = new FileWriter(FILE)){

            gson.toJson(labelList,fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        List<Label> labelList;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Label>>() {}.getType();
            labelList = gson.fromJson(reader, listType);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<Label> labelOpt = labelList.stream().filter(e -> e.getId() == id).findFirst();

        if (labelOpt.isPresent()) {                    //  как это работает?
            Label label = labelOpt.get();
            labelList.remove(label);
        } else {
            System.err.println("Label с ID " + id + " не найден.");

        }

        try (FileWriter fileWriter = new FileWriter(FILE)){

            gson.toJson(labelList,fileWriter);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<Label> getAll() {
        List<Label> allLabels;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader reader = new FileReader(FILE)) {
            Type listType = new TypeToken<List<Label>>() {
            }.getType();
            allLabels = gson.fromJson(reader, listType);
            if (allLabels == null) {
                allLabels = new ArrayList<>();
            }
        } catch (IOException e) {
            // System.err.println("Файл не найден или ошибка при чтении, будет создан новый: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return allLabels;
    }

}

