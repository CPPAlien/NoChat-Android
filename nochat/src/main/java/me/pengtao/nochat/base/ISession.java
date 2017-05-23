package me.pengtao.nochat.base;

import java.io.Serializable;

import me.pengtao.nochat.constant.SessionType;

/**
 * session interface
 * Created by 90Chris on 2016/5/31.
 */
public interface ISession extends Serializable{
    String getId();
    String getLogo();
    String getTitle();
    String getContent();
    void setContent(String content);
    long getTime();
    SessionType getType();
}
