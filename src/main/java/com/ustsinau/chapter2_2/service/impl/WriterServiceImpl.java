package com.ustsinau.chapter2_2.service.impl;

import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.Writer;
import com.ustsinau.chapter2_2.repository.WriterRepository;
import com.ustsinau.chapter2_2.repository.impl.JdbcWriterRepositoryImpl;
import com.ustsinau.chapter2_2.service.WriterService;

import java.util.List;

public class WriterServiceImpl implements WriterService {
    private final WriterRepository writerRepository = new JdbcWriterRepositoryImpl();
    @Override
    public void createWriter(String firstName, String lastName) {
        Writer writer = new Writer(firstName, lastName);
        writerRepository.create(writer);
    }

    @Override
    public void updateWriter(long id, String firstName, String lastName, List<Post> posts) {
        Writer wrt = new Writer(id, firstName, lastName, posts );
        writerRepository.update(wrt);
    }

    @Override
    public void deleteWriter(long id) {
        writerRepository.delete(id);

    }

    @Override
    public Writer getWriterById(long id) {
        return writerRepository.getById(id);

    }

    @Override
    public List<Writer> getAllWriters() {
        return writerRepository.getAll();
    }
}
