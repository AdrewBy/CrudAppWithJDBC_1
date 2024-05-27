package com.ustsinau.chapter1_3.controller;

import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.models.Status;
import com.ustsinau.chapter1_3.models.Writer;
import com.ustsinau.chapter1_3.repository.LabelRepository;
import com.ustsinau.chapter1_3.repository.PostRepository;
import com.ustsinau.chapter1_3.repository.WriterRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonLabelRepository;
import com.ustsinau.chapter1_3.repository.impl.GsonPostRepositoryImpl;
import com.ustsinau.chapter1_3.repository.impl.GsonWriterRepositoryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ustsinau.chapter1_3.services.impl.CacheServiceImpl.cache;

public class WriterController {
    private  WriterRepository writers = new GsonWriterRepositoryImpl();
    private  PostRepository posts = new GsonPostRepositoryImpl();
    private  LabelRepository labels = new GsonLabelRepository();

    public List<Writer> showAll() {
        return writers.getAll();
    }


    public void createWriterWithoutPost(String firstName, String lastName) {
        long maxWriterId = cache.getMaxWriterId();
        maxWriterId++;

        writers.create(new Writer(maxWriterId,firstName, lastName, Status.ACTIVE));
        cache.setMaxWriterId(maxWriterId);

    }

    public void updateWriter(long id, String firstName, String lastName, String post) {
        List<Post> postsWriter = new ArrayList<>();

        long[] numArr = Arrays.stream(post.split(" ")).mapToLong(e -> Long.parseLong(e)).toArray();

        for (long l : numArr) {                               // подумать над этим
            if (posts.getById(l).getLabels().isEmpty()) {
                postsWriter.add(posts.getById(l));
            }
        }

        Writer wrt = new Writer(id, firstName, lastName, postsWriter );
        writers.update(wrt);
    }

    public void deleteWriter(long index) {

        writers.delete(index);
    }

    public Writer getValueByIndex(long id)  {


        return writers.getById(id);
    }

}
