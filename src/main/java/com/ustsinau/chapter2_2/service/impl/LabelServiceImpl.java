package com.ustsinau.chapter2_2.service.impl;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.repository.LabelRepository;
import com.ustsinau.chapter2_2.repository.impl.JdbcLabelRepository;
import com.ustsinau.chapter2_2.service.LabelService;

import java.util.List;

public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRep = new JdbcLabelRepository();

    @Override
    public void createLabel(String name) {
        Label label = new Label(name);
        labelRep.create(label);
    }

    @Override
    public void updateLabel(long id, String name) {
        Label label = new Label(id, name);
        labelRep.update(label);

    }

    @Override
    public void deleteLabel(long id) {
        labelRep.delete(id);
    }

    @Override
    public Label getLabelById(long id) {
        return labelRep.getById(id);
    }

    @Override
    public List<Label> getAllLabels() {
        return labelRep.getAll();
    }
}
