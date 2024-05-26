package com.ustsinau.chapter13.models;

public class Cache {

    private long maxWriterId;
    private long maxPostId;
    private long maxLabelId;

    public long getMaxWriterId() {
        return maxWriterId;
    }

    public void setMaxWriterId(long maxWriterId) {
        this.maxWriterId = maxWriterId;
    }

    public long getMaxPostId() {
        return maxPostId;
    }

    public void setMaxPostId(long maxPostId) {
        this.maxPostId = maxPostId;
    }

    public long getMaxLabelId() {
        return maxLabelId;
    }

    public void setMaxLabelId(long maxLabelId) {
        this.maxLabelId = maxLabelId;
    }
}
