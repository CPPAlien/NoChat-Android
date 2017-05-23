package me.pengtao.nochat.format;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * MsgFormat
 * Notice: must include a default constructor
 * Created by 90Chris on 2016/5/12.
 */
public abstract class MsgFormat<T> implements MsgFormatBase<T> {
    private static final String TAG = "NoChat.MsgFormat";

    /**
     * return this
     *
     * @return this
     */
    protected abstract T retThis();

    /**
     * construct the class, use the field name to get the field value
     *
     * @param jsonObject content
     * @return class
     */
    protected abstract T construct(JSONObject jsonObject);

    @Override
    public String toJsonString() {
        Field[] fields = retThis().getClass().getDeclaredFields();
        JSONObject jsonObject = new JSONObject();
        for (Field field : fields) {
            if (!Modifier.isPrivate(field.getModifiers())) {
                continue;
            }
            String name = field.getName();
            field.setAccessible(true);
            try {
                Object value = field.get(retThis());
                jsonObject.put(name, value);
            } catch (Exception e) {
                Log.e(TAG, "put json failed, name = " + name, e);
            }
        }

        String jsonString = jsonObject.toString();
        String encodedString = null;
        try {
            encodedString = URLEncoder.encode(jsonString, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "string encode failed", e);
        }
        return encodedString;
    }

    @Override
    public T restore(String json) {
        try {
            String decodeString = URLDecoder.decode(json, "utf-8");
            JSONObject jsonObject = new JSONObject(decodeString);
            return construct(jsonObject);
        } catch (JSONException e) {
            Log.e(TAG, "Json construct error", e);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "url decode error", e);
        }
        return null;
    }
}
