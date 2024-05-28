package com.ustsinau.chapter1_3.controller;

import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.models.Status;
import com.ustsinau.chapter1_3.models.Writer;
import com.ustsinau.chapter1_3.repository.WriterRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonWriterRepositoryImpl;

import java.util.List;

import static com.ustsinau.chapter1_3.repository.impl.GeneratorIdRepositoryImpl.generatorId;

public class WriterController {
    private final WriterRepository writers = new GsonWriterRepositoryImpl();

    public List<Writer> showAll() {
        return writers.getAll();
    }


    public void createWriterWithoutPost(String firstName, String lastName) {
        long maxWriterId = generatorId.getMaxWriterId();
        maxWriterId++;

        writers.create(new Writer(maxWriterId,firstName, lastName, Status.ACTIVE));
        generatorId.setMaxWriterId(maxWriterId);

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
