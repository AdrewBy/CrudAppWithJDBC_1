CREATE TABLE if not exists Post_Label
(
    post_id  BIGINT,
    label_id BIGINT,
    PRIMARY KEY (post_id, label_id),
    FOREIGN KEY (post_id) REFERENCES Posts (id),
    FOREIGN KEY (label_id) REFERENCES Labels (id)
);