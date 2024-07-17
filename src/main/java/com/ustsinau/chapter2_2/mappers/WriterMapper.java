package com.ustsinau.chapter2_2.mappers;

import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.Writer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WriterMapper {
    private final PostMapper postMapper = new PostMapper();

    public void mapWriter(ResultSet resultSet, Writer writer, List<Post> posts) {


        try {
            writer.setId(resultSet.getLong("writer_id"));
            writer.setFirstName(resultSet.getString("firstName"));
            writer.setLastName(resultSet.getString("lastName"));

            long postId = resultSet.getLong("post_id");
            if (postId != 0) {
                Post post = null;
                for (Post p : posts) {
                    if (p.getId() == postId) {
                        post = p;
                        break;
                    }
                }

                if (post == null) {
                    post = new Post();
                    post.setId(postId);
                    post.setLabels(new ArrayList<>());
                    posts.add(post);
                }

                postMapper.mapPost(resultSet, post, post.getLabels());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
