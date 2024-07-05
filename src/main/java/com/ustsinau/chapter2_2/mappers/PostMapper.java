package com.ustsinau.chapter2_2.mappers;

import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PostMapper {
    public void mapPost(ResultSet resultSet, Post post)  {

        try {
            post.setId(resultSet.getLong("post_id"));
            post.setContent(resultSet.getString("content"));
            post.setCreated(resultSet.getDate("created"));
            post.setUpdated(resultSet.getDate("updated"));
            post.setPostStatus(PostStatus.valueOf(resultSet.getString("postStatus")));
            if (post.getLabels() == null) {
                post.setLabels(new ArrayList<>());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
