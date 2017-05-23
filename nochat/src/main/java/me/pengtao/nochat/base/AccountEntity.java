package me.pengtao.nochat.base;

import me.pengtao.nochat.constant.AccountType;
import me.pengtao.nochat.constant.Gender;

/**
 * user account
 * Created by 90Chris on 2016/4/25.
 */
public class AccountEntity implements IAccount {
    private AccountType type;
    private Gender gender;
    private String accountId;
    private String avatarUrl;
    private String nickname;

    public AccountEntity(Builder builder) {
        this.type = builder.type;
        this.accountId = builder.accountId;
        this.avatarUrl = builder.avatarUrl;
        this.nickname = builder.nickname;
        this.gender = builder.gender;
    }

    @Override
    public String getId() {
        return accountId;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public AccountType getType() {
        return type;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setType(AccountType type) {
        this.type = type;
    }

    public static class Builder {
        private AccountType type;
        private Gender gender;
        private String accountId;
        private String avatarUrl;
        private String nickname;

        public Builder id(String id) {
            this.accountId = id;
            return this;
        }

        public Builder type(AccountType type) {
            this.type = type;
            return this;
        }

        public Builder avatarUrl(String url) {
            this.avatarUrl = url;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickname = nickName;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public AccountEntity build() {
            return new AccountEntity(this);
        }
    }
}
