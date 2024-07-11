package com.ustsinau.chapter2_2.controller;

import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.Writer;
import com.ustsinau.chapter2_2.service.WriterService;
import com.ustsinau.chapter2_2.service.impl.WriterServiceImpl;

import java.util.List;

public class WriterController {
    private final WriterService writerService = new WriterServiceImpl();

    public void createWriterWithoutPost(String firstName, String lastName) {
        writerService.createWriter(firstName, lastName);

    }

    public void updateWriter(long id, String firstName, String lastName,  List<Post> posts) {
        writerService.updateWriter(id, firstName, lastName, posts );
    }

    public void deleteWriter(long id) {
        writerService.deleteWriter(id);
    }

    public Writer getValueByIndex(long id)  {
        return writerService.getWriterById(id);
    }
    public List<Writer> showAll() {
        return writerService.getAllWriters();
    }
}
