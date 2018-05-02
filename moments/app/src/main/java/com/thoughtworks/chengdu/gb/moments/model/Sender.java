package com.thoughtworks.chengdu.gb.moments.model;

import com.thoughtworks.chengdu.gb.moments.base.BaseModel;

import java.io.Serializable;

/**
 * Created by GB on 2018/04/30.
 */

public class Sender implements Serializable{
    private String avatar;
    private String nick;
    private String username;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
