package com.thoughtworks.chengdu.gb.moments.model;

import com.thoughtworks.chengdu.gb.moments.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GB on 2018/04/30.
 */

public class Tweet implements Serializable{
    private String content;
    private List<ImageModel> images;
    private Sender sender;
    private List<Comment> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
