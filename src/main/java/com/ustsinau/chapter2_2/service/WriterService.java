package com.ustsinau.chapter2_2.service;

import com.ustsinau.chapter2_2.models.Writer;
import com.ustsinau.chapter2_2.models.Post;

import java.util.List;

public interface WriterService {
    void createWriter(String firstName, String lastName);
    void updateWriter(long id, String firstName, String lastName, List<Post> posts);
    void deleteWriter(long id);
    Writer getWriterById(long id);
    List<Writer> getAllWriters();
}
