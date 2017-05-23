package me.pengtao.nochat.msg;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import me.pengtao.nochat.format.MsgFormat;

/**
 * TextMsgFormat
 * Created by 90Chris on 2016/4/20.
 */
public class TextMsgFormat extends MsgFormat<TextMsgFormat> {
    private static final String TAG = "NoChat.TextMsgFormat";
    private String content;
    /**
     * Shouldn't be deleted
     * used by reflection class.newInstance();
     */
    @SuppressWarnings("unused")
    public TextMsgFormat() {
    }

    public TextMsgFormat(String ctn) {
        content = ctn;
    }

    public String getContent() {
        return content;
    }

    @Override
    protected TextMsgFormat retThis() {
        return this;
    }

    @Override
    protected TextMsgFormat construct(JSONObject jsonObject) {
        try {
            return new TextMsgFormat(jsonObject.getString("content"));
        } catch (JSONException e) {
            Log.e(TAG, "get json failed", e);
        }
        return null;
    }
}