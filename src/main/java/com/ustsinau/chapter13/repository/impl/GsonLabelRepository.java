package com.ustsinau.chapter13.repository.impl;

import com.ustsinau.chapter13.models.Label;
import com.ustsinau.chapter13.repository.LabelRepository;

import java.util.Collections;
import java.util.List;

public class GsonLabelRepository implements LabelRepository {

    private static String FILE = "src\\main\\java\\com\\ustsinau\\chapter13\\resources\\labels.json";

    @Override
    public Label getById(Long id) {
        return null;
    }

    @Override
    public void create(Label value) {

    }

    @Override
    public void update(Label value) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Label> getAll() {
      return Collections.emptyList();
    }

}

