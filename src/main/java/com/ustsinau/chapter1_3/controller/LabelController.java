package com.ustsinau.chapter1_3.controller;

import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.repository.impl.GsonLabelRepository;
import com.ustsinau.chapter1_3.repository.LabelRepository;

import java.util.List;

import static com.ustsinau.chapter1_3.repository.impl.GeneratorIdImpl.cache;

public class LabelController {
    private final LabelRepository label = new GsonLabelRepository();


    public List<Label> showAll() {
        return label.getAll();
    }

    public void createLabel(String name) {
        long maxLabelId = cache.getMaxLabelId();
        maxLabelId++;
        label.create(new Label(maxLabelId, name));
        cache.setMaxPostId(maxLabelId);
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
