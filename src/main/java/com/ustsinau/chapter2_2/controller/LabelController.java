package com.ustsinau.chapter2_2.controller;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.repository.impl.JdbcLabelRepository;
import com.ustsinau.chapter2_2.repository.LabelRepository;

import java.util.List;


public class LabelController {
    private final LabelRepository label = new JdbcLabelRepository();


    public List<Label> showAll() {
        return label.getAll();
    }

    public void createLabel(String name) {
        label.create(new Label(name));
    }


    public void updateLabel(Label labelNew) {
        label.update(labelNew);
    }


    public void deleteLabel(Long index) {
        label.delete(index);
    }

    public Label getValueByIndex(Long id) {
        return label.getById(id);
    }


}
