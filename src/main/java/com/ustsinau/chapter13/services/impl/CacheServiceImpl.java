package com.ustsinau.chapter13.services.impl;

import com.ustsinau.chapter13.models.Cache;
import com.ustsinau.chapter13.models.Label;
import com.ustsinau.chapter13.models.Post;
import com.ustsinau.chapter13.models.Writer;
import com.ustsinau.chapter13.repository.LabelRepository;
import com.ustsinau.chapter13.repository.PostRepository;
import com.ustsinau.chapter13.repository.WriterRepository;
import com.ustsinau.chapter13.repository.impl.GsonLabelRepository;
import com.ustsinau.chapter13.repository.impl.GsonPostRepositoryImpl;
import com.ustsinau.chapter13.repository.impl.GsonWriterRepositoryImpl;
import com.ustsinau.chapter13.services.CacheService;

import java.util.*;
import java.util.stream.Collectors;

public class CacheServiceImpl implements CacheService {

    public static Cache cache;
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();
    private final PostRepository postRepository= new GsonPostRepositoryImpl();

    private final LabelRepository labelRepository = new GsonLabelRepository();


    public CacheServiceImpl() {
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

        if(!writers.isEmpty()){
            long id = writers.stream().max((a,b) -> Math.toIntExact(a.getId() - b.getId())).get().getId();

          cache.setMaxWriterId(id);

        } else {
            cache.setMaxWriterId(0);
        }
        return cache.getMaxWriterId(); // что вернуть тут?
    }

    private Long getMaxPostId(){
        List<Post> posts = postRepository.getAll();
        if(!posts.isEmpty()){
         //   long id = posts.stream().max((a,b) -> Math.toIntExact(a.getId() - b.getId())).get().getId();
            long id = posts.stream().mapToLong(Post::getId).summaryStatistics().getMax();
            cache.setMaxPostId(id);
        }else {
            cache.setMaxPostId(0);
        }
        return cache.getMaxPostId(); // что вернуть тут?
    }
    private Long getMaxLabelId() {
        List<Label> labels = labelRepository.getAll();
        if(!labels.isEmpty()){
            long id = labels.stream().max((a,b) -> Math.toIntExact(a.getId() - b.getId())).get().getId();

            cache.setMaxLabelId(id);
        }else {
            cache.setMaxLabelId(0);
        }
        return cache.getMaxLabelId(); // что вернуть тут?
    }

}
