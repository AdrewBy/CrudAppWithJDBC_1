package com.ustsinau.chapter13.controller;

import com.ustsinau.chapter13.models.Post;
import com.ustsinau.chapter13.models.Status;
import com.ustsinau.chapter13.models.Writer;
import com.ustsinau.chapter13.repository.LabelRepository;
import com.ustsinau.chapter13.repository.PostRepository;
import com.ustsinau.chapter13.repository.WriterRepository;
import com.ustsinau.chapter13.repository.impl.GsonLabelRepository;
import com.ustsinau.chapter13.repository.impl.GsonPostRepositoryImpl;
import com.ustsinau.chapter13.repository.impl.GsonWriterRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static com.ustsinau.chapter13.services.impl.CacheServiceImpl.cache;

public class WriterController {
    private static WriterRepository writers = new GsonWriterRepositoryImpl();
    private static PostRepository posts = new GsonPostRepositoryImpl();
    private static LabelRepository labels = new GsonLabelRepository();

    private List<Post> postsWriter = new ArrayList<>();

    public List<Writer> showAll() {
        return writers.getAll();
    }

    public void createWriterWithPost(String firstName, String lastName, List<Post> posts) {
        long maxWriterId = cache.getMaxWriterId();
        maxWriterId++;
        writers.create(new Writer(maxWriterId,firstName, lastName, posts, Status.ACTIVE));
        cache.setMaxWriterId(maxWriterId);
    }
    public void createWriterWithoutPost(String firstName, String lastName) {
        long maxWriterId = cache.getMaxWriterId();
        maxWriterId++;
        writers.create(new Writer(maxWriterId,firstName, lastName, Status.ACTIVE));
        cache.setMaxWriterId(maxWriterId);

    }

    public void updateWriter(long id, String firstName, String lastName,  Status ps) {


        Writer wrt = new Writer(id, firstName, lastName, postsWriter, ps );
        writers.update(wrt);
    }

    public void deleteWriter(long index) {
        writers.delete(index);
    }

    public Writer getValueByIndex(long index) {
        return writers.getById(index);
    }

}
