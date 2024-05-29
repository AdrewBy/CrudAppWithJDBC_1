package com.ustsinau.chapter1_3.repository.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.models.Status;
import com.ustsinau.chapter1_3.repository.LabelRepository;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class GsonLabelRepository implements LabelRepository {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String FILE = "src/main/resources/labels.json";

    @Override
    public Label create(Label label) {
        List<Label> labels = getAllLabelsInternal();
        long maxLabelId = getNewId(getAllLabelsInternal());
        label.setId(maxLabelId);
        labels.add(label);

        writerLabelsToFile(labels);
        return label;
    }

    @Override
    public Label update(Label newLabel) {
        List<Label> labels = getAllLabelsInternal().stream().map(l -> {
            if(newLabel.getId() == l.getId()) {
                return newLabel;
            }
            return l;
        }).collect(Collectors.toList());

        writerLabelsToFile(labels);
        return newLabel;
    }

    @Override
    public void delete(Long id) {
        List<Label> labels = getAllLabelsInternal();
        labels.removeIf(l -> l.getId() == id);
        writerLabelsToFile(labels);
    }

    @Override
    public Label getById(Long id) {
        return getAllLabelsInternal().stream().
                filter(e -> e.getId() == id).findFirst()
                .orElse(null);
    }

    @Override
    public List<Label> getAll() {
      return getAllLabelsInternal();
    }


    private List<Label> getAllLabelsInternal() {
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

    private void writerLabelsToFile(List<Label> labelList) {
        try (FileWriter fileWriter = new FileWriter(FILE)) {
            gson.toJson(labelList, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private Long getNewId(List<Label> labels) {
      return   labels.stream().max(Comparator.comparingLong(Label::getId))
              .map(label -> label.getId()+1).orElse((1L));
    }

}

