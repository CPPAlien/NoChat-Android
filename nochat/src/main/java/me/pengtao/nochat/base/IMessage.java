package me.pengtao.nochat.base;

import java.io.Serializable;

import me.pengtao.nochat.constant.MsgStatus;
import me.pengtao.nochat.format.MsgFormatBase;

/**
 * define the message content used in displaying
 * Created by 90Chris on 2016/4/19.
 */
public interface IMessage extends Serializable {
    String getId();
    boolean isSame(IMessage msg);
    long getTime();
    IAccount getSender();

    MsgStatus getStatus();
    void setStatus(MsgStatus status);

    MsgFormatBase getFormat();
    void setFormat(MsgFormatBase format);

    String getSessionId();
}
