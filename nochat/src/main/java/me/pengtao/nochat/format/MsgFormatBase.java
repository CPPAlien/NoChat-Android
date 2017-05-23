package me.pengtao.nochat.format;

import java.io.Serializable;

/**
 * the base msg data format
 * Notice:
 * 1, Sub Class must implement default constructor, because we use class.newInstance() to construct the class
 * Created by 90Chris on 2016/4/19.
 */
public interface MsgFormatBase<T> extends Serializable{
    /**
     * change the class fields to json string format
     * for example: if the class have one field called "content". toJsonString
     * should return UrlEncoded string of "{"content":"xxx"}"
     * @return the encoded string format of the class
     */
    String toJsonString();

    /**
     * restore the jsonString to the class instance
     * @param encodedJsonString encoded string
     * @return class instance
     */
    T restore(String encodedJsonString);
}
