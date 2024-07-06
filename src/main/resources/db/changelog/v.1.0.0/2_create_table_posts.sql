CREATE TABLE if not exists Posts
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer_id  BIGINT,
    content    TEXT,
    postStatus VARCHAR(50),
    created    TIMESTAMP,
    updated    TIMESTAMP,
    FOREIGN KEY (writer_id) REFERENCES writers (id)
);