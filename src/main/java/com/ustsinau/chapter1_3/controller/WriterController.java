package com.ustsinau.chapter1_3.controller;

import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.models.Status;
import com.ustsinau.chapter1_3.models.Writer;
import com.ustsinau.chapter1_3.repository.WriterRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonWriterRepositoryImpl;

import java.util.List;

public class WriterController {
    private final WriterRepository writers = new GsonWriterRepositoryImpl();

    public List<Writer> showAll() {
        return writers.getAll();
    }


    public void createWriterWithoutPost(String firstName, String lastName) {
        writers.create(new Writer(firstName, lastName, Status.ACTIVE));

    }

    public void updateWriter(long id, String firstName, String lastName,  List<Post> posts) {

        Writer wrt = new Writer(id, firstName, lastName, posts );
        writers.update(wrt);
    }

    public void deleteWriter(long index) {
        writers.delete(index);
    }

    public Writer getValueByIndex(long id)  {
        return writers.getById(id);
    }

}
