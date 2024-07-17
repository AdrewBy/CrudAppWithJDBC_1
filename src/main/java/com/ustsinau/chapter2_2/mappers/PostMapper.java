package com.ustsinau.chapter2_2.mappers;

import com.ustsinau.chapter2_2.models.Label;
import com.ustsinau.chapter2_2.models.Post;
import com.ustsinau.chapter2_2.models.PostStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostMapper {
    private final LabelMapper labelMapper = new LabelMapper();
    public void mapPost(ResultSet resultSet, Post post, List<Label> labels)  {

        try {
            post.setId(resultSet.getLong("post_id"));
            post.setContent(resultSet.getString("content"));
            post.setCreated(resultSet.getDate("created"));
            post.setUpdated(resultSet.getDate("updated"));
            post.setPostStatus(PostStatus.valueOf(resultSet.getString("postStatus")));

            long labelId = resultSet.getLong("label_id");
            if (labelId != 0) {
                Label label = new Label();
                labelMapper.mapLabel(resultSet, label);
                labels.add(label);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
