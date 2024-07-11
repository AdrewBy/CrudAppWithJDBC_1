package com.ustsinau.chapter2_2.service;

import com.ustsinau.chapter2_2.models.Label;

import java.util.List;

public interface LabelService {

    void createLabel(String name);
    void updateLabel(long id, String name);
    void deleteLabel(long id);
    Label getLabelById(long id);

    List<Label> getAllLabels();
}
