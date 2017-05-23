package me.pengtao.nochat.base;

import java.io.Serializable;

import me.pengtao.nochat.constant.AccountType;
import me.pengtao.nochat.constant.Gender;

/**
 * IAccount
 * Created by 90Chris on 2016/4/25.
 */
public interface IAccount extends Serializable {
    String getId();
    String getAvatarUrl();
    AccountType getType();
    String getNickname();
    Gender getGender();
    void setType(AccountType type);
}
