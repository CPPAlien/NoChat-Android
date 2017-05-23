package me.pengtao.nochat.base;

import me.pengtao.nochat.constant.SessionType;

/**
 * session entity
 * Created by 90Chris on 2016/5/31.
 */
public class SessionEntity implements ISession {
    private String id;
    private String logo;
    private String title;
    private String content;
    private long time;
    private SessionType type;

    private SessionEntity(Builder builder) {
        this.id = builder.id;
        this.logo = builder.logo;
        this.title = builder.title;
        this.content = builder.content;
        this.time = builder.time;
        this.type = builder.type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLogo() {
        return logo;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public SessionType getType() {
        return type;
    }

    public static class Builder {
        private String id;
        private String logo;
        private String title;
        private String content;
        private long time;
        private SessionType type;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder logo(String logo) {
            this.logo = logo;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder time(long time) {
            this.time = time;
            return this;
        }

        public Builder type(SessionType type) {
            this.type = type;
            return this;
        }

        public SessionEntity build() {
            return new SessionEntity(this);
        }
    }
}
