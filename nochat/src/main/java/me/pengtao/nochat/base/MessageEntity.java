package me.pengtao.nochat.base;

import me.pengtao.nochat.constant.MsgStatus;
import me.pengtao.nochat.format.MsgFormatBase;

/**
 * MessageEntity
 * Created by 90Chris on 2016/4/19.
 */
public class MessageEntity implements IMessage {
    private String msgId;
    private MsgStatus msgStatus;
    private long msgTime;
    private MsgFormatBase msgFormat;
    private IAccount msgSender;
    private String sessionId;

    private MessageEntity(Builder builder) {
        msgId = builder.msgId;
        msgStatus = builder.msgStatus;
        msgTime = builder.msgTime;
        msgFormat = builder.msgFormat;
        msgSender = builder.msgSender;
        sessionId = builder.sessionId;
    }

    @Override
    public String getId() {
        return msgId;
    }

    @Override
    public boolean isSame(IMessage msg) {
        return msg.getId().equals(msgId);
    }

    @Override
    public long getTime() {
        return msgTime;
    }

    @Override
    public IAccount getSender() {
        return msgSender;
    }

    @Override
    public MsgStatus getStatus() {
        return msgStatus;
    }

    @Override
    public void setStatus(MsgStatus status) {
        msgStatus = status;
    }

    @Override
    public MsgFormatBase getFormat() {
        return msgFormat;
    }

    @Override
    public void setFormat(MsgFormatBase format) {
        msgFormat = format;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }


    public static class Builder {
        private String msgId;
        private MsgStatus msgStatus;
        private long msgTime;
        private MsgFormatBase msgFormat;
        private IAccount msgSender;
        private String sessionId;

        //necessary
        public Builder id(String msgId) {
            this.msgId = msgId;
            return this;
        }

        //necessary
        public Builder status(MsgStatus msgStatus) {
            this.msgStatus = msgStatus;
            return this;
        }

        //necessary for sorting
        public Builder time(long msgTime) {
            this.msgTime = msgTime;
            return this;
        }

        //necessary
        public Builder format(MsgFormatBase msgFormat) {
            this.msgFormat = msgFormat;
            return this;
        }

        public Builder sender(IAccount sender) {
            this.msgSender = sender;
            return this;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public MessageEntity build() {
            return new MessageEntity(this);
        }
    }
}
