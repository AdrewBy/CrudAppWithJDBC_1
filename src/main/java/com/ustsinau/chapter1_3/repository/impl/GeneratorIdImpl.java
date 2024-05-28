package com.ustsinau.chapter1_3.repository.impl;

import com.ustsinau.chapter1_3.models.Cache;
import com.ustsinau.chapter1_3.models.Label;
import com.ustsinau.chapter1_3.models.Post;
import com.ustsinau.chapter1_3.models.Writer;
import com.ustsinau.chapter1_3.repository.GeneratorId;
import com.ustsinau.chapter1_3.repository.LabelRepository;
import com.ustsinau.chapter1_3.repository.PostRepository;
import com.ustsinau.chapter1_3.repository.WriterRepository;

import java.util.*;

public class GeneratorIdImpl implements GeneratorId {

    public static Cache cache;
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();
    private final PostRepository postRepository= new GsonPostRepositoryImpl();

    private final LabelRepository labelRepository = new GsonLabelRepository();


    public GeneratorIdImpl() {
    }

    @Override
    public void initCache() {
        cache = new Cache();

        cache.setMaxWriterId(getMaxWriterId());
        cache.setMaxLabelId(getMaxLabelId());
        cache.setMaxPostId(getMaxPostId());
    }

    private Long getMaxWriterId(){
        List<Writer> writers = writerRepository.getAll();

        if(!writers.isEmpty()) {
            long id = writers.stream().max((a, b) -> Math.toIntExact(a.getId() - b.getId())).get().getId();

            cache.setMaxWriterId(id);
        }
        return  0L; // что вернуть тут?
    }

    private Long getMaxPostId(){
        List<Post> posts = postRepository.getAll();
        if(!posts.isEmpty()){
         //   long id = posts.stream().max((a,b) -> Math.toIntExact(a.getId() - b.getId())).get().getId();
            long id = posts.stream().mapToLong(Post::getId).summaryStatistics().getMax();
            cache.setMaxPostId(id);
        }
        return 0L;
    }
    private Long getMaxLabelId() {
        List<Label> labels = labelRepository.getAll();
        if(!labels.isEmpty()){
            long id = labels.stream().max((a,b) -> Math.toIntExact(a.getId() - b.getId())).get().getId();

            cache.setMaxLabelId(id);
        }
        return 0L;
    }

}
