package com.ustsinau.chapter2_2.mappers;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostMapper {
    public Post mapPost(ResultSet resultSet, Post post) throws SQLException {

        post.setId(resultSet.getLong("post_id"));
        post.setContent(resultSet.getString("content"));
        post.setCreated(resultSet.getDate("created"));
        post.setUpdated(resultSet.getDate("updated"));
        post.setPostStatus(PostStatus.valueOf(resultSet.getString("postStatus")));
        if (post.getLabels() == null) {
            post.setLabels(new ArrayList<>());
        }
        return post;
    }
}
